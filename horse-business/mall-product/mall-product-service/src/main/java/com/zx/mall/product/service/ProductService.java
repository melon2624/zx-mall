package com.zx.mall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.web.result.BizAssert;
import com.zx.mall.marketing.api.ProductRecommendDubboService;
import com.zx.mall.product.enums.ProductTypeEnums;
import com.zx.mall.product.mapper.ProductMapper;
import com.zx.mall.product.model.dto.admin.ProductModifyDTO;
import com.zx.mall.product.model.dto.admin.ProductQueryDTO;
import com.zx.mall.product.model.entity.Product;
import com.zx.mall.product.model.entity.ProductDetail;
import com.zx.mall.product.model.vo.admin.ProductModifyVO;
import com.zx.mall.product.model.vo.admin.ProductVO;
import com.zx.mall.product.model.vo.admin.VirtualProductVO;
import com.zx.mall.product.service.convert.ProductConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/3 15:17
 */
@Slf4j
@Service("productService")
public class ProductService extends ServiceImpl<ProductMapper, Product> {

    @Resource
    private ProductConvert productConvert;

    @Resource
    private ProductDetailService productDetailService;

    @Resource
    private ProductPictureService productPictureService;

    @DubboReference
    private ProductRecommendDubboService productRecommendDubboService;

    @Resource
    private VirtualProductService virtualProductService;

    @Resource
    private  ProductSkuService productSkuService;


    /**
     * 更新商品分类
     *
     * @param oldCategoryId 老分类id
     * @param newCategoryId 新分类id
     * @return boolean
     */
    public Integer updateCategory(Long oldCategoryId, Long newCategoryId) {

        List<Product> productList = lambdaQuery().eq(Product::getCategoryId, oldCategoryId).list();
        productList.forEach(product -> product.setCategoryId(newCategoryId));
        updateBatchById(productList, 1000);
        return productList.size();
    }

    public IPage<ProductVO> page(ProductQueryDTO productQueryDTO) {

        String productName = productQueryDTO.getName();
        Long categoryId = productQueryDTO.getCategoryId();
        Page<Product> productPage = lambdaQuery().like(StringUtils.isNotEmpty(productName), Product::getName, productName)
                .eq(categoryId != null, Product::getCategoryId, categoryId)
                .page(productQueryDTO.page());
        Page<ProductVO> productVOPage = productConvert.toVo(productPage);
        // 统计商品总库存
        productVOPage.getRecords().forEach(productVO -> {
            productVO.setTotalStock(productSkuService.getTotalStock(productVO.getId()));
        });
        return productVOPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long save(ProductModifyDTO productModifyDTO) {
        List<String> pictureList = productModifyDTO.getPictureList();
        String detail = productModifyDTO.getDetail();
        Product product = productConvert.toEntity(productModifyDTO);
        // 1.写入商品表
        // 设置主图
        product.setMainPicture(productModifyDTO.getPictureList().get(0));
        save(product);
        Long productId = product.getId();
        // 2.写入商品详情表
        productDetailService.save(new ProductDetail().setProductId(productId).setDetail(detail));
        // 3.写入商品图片表
        productPictureService.savePictureList(productId, pictureList);
        // 设置商品推荐
        if (productModifyDTO.getIsRecommend()) {
            productRecommendDubboService.setProductRecommend(productId);
        }
        // 如果是虚拟商品 更新虚拟商品信息
        if (ProductTypeEnums.VIRTUAL.equals(product.getProductType())) {
            BizAssert.notEmpty(productModifyDTO.getVirtualProductModifyDTOList(), "虚拟商品的文件、内容必填一项");
            virtualProductService.deleteAllAndAdd(productId, productModifyDTO.getVirtualProductModifyDTOList());
        }
        return productId;

    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> idList) {
        for (Long id : idList) {
            productRecommendDubboService.delProductRecommend(id);
        }
        return removeByIds(idList);
    }

    public Boolean update(Long productId, ProductModifyDTO productModifyDTO) {
        Product oldProduct = getById(productId);
        BizAssert.isTrue(oldProduct.getProductType().equals(productModifyDTO.getProductType()), "不能修改商品类型,productId:" + productId);
        Product product = productConvert.toEntity(productModifyDTO);
        product.setId(productId);
        product.setMainPicture(productModifyDTO.getPictureList().get(0));
        // 商品详情表
        String detail = productModifyDTO.getDetail();
        productDetailService.update(productId, detail);
        //商品图片表
        productPictureService.savePictureList(productId, productModifyDTO.getPictureList());
        //设置商品推荐表
        if (productModifyDTO.getIsRecommend()) {
            productRecommendDubboService.setProductRecommend(productId);
        } else {
            productRecommendDubboService.delProductRecommend(productId);
        }
        // 如果是虚拟商品 保存虚拟商品信息
        if (ProductTypeEnums.VIRTUAL.getCode().equals(product.getProductType())) {
            BizAssert.notEmpty(productModifyDTO.getPictureList(), "虚拟商品的文件、内容必填一项");
            virtualProductService.deleteAllAndAdd(productId, productModifyDTO.getVirtualProductModifyDTOList());
        }
        return true;
    }

    /**
     * 上下架
     *
     * @param productId 商品id
     * @param isEnable  上架状态
     * @return boolean
     */
    public Boolean enableOrDisable(Long productId, Boolean isEnable) {
        Product product = new Product();
        product.setId(productId);
        product.setIsEnable(isEnable);
        return updateById(product);
    }

    public ProductModifyVO getOne(Long productId) {
        Product product = getById(productId);
        ProductDetail productDetail = productDetailService.getById(productId);
        BizAssert.notNull(productDetail, "商品详情数据异常，找不到商品数据：" + productId);
        List<String> productPictureList = productPictureService.listProductPictureByProductId(productId);
        ProductModifyVO productModifyVO = productConvert.toModifyVO(product);
        productModifyVO.setDetail(productDetail.getDetail());
        productModifyVO.setPictureList(productPictureList);
        productModifyVO.setIsRecommend(productRecommendDubboService.getProductRecommend(productId));
        // 如果商品类型是虚拟商品 需要查询虚拟发货信息进行返回
        if (ProductTypeEnums.VIRTUAL.getCode().equals(product.getProductType())) {
            List<VirtualProductVO> virtualProductVOS = virtualProductService.listVirtualProduct(productId);
            productModifyVO.setVirtualProductVOList(virtualProductVOS);
        }
        return productModifyVO;
    }
}
