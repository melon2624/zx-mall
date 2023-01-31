package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.common.utils.ListUtil;
import com.zx.framework.web.result.BizAssert;
import com.zx.mall.product.mapper.ProductSkuMapper;
import com.zx.mall.product.model.dto.admin.ProductSkuDTO;
import com.zx.mall.product.model.entity.Product;
import com.zx.mall.product.model.entity.ProductSku;
import com.zx.mall.product.model.vo.admin.ProductSkuVO;
import com.zx.mall.product.service.convert.ProductSkuConvert;
import io.swagger.models.auth.In;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zhangxin
 * @date 2023/1/10 11:27
 */
@Service("productSkuService")
public class ProductSkuService extends ServiceImpl<ProductSkuMapper, ProductSku> {

    @Resource
    ProductService productService;

    @Resource
    ProductSkuConvert productSkuConvert;

    public BigDecimal getTotalStock(Long id) {
        return null;
    }

    /**
     * 新增或修改sku数据
     *
     * @param productId         商品id
     * @param productSkuDTOList sku数据
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(Long productId, List<ProductSkuDTO> productSkuDTOList) {
        if (CollectionUtils.isEmpty(productSkuDTOList)) {
            return false;
        }
        Product product = new Product();
        product.setId(productId);
        // 1.更新商品起售价，总库存
        productSkuDTOList.stream().map(ProductSkuDTO::getSellPrice)
                .min(Comparator.naturalOrder())
                .ifPresent(product::setStartingPrice);
        productService.updateById(product);

        // 2.新增或修改sku数据
        List<ProductSku> productSkuList = productSkuDTOList.stream().map(productSkuDTO -> {
            ProductSku productSku = productSkuConvert.toEntity(productSkuDTO);
            productSku.setProductId(productId);
            // 更新库存数量时，忽略stock参数，通过第三步的stockChange来设置
            if (productSku.getId() != null) {
                productSku.setStock(null);
            }
            return productSku;
        }).collect(Collectors.toList());
        saveOrUpdateBatch(productSkuList);

        // 3.更新sku库存数量
        // 有id代表是修改，有stockChange且不等于0代表是更新库存数量
        Predicate<ProductSkuDTO> needUpdateStockPredicate = productSkuDTO -> (
                productSkuDTO.getId() != null && productSkuDTO.getStockChange() != null && !productSkuDTO.getStockChange().equals(0));

        productSkuDTOList.stream().filter(needUpdateStockPredicate).forEach(productSkuDTO -> {
            Integer stockChange = productSkuDTO.getStockChange();
            // 判断是增加库存还是扣减库存
            boolean result = stockChange > 0 ? getBaseMapper().addStock(productSkuDTO.getId(), stockChange)
                    : getBaseMapper().reduceStock(productSkuDTO.getId(), -stockChange);
            BizAssert.isTrue(result, "规格[" + productSkuDTO.getName() + "]库存不足，仅剩" + getById(productSkuDTO.getId()).getStock() + "，调整库存数量：" + stockChange);
        });

        return true;
    }

    /**
     * 根据商品id查询sku信息(后管)
     *
     * @param productId 商品id
     * @return 商品sku列表
     */
    public List<ProductSkuVO> listVo(Long productId) {
        List<ProductSku> productSkuList = lambdaQuery().eq(ProductSku::getProductId, productId).list();
        return productSkuConvert.toVo(productSkuList);
    }

    /**
     * 删除商品下的所有sku
     *
     * @param productId 商品id
     */
    public void deleteAllByProductId(Long productId) {
        List<ProductSku> productSkuList = lambdaQuery().select(ProductSku::getId).eq(ProductSku::getProductId, productId).list();
        List<Long> idList = ListUtil.convert(productSkuList, ProductSku::getId);
        delete(idList);
    }

    private Boolean delete(List<Long> idList) {
        return this.removeByIds(idList);
    }


}
