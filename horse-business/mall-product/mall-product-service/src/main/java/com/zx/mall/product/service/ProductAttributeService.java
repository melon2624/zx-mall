package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.mall.product.mapper.ProductAttributeMapper;
import com.zx.mall.product.model.dto.admin.ProductAttributeDTO;
import com.zx.mall.product.model.dto.admin.SortModifyDTO;
import com.zx.mall.product.model.entity.ProductAttribute;
import com.zx.mall.product.model.vo.admin.ProductAttributeVO;
import com.zx.mall.product.service.convert.ProductAttributeConvert;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性(ProductAttribute)表服务实现类
 *
 * @author zhangxin
 * @date 2023/1/12 20:22
 */
@Service
public class ProductAttributeService extends ServiceImpl<ProductAttributeMapper, ProductAttribute> {

    @Resource
    ProductAttributeConvert productAttributeConvert;

    @Transactional(rollbackFor = Exception.class)
    public Long save(ProductAttributeDTO productAttributeDTO) {
        ProductAttribute productAttribute = productAttributeConvert.toEntity(productAttributeDTO);
        save(productAttribute);
        //设置排序值
        getBaseMapper().setSort(productAttribute.getProductId(), productAttribute.getProductAttributeGroupId());
        // 设置symbol
        getBaseMapper().setSymbol(productAttribute.getProductId(), productAttribute.getId());
        return productAttribute.getId();
    }

    public List<ProductAttributeVO> listVo(Long groupId) {
        return productAttributeConvert.toVo(list(groupId));
    }

    /**
     * 查询属性组下的所有属性，排序
     */
    private List<ProductAttribute> list(Long groupId) {
        return lambdaQuery().eq(ProductAttribute::getProductAttributeGroupId, groupId)
                .orderByAsc(ProductAttribute::getSort).list();
    }

    public Boolean update(Long attributeId, ProductAttributeDTO productAttributeDTO) {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(attributeId);
        productAttribute.setName(productAttributeDTO.getName());
        return updateById(productAttribute);
    }

    /**
     * 更新排序
     *
     * @param sortModifyDTO 排序信息
     */
    public Boolean updateSort(SortModifyDTO sortModifyDTO) {
        Long attributeId = sortModifyDTO.getId();
        Integer oldSort = sortModifyDTO.getOldSort();
        Integer currentSort = sortModifyDTO.getCurrentSort();
        ProductAttribute productAttribute = lambdaQuery().select(ProductAttribute::getProductId)
                .eq(ProductAttribute::getId, attributeId)
                .oneOpt().orElseThrow(() -> new RuntimeException("找不到该属性id：" + attributeId));
        Long productId = productAttribute.getProductId();
        return getBaseMapper().updateSort(productId, attributeId, oldSort, currentSort);
    }

    public Boolean delete(List<Long> idList) {
        return this.removeByIds(idList);
    }
}
