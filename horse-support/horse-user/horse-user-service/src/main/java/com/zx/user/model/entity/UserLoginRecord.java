package com.zx.user.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zx.framework.mysql.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author zhangxin
 * @date 2022/12/14 10:39
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_login_record")
public class UserLoginRecord  extends BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录下放token
     */
    private String token;

    /**
     *  系统id，严选商城，学习平台
     */
    private Integer systemId;

    /**
     * 客户端id
     */
    private Integer clientId;

    /**
     * ip
     */
    private String ip;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;

}
