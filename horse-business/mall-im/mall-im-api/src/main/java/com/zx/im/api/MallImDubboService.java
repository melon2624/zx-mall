package com.zx.im.api;

import com.msb.im.api.dto.SendMessageDTO;

/**
 * 发送消息dubbo
 *
 * @author zhangxin
 * @date 2023/1/30 14:18
 */
public interface MallImDubboService {

    /**
     * 发送消息
     *
     * @param sendMessageDTO
     * @return 是否发送成功
     */
    Boolean sendMessage(SendMessageDTO sendMessageDTO);

}
