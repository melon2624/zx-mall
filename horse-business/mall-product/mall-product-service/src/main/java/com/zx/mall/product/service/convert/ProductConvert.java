package com.zx.mall.product.service.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zx.mall.product.model.dto.admin.ProductModifyDTO;
import com.zx.mall.product.model.entity.Product;
import com.zx.mall.product.model.vo.admin.ProductModifyVO;
import com.zx.mall.product.model.vo.admin.ProductVO;
import org.mapstruct.Mapper;

/**
 * 商品(Product)转换接口
 *
 * @author zhangxin
 * @date 2023/1/3 18:32
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    Product toEntity(ProductModifyDTO productQueryDTO);

    ProductModifyVO toModifyVO(Product product);

    Page<ProductVO> toVo(Page<Product> productPage);
}
