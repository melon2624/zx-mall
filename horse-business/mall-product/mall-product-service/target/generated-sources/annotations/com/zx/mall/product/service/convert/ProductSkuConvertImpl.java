package com.zx.mall.product.service.convert;

import com.zx.mall.product.model.dto.admin.ProductSkuDTO;
import com.zx.mall.product.model.entity.ProductSku;
import com.zx.mall.product.model.vo.admin.ProductSkuVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-30T15:03:25+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class ProductSkuConvertImpl implements ProductSkuConvert {

    @Override
    public ProductSku toEntity(ProductSkuDTO productSkuDTO) {
        if ( productSkuDTO == null ) {
            return null;
        }

        ProductSku productSku = new ProductSku();

        productSku.setId( productSkuDTO.getId() );
        productSku.setAttributeSymbolList( productSkuDTO.getAttributeSymbolList() );
        productSku.setName( productSkuDTO.getName() );
        productSku.setSellPrice( productSkuDTO.getSellPrice() );
        productSku.setCostPrice( productSkuDTO.getCostPrice() );
        productSku.setStock( productSkuDTO.getStock() );
        productSku.setIsEnable( productSkuDTO.getIsEnable() );
        productSku.setStockWarn( productSkuDTO.getStockWarn() );

        return productSku;
    }

    @Override
    public List<ProductSkuVO> toVo(List<ProductSku> productSkuList) {
        if ( productSkuList == null ) {
            return null;
        }

        List<ProductSkuVO> list = new ArrayList<ProductSkuVO>( productSkuList.size() );
        for ( ProductSku productSku : productSkuList ) {
            list.add( productSkuToProductSkuVO( productSku ) );
        }

        return list;
    }

    protected ProductSkuVO productSkuToProductSkuVO(ProductSku productSku) {
        if ( productSku == null ) {
            return null;
        }

        ProductSkuVO productSkuVO = new ProductSkuVO();

        productSkuVO.setId( productSku.getId() );
        productSkuVO.setProductId( productSku.getProductId() );
        productSkuVO.setAttributeSymbolList( productSku.getAttributeSymbolList() );
        productSkuVO.setName( productSku.getName() );
        productSkuVO.setSellPrice( productSku.getSellPrice() );
        productSkuVO.setCostPrice( productSku.getCostPrice() );
        productSkuVO.setStock( productSku.getStock() );
        productSkuVO.setIsEnable( productSku.getIsEnable() );

        return productSkuVO;
    }
}
