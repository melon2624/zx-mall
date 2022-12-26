package com.zx.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.mall.product.model.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangxin
 * @date 2022/12/20 14:30
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    /**
     * 设置排序
     * 取同级分类中排序最大值
     *
     * @param parentId 父id
     * @param id       分类id
     * @return boolean
     */
    Boolean setSort( @Param("id") Long id, @Param("parentId")  Long parentId);
}
