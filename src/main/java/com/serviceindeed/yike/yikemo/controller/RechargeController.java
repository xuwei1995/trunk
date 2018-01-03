package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.Card;
import com.serviceindeed.yike.yikemo.domain.Recharge;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.RechargeService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("auth/recharge")
public class RechargeController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String TAG = "RechargeController";
    @Autowired
    RechargeService rechargeService;
    /**
     * 查询充值历史
     *@Author xw
     *@Date 2017/12/16 16:33
     */
    @RequestMapping("/getAllRecharges")
    @ResponseBody
    public Map<String,Object> getAllRecharges(HttpPages httpPages, Recharge recharge){
        try {
            return  rechargeService.getAllRecharges(httpPages,recharge);
        }catch (NullPointerException e)
        {
            log.error(e.getMessage(),e);
            e.printStackTrace();
            return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            return  YiKeMoHelper.getInstance().getFailPageQueryJson(httpPages.getDraw());
        }

    }
}
