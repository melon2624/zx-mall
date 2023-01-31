package com.zx.mall.product.dubbo;

import com.zx.mall.product.api.duboo.ProductDubboService;
import com.zx.mall.product.api.model.ProductDO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author zhangxin
 * @date 2023/1/6 19:25
 */
@DubboService
public class ProductDubboServiceImpl  implements ProductDubboService {
    @Override
    public ProductDO getProductById(Long id) {
        return null;
    }
}
