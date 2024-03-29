package com.zx.mall.trade.enums;

import com.zx.framework.common.model.IDict;
import com.zx.mall.trade.service.activity.impl.Buy;

import java.util.Arrays;
import java.util.Objects;

/**
 * 订单商品活动类型枚举
 *
 * @author zhangxin
 * @date 2023/1/31 15:51
 */
public enum ActivityTypeEnum implements IDict<Integer> {

    // 订单商品活动类型枚举
    NORMAL(1, "正常购买", Buy.class),
    FREE(2, "免费领取", Buy.class),
    SECKILL(3, "秒杀", Seckill.class),
    ;

    private final Class<? extends OrderProductActivityHandle> activityHandle;

    ActivityTypeEnum(Integer code, String text, Class<? extends OrderProductActivityHandle> activityHandle) {
        init(code, text);
        this.activityHandle = activityHandle;
    }

    /**
     * 根据活动类型，获取对应的处理类
     *
     * @param activityType：活动类型
     * @return com.msb.mall.trade.service.activity.OrderProductActivityHandle
     * @author peng.xy
     * @date 2022/4/18
     */
    public static OrderProductActivityHandle getOrderProductActivityHandle(final int activityType) {
        ActivityTypeEnum activityTypeEnum = Arrays.stream(ActivityTypeEnum.values()).filter(handleEnum ->
                Objects.equals(handleEnum.getCode(), activityType)
        ).findFirst().orElseThrow(() -> new BizException(TradeExceptionCodeEnum.ORDER_PRODUCT_ACTIVITY_EXCEPTION));
        return SpringContextUtil.getBean(activityTypeEnum.getActivityHandle());
    }

    public Class<? extends OrderProductActivityHandle> getActivityHandle() {
        return activityHandle;
    }

}
