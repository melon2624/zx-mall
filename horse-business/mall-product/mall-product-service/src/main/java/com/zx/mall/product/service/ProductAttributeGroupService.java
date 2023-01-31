package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.mall.product.mapper.ProductAttributeGroupMapper;
import com.zx.mall.product.model.dto.admin.ProductAttributeGroupDTO;
import com.zx.mall.product.model.dto.admin.SortModifyDTO;
import com.zx.mall.product.model.entity.ProductAttributeGroup;
import com.zx.mall.product.model.vo.admin.ProductAttributeGroupVO;
import com.zx.mall.product.service.convert.ProductAttributeGroupConvert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性组(ProductAttributeGroup)表服务实现类
 *
 * @author zhangxin
 * @date 2023/1/11 14:38
 */
@Service("productAttributeGroupService")
public class ProductAttributeGroupService extends ServiceImpl<ProductAttributeGroupMapper, ProductAttributeGroup> {


    @Resource
    ProductAttributeGroupConvert productAttributeGroupConvert;

    @Transactional(rollbackFor = Exception.class)
    public Long save(ProductAttributeGroupDTO productAttributeGroupDTO) {
        ProductAttributeGroup productAttributeGroup = productAttributeGroupConvert.toEntity(productAttributeGroupDTO);
        //保存并设置排序
        save(productAttributeGroup);
        getBaseMapper().setSort(productAttributeGroup.getProductId(), productAttributeGroup.getId());
        return productAttributeGroup.getId();
    }

    public Boolean update(Long id, ProductAttributeGroupDTO productAttributeGroupDTO) {

        ProductAttributeGroup productAttributeGroup = productAttributeGroupConvert.toEntity(productAttributeGroupDTO);
        productAttributeGroup.setId(id);
        return updateById(productAttributeGroup);
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

        ProductAttributeGroup productAttributeGroup = lambdaQuery().select(ProductAttributeGroup::getProductId)
                .eq(ProductAttributeGroup::getId, attributeId)
                .oneOpt()
                .orElseThrow(() -> new RuntimeException("找不到该属性id:" + attributeId));
        Long productId = productAttributeGroup.getProductId();
        return getBaseMapper().updateSort(productId, attributeId, oldSort, currentSort);
    }

    public Boolean delete(List<Long> idList) {
        return this.removeByIds(idList);
    }

    /**
     * 根据商品id获取属性组
     */
    public List<ProductAttributeGroupVO> getVOByProductId(Long productId) {
        return productAttributeGroupConvert.toVO(getByProductId(productId));
    }

    private List<ProductAttributeGroup> getByProductId(Long productId) {
        return lambdaQuery().eq(ProductAttributeGroup::getProductId, productId)
                .orderByAsc(ProductAttributeGroup::getSort)
                .list();
    }
}
