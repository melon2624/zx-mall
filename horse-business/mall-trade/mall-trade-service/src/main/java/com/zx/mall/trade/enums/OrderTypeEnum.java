package com.zx.mall.trade.enums;

import com.zx.framework.common.model.IDict;

/**
 * 订单类型枚举
 *
 * @author zhangxin
 * @date 2023/1/31 16:18
 */
public enum OrderTypeEnum  implements IDict<Integer> {

    // 订单类型枚举
    NORMAL(1, "普通订单"),
    FREE(2, "免费订单"),
    SECKILL(3, "秒杀订单"),
    VIRTUAL(4, "虚拟商品订单"),
    ;

    OrderTypeEnum(Integer code, String text) {
        init(code, text);
    }

}
