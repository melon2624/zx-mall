package com.zx.mall.product.service.convert;

import com.zx.mall.product.model.dto.admin.ProductSkuDTO;
import com.zx.mall.product.model.entity.ProductSku;
import com.zx.mall.product.model.vo.admin.ProductSkuVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 
 * 商品sku(ProductSku)转换接口
 * 
 * @author zhangxin
 * @date 2023/1/29 14:18
 */
@Mapper(componentModel = "spring")
public interface ProductSkuConvert {
    ProductSku toEntity(ProductSkuDTO productSkuDTO);

    List<ProductSkuVO> toVo(List<ProductSku> productSkuList);
}
