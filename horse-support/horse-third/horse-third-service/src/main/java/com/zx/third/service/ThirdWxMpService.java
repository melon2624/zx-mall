package com.zx.third.service;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangxin
 * @date 2023/1/30 17:31
 */
@Slf4j
@Service("thirdWxMpService")
public class ThirdWxMpService {


    @Resource
    private WxMpService wxMpService;

    public Boolean sendTemplateMessage(String appId, String templateId, String openId, String url, String first, String remark, String[] keywords, Map<String, String> keywordMap) {

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().templateId(templateId).toUser(openId).url(url).build();
        templateMessage.addData(new WxMpTemplateData("first", first));
        templateMessage.addData(new WxMpTemplateData("remark", remark));
        if (ArrayUtils.isNotEmpty(keywords)){
            for (int index = 0; index < keywords.length; index++) {
                templateMessage.addData(new WxMpTemplateData("keyword" + (index + 1), keywords[index]));
            }
        }
        if (MapUtils.isNotEmpty(keywordMap)) {
            Set<Map.Entry<String, String>> entrySet = keywordMap.entrySet();
            entrySet.stream().forEach(entry -> {
                templateMessage.addData(new WxMpTemplateData(entry.getKey(), entry.getValue()));
            });
        }

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            return true;
        }catch (WxErrorException e){
            log.error("发送微信公众号模板消息异常", e);
            return false;
        }


    }
}
