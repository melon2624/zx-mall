package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.web.result.BizAssert;
import com.zx.mall.product.mapper.ProductCategoryMapper;
import com.zx.mall.product.model.dto.admin.ProductCategoryModifyDTO;
import com.zx.mall.product.model.dto.admin.ProductCategoryQueryDTO;
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
}
