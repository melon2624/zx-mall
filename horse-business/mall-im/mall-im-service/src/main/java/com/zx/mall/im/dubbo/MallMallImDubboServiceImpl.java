package com.zx.mall.im.dubbo;

import com.msb.im.api.MessageApi;
import com.msb.im.api.dto.SendMessageDTO;
import com.msb.im.api.enums.TicketTypeEnum;
import com.msb.im.api.result.ImApiResultEnum;
import com.msb.im.api.util.TicketUtil;
import com.msb.im.api.vo.ResultVO;
import com.zx.im.api.MallImDubboService;
import com.zx.mall.im.config.MallImConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhangxin
 * @date 2023/1/30 14:27
 */
@Slf4j
@DubboService
@Service
public class MallMallImDubboServiceImpl implements MallImDubboService {

    @Resource
    private MessageApi messageApi;

    @Resource
    private MallImConfig mallImConfig;

    @Override
    public Boolean sendMessage(SendMessageDTO sendMessageDTO) {
        log.info("发送单人消息参数 {}", sendMessageDTO);
        ResultVO resultVO = messageApi.sendMessage(sendMessageDTO, TicketUtil.ticket(mallImConfig.getClient(), sendMessageDTO.getFromId(), TicketTypeEnum.API_CURL_TICKET, System.currentTimeMillis(), mallImConfig.getSecret()), sendMessageDTO.getFromId());
        log.info("发送单人消息响应 {}", resultVO);
        return Objects.equals(ImApiResultEnum.API_CURL_SUCCESS.getCode(), resultVO.getCode());
    }
}
