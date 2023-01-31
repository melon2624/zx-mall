package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.mall.product.mapper.ProductDetailMapper;
import com.zx.mall.product.model.entity.ProductDetail;
import org.springframework.stereotype.Service;

/**
 * 商品详情表(ProductDetail)表服务实现类
 *
 * @author zhangxin
 * @date 2023/1/3 18:37
 */
@Service("productDetailService")
public class ProductDetailService extends ServiceImpl<ProductDetailMapper, ProductDetail> {


    public void update(Long productId, String detail) {
        lambdaUpdate().eq(ProductDetail::getProductId, productId).set(ProductDetail::getDetail, detail).update();
    }
}
