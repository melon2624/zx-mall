package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.web.result.BizAssert;
import com.zx.mall.product.mapper.ProductCategoryMapper;
import com.zx.mall.product.model.dto.admin.ProductCategoryModifyDTO;
import com.zx.mall.product.model.dto.admin.ProductCategoryQueryDTO;
import com.zx.mall.product.model.dto.admin.SortModifyDTO;
import com.zx.mall.product.model.entity.ProductCategory;
import com.zx.mall.product.model.vo.admin.ProductCategoryVO;
import com.zx.mall.product.service.convert.ProductCategoryConvert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxin
 * @date 2022/12/20 13:23
 */
@Service("productCategoryService")
public class ProductCategoryService extends ServiceImpl<ProductCategoryMapper, ProductCategory> {


    @Resource
    ProductCategoryConvert productCategoryConvert;

    @Resource
    ProductService productService;

    @Resource
    private ProductCategoryMapper productCategoryMapper;


    private static final long PARENT_ID = 0L;

    @Transactional(rollbackFor = Exception.class)
    public Boolean save(ProductCategoryModifyDTO productCategoryModifyDTO) {

        ProductCategory productCategory = productCategoryConvert.toEntity(productCategoryModifyDTO);

        int level;
        // 一级分类的父id为0
        Long parentId = productCategoryModifyDTO.getParentId();
        if (parentId == null || parentId == PARENT_ID) {
            level = 1;
        } else {
            ProductCategory parentProductCategory = getById(parentId);
            BizAssert.notNull(parentProductCategory, "parentId查不到: " + parentId);
            level = parentProductCategory.getLevel() + 1;
        }
        productCategory.setLevel(level);
        save(productCategory);

        return getBaseMapper().setSort(productCategory.getId(), parentId);
    }

    public List<ProductCategoryVO> listUserTree(ProductCategoryQueryDTO productCategoryQueryDTO) {

        return null;
    }

    public Boolean update(Long id, ProductCategoryModifyDTO productCategoryModifyDTO) {
        ProductCategory productCategory = productCategoryConvert.toEntity(productCategoryModifyDTO);
        productCategory.setId(id);
        Long parentId = productCategory.getParentId();
        if (parentId != null && parentId != PARENT_ID) {
            // 设置分级：父分类分级+1
            ProductCategory parentCateGory = getById(parentId);
            productCategory.setLevel(parentCateGory.getLevel() + 1);
        }
        return updateById(productCategory);
    }

    /**
     * 转移分类下的商品
     *
     * @param oldId 老分类id
     * @param newId 新分类id
     * @return boolean
     */
    public Integer transfer(Long oldId, Long newId) {
        return productService.updateCategory(oldId, newId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSort(SortModifyDTO sortModifyDTO) {
        Long id = sortModifyDTO.getId();
        Integer oldSort=sortModifyDTO.getOldSort();
        Integer currentSort=sortModifyDTO.getCurrentSort();
        return productCategoryMapper.updateSort(id,oldSort,currentSort);
    }
}
