package com.zx.mal.marketing.dubbo;

import com.zx.mal.marketing.model.dto.ProductRecommendedDTO;
import com.zx.mal.marketing.service.ProductRecommendedService;
import com.zx.mall.marketing.api.ProductRecommendDubboService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

/**
 * @author zhangxin
 * @date 2023/1/3 19:39
 */
@DubboService
public class ProductRecommendDubboServiceImpl implements ProductRecommendDubboService {

    @Resource
    private ProductRecommendedService productRecommendedService;

    @Override
    public Boolean setProductRecommend(Long productId) {
        ProductRecommendedDTO productRecommendedDTO = new ProductRecommendedDTO();
        productRecommendedDTO.setProductIdList(Collections.singletonList(productId));
        return productRecommendedService.save(productRecommendedDTO);
    }

    @Override
    public void delProductRecommend(Long id) {
        productRecommendedService.delete(id);
    }

    @Override
    public Boolean getProductRecommend(Long productId) {
       return  productRecommendedService.getProductIsRecommended(productId);
    }
}
