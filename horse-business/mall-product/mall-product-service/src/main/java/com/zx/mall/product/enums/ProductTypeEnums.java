package com.zx.mall.product.enums;

import com.zx.framework.common.model.IDict;

/**
 * @author zhangxin
 * @date 2023/1/3 16:23
 */
public enum ProductTypeEnums implements IDict<Integer> {

    //
    REAL(1, "实物商品"),
    VIRTUAL(2, "虚拟商品"),
    ;

    ProductTypeEnums(Integer code, String text) {
        init(code, text);
    }

}
