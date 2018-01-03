package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.*;
import com.serviceindeed.yike.yikemo.domain.helper.CardTypeCount;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.CardActiveService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("auth/card")
public class CardActiveController {
    @Autowired
    CardActiveService cardActiveService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * @Param httpPages 分页参数对象
     * @Param cardActive CardActive对象
     * @Author xw
     * @Date 2017/12/16 11:37
     */
    @RequestMapping(value = "/getAllCardActive")
    @ResponseBody
    public Map<String, Object> getAllCardActive(@AuthenticationPrincipal User userToken,@RequestHeader HttpHeaders headers,
                                                @Validated HttpPages httpPages,@Validated CardActive cardActive) {
       try {
           return cardActiveService.getAllCardActive(httpPages, cardActive);
       }catch (Exception e)
       {
           log.error(e.getMessage(),e);
           return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
       }
    }
    /**
     * 所有会员卡类型
     * @Author xw
     * @Date 2017/12/16 13:20
     */
    @RequestMapping("/getCardType")
    @ResponseBody
    public Map<String, Object> getCardType() {
        try {
            return cardActiveService.getCardType();
        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }

    //开卡操作接口
    @RequestMapping("/activateCard")
    @ResponseBody
    public Map<String, Object> activateCard(@RequestHeader HttpHeaders headers,@Validated CardActive cardActive, @AuthenticationPrincipal User userToken) {
        try {
            return cardActiveService.activateCard(headers,userToken,cardActive);
        } catch (Exception e) {
                 e.printStackTrace();
                log.error(e.getMessage(), e);
                if (e instanceof DataIntegrityViolationException) {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
            }
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }

    /**
     * 作废会员卡
     *
     * @Author xw
     * @Date 2017/12/16 12:26
     * @Param cardId 会员卡id
     */
    @RequestMapping("/cardCancelByCardId")
    @ResponseBody
    public Map<String, Object> cardCancel(@RequestHeader HttpHeaders headers,Card card,@AuthenticationPrincipal User userToken) {
        try {
            return cardActiveService.cardCancel(headers,card,userToken);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    /**
     * 批量操作会员卡作废
     *@Author xw
     *@Date 2017/12/20 14:21
     */
    @RequestMapping("/cardActiveCancelAndCards")
    @ResponseBody
    public Map<String, Object> cardActiveCancelAndCards(@RequestHeader HttpHeaders headers,CardActive cardActive,@AuthenticationPrincipal User userToken) {
        try {
            return cardActiveService.cardActiveCancelAndCards(headers,userToken,cardActive);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }

    /**
     * 查询所有会员卡
     *
     * @Author xw
     * @Date 2017/12/16 16:19
     */
    @RequestMapping("/getAllCards")
    @ResponseBody
    public Map<String, Object> getAllCards(HttpPages httpPages, Card card) {
        try {
            return cardActiveService.getAllCards(httpPages, card);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            if(e instanceof BadSqlGrammarException)
            {
                return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_006);
            }
            if(e instanceof NullPointerException)
            {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002 );
            }

            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }

    /**
     * @Author xw
     * @Date 2017/12/18 14:41
     */
    @RequestMapping("/editCard")
    @ResponseBody
    public Map<String, Object> editCard(@RequestHeader HttpHeaders headers,@AuthenticationPrincipal User userToken, Card card  ) {
        try {
            return cardActiveService.editCard(headers,userToken,card);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    /**
     * 查询不同 未使用的会员卡类型的数量
     *
     * @param
     * @Author xw
     * @Date 2017/12/20 12:00
     */
    @RequestMapping("/getCardCounts")
    @ResponseBody
    public Map<String, Object> getCardCounts(Integer orgId) {
        try {
            return cardActiveService.getCardCounts(orgId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }

}
