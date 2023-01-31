package com.zx.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.mall.product.model.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 商品分类(ProductCategory)表数据库访问层
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

    /**
     * 更新排序
     * <p>
     * 如果位置往后调，则新老位置之间的元素的sort全部减1
     * 反之如果位置往前调，则新老位置之间的元素的sort全部加1
     *
     * @param id      商品分类id
     * @param oldSort 旧排序
     * @param newSort 新排序
     * @return 更新数量
     */
    @Update({
            "update product_category set sort = if(${oldSort}>${newSort}, sort+1, sort-1) where (sort between ${oldSort} and ${newSort} or sort between ${newSort} and ${oldSort});",
            "update product_category set sort = ${newSort} where id = ${id}"
    })
    boolean updateSort(@Param("id") Long id, @Param("oldSort") int oldSort, @Param("newSort") int newSort);
}
