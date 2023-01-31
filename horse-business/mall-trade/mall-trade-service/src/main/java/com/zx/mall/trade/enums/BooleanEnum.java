package com.zx.mall.trade.enums;

import com.zx.framework.common.model.IDict;

/**
 * 布尔值枚举
 *
 * @author zhangxin
 * @date 2023/1/31 15:47
 */
public enum BooleanEnum  implements IDict<Boolean> {

    // 退款单状态
    NO(false, "否"),
    YES(true, "是"),
    ;

    BooleanEnum(Boolean code, String text) {
        init(code, text);
    }

}
