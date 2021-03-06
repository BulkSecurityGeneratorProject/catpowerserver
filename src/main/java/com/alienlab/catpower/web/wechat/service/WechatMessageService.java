package com.alienlab.catpower.web.wechat.service;

import com.alienlab.catpower.domain.BuyCourse;

/**
 * Created by 橘 on 2017/5/14.
 */
public interface WechatMessageService {
    //下课时，学员对教练评价的消息推送
    void sendEvalCoachMsg(Long scheId) throws Exception;

    //下课时，教练给学员建议的消息推送
    void sendOverClassMsg(Long scheId) throws Exception;

    //学员预约时，教练对预约进行回复的推送
    void sendAppointMsg(Long appointmentId) throws Exception;

    //教练回复后，学员收到教练回复的推送
    void sendAppointResultMsg(Long appointmentId) throws Exception;

    //购买成功推送给学员的购买成功消息
    void sendBuyClassSuccess(BuyCourse buyCourse) throws Exception;


}
