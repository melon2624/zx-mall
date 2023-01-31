package com.zx.mal.marketing.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.framework.redis.RedisClient;
import com.zx.mal.marketing.entity.ProductRecommended;
import com.zx.mal.marketing.mapper.ProductRecommendedMapper;
import com.zx.mal.marketing.model.dto.ProductRecommendedDTO;
import com.zx.mall.product.api.duboo.ProductDubboService;
import com.zx.mall.product.api.model.ProductDO;
import jdk.nashorn.internal.runtime.options.Option;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.zx.mal.marketing.model.RedisKeyConstants.PRODUCT_RECOMMENDED;

/**
 * @author zhangxin
 * @date 2023/1/3 19:49
 */
@Service("productRecommendedService")
public class ProductRecommendedService extends ServiceImpl<ProductRecommendedMapper, ProductRecommended> {

    @DubboReference
    ProductDubboService productDubboService;


    @Resource
    RedisClient redisClient;

    public Boolean save(ProductRecommendedDTO productRecommendedDTO) {
        List<Long> productIdList = productRecommendedDTO.getProductIdList();
        List<ProductRecommended> productRecommendedList = productMapToProductRecommended(productIdList).collect(Collectors.toList());
        this.saveBatch(productRecommendedList);
        productIdList.forEach(this::deleteProductRecommendedCache);
        return true;
    }

    private void deleteProductRecommendedCache(Long productId) {
        redisClient.delete(PRODUCT_RECOMMENDED.concat(String.valueOf(productId)));
    }

    public Boolean getProductRecommendedCache(Long productId) {
       return redisClient.get(PRODUCT_RECOMMENDED.concat(String.valueOf(productId)));
    }

    public Stream<ProductRecommended> productMapToProductRecommended(List<Long> productIdList) {

        return productIdList.stream().map(productId -> {

            Optional<ProductDO> productOptional = Optional.ofNullable(productDubboService.getProductById(productId));
            return productOptional.map(productDO -> {
                ProductDO product = productOptional.get();
                ProductRecommended productRecommendedNew = new ProductRecommended().setProductId(productId).setProductMainPicture(product.getMainPicture()).setProductName(product.getName()).setIsEnable(Boolean.TRUE);
                Optional<ProductRecommended> productRecommendedOptional = lambdaQuery().eq(ProductRecommended::getProductId, productRecommendedNew.getProductId()).oneOpt();
                productRecommendedOptional.ifPresent(productRecommended -> productRecommendedNew.setId(productRecommended.getId()));
                return productRecommendedNew;
            }).orElse(null);
        }).filter(Objects::nonNull);
    }

    public void delete(Long productId) {
        this.lambdaUpdate().eq(ProductRecommended::getProductId, productId).remove();
    }

    public Boolean getProductIsRecommended(Long productId) {

        return Optional.ofNullable(getProductRecommendedCache(productId)).orElseGet(
                () -> {
                    Boolean flag = lambdaQuery().eq(ProductRecommended::getProductId, productId)
                            .eq(ProductRecommended::getIsEnable, Boolean.TRUE)
                            .oneOpt()
                            .map(productRecommended -> Boolean.TRUE)
                            .orElse(Boolean.FALSE);
                    putProductRecommendedCache(productId, flag);
                    return flag;
                }
        );

    }

    private void putProductRecommendedCache(Long productId, Boolean flag) {
        redisClient.set(PRODUCT_RECOMMENDED.concat(String.valueOf(productId)), flag, 10, TimeUnit.DAYS);
    }
}
