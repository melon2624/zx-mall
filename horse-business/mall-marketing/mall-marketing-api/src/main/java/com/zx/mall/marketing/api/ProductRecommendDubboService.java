package com.zx.mall.marketing.api;

/**
 * @author zhangxin
 * @date 2023/1/3 19:33
 */
public interface ProductRecommendDubboService  {

    /**
     * 设置商品为推荐商品
     *
     * @param productId 商品id
     * @return Boolean
     */
    Boolean setProductRecommend(Long productId);

    /**
     * 删除
     * @param productId
     * @return
     */
    void delProductRecommend(Long productId);

    /**
     * 查询商品是否为推荐商品
     *
     * @param productId 商品id
     * @return Boolean
     */
    Boolean getProductRecommend(Long productId);
}
