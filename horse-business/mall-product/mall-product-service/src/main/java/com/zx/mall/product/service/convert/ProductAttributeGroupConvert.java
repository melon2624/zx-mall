package com.zx.mall.product.service.convert;

import com.zx.mall.product.model.dto.admin.ProductAttributeGroupDTO;
import com.zx.mall.product.model.entity.ProductAttributeGroup;
import com.zx.mall.product.model.vo.admin.ProductAttributeGroupVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/12 13:59
 */
@Mapper(componentModel = "spring")
public interface ProductAttributeGroupConvert {


    public ProductAttributeGroup toEntity(ProductAttributeGroupDTO productAttributeGroupDTO);

    List<ProductAttributeGroupVO> toVO(List<ProductAttributeGroup> byProductId);
}
