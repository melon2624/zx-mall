package com.zx.mall.product.api.duboo;

import com.zx.mall.product.api.model.ProductDO;

/**
 * 商品dubbo服务
 *
 * @author zhangxin
 * @date 2023/1/6 17:50
 */
public interface ProductDubboService {



    ProductDO getProductById(Long id);


}
