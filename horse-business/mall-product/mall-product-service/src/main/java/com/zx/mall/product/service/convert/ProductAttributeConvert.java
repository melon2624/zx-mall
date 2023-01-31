package com.zx.mall.product.service.convert;

import com.zx.mall.product.model.dto.admin.ProductAttributeDTO;
import com.zx.mall.product.model.entity.ProductAttribute;
import com.zx.mall.product.model.vo.admin.ProductAttributeVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 商品属性(ProductAttribute)转换接口
 *
 * @author zhangxin
 * @date 2023/1/12 20:31
 */
@Mapper(componentModel = "spring")
public interface ProductAttributeConvert {

    ProductAttribute toEntity(ProductAttributeDTO productAttributeDTO);

    List<ProductAttributeVO> toVo(List<ProductAttribute> list);
}
