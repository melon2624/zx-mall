package com.zx.mall.product.service.convert;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zx.mall.product.model.dto.admin.ProductModifyDTO;
import com.zx.mall.product.model.entity.Product;
import com.zx.mall.product.model.vo.admin.ProductModifyVO;
import com.zx.mall.product.model.vo.admin.ProductVO;
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
public class ProductConvertImpl implements ProductConvert {

    @Override
    public Product toEntity(ProductModifyDTO productQueryDTO) {
        if ( productQueryDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( productQueryDTO.getName() );
        product.setCategoryId( productQueryDTO.getCategoryId() );
        product.setRemoteAreaPostage( productQueryDTO.getRemoteAreaPostage() );
        product.setSingleBuyLimit( productQueryDTO.getSingleBuyLimit() );
        product.setIsEnable( productQueryDTO.getIsEnable() );
        product.setRemark( productQueryDTO.getRemark() );
        product.setProductType( productQueryDTO.getProductType() );

        return product;
    }

    @Override
    public ProductModifyVO toModifyVO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductModifyVO productModifyVO = new ProductModifyVO();

        if ( product.getId() != null ) {
            productModifyVO.setId( String.valueOf( product.getId() ) );
        }
        productModifyVO.setName( product.getName() );
        productModifyVO.setCategoryId( product.getCategoryId() );
        productModifyVO.setRemark( product.getRemark() );
        productModifyVO.setIsEnable( product.getIsEnable() );
        productModifyVO.setRemoteAreaPostage( product.getRemoteAreaPostage() );
        productModifyVO.setSingleBuyLimit( product.getSingleBuyLimit() );
        productModifyVO.setProductType( product.getProductType() );

        return productModifyVO;
    }

    @Override
    public Page<ProductVO> toVo(Page<Product> productPage) {
        if ( productPage == null ) {
            return null;
        }

        Page<ProductVO> page = new Page<ProductVO>();

        page.setPages( productPage.getPages() );
        page.setRecords( productListToProductVOList( productPage.getRecords() ) );
        page.setTotal( productPage.getTotal() );
        page.setSize( productPage.getSize() );
        page.setCurrent( productPage.getCurrent() );
        page.setSearchCount( productPage.isSearchCount() );
        page.setOptimizeCountSql( productPage.isOptimizeCountSql() );
        List<OrderItem> list1 = productPage.getOrders();
        if ( list1 != null ) {
            page.setOrders( new ArrayList<OrderItem>( list1 ) );
        }
        page.setCountId( productPage.getCountId() );
        page.setMaxLimit( productPage.getMaxLimit() );

        return page;
    }

    protected ProductVO productToProductVO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductVO productVO = new ProductVO();

        productVO.setId( product.getId() );
        productVO.setName( product.getName() );
        productVO.setCategoryId( product.getCategoryId() );
        productVO.setStartingPrice( product.getStartingPrice() );
        productVO.setMainPicture( product.getMainPicture() );
        productVO.setRemoteAreaPostage( product.getRemoteAreaPostage() );
        productVO.setSingleBuyLimit( product.getSingleBuyLimit() );
        productVO.setIsEnable( product.getIsEnable() );
        productVO.setRemark( product.getRemark() );
        productVO.setProductType( product.getProductType() );

        return productVO;
    }

    protected List<ProductVO> productListToProductVOList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductVO> list1 = new ArrayList<ProductVO>( list.size() );
        for ( Product product : list ) {
            list1.add( productToProductVO( product ) );
        }

        return list1;
    }
}
