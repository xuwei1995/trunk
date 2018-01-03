package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.Card;
import com.serviceindeed.yike.yikemo.domain.CardActive;
import com.serviceindeed.yike.yikemo.domain.RechargeType;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.CardTypeCount;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.CardActiveMapper;
import com.serviceindeed.yike.yikemo.mapper.CardMapper;
import com.serviceindeed.yike.yikemo.mapper.RechargeTypeMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CardActiveService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CardActiveMapper cardActiveMapper;
    @Autowired
    RechargeTypeMapper rechargeTypeMapper;
    @Autowired
    CardMapper cardMapper;

    //开卡操作分页查询
    public Map<String, Object> getAllCardActive(HttpPages httpPages, CardActive cardActive) {
          if (YiKeMoHelper.getInstance().isPaging(httpPages)){
                Page<CardActive> page = PageHelper.offsetPage(httpPages.getStart(),httpPages.getLength()).doSelectPage(()
                        -> cardActiveMapper.getAllCardActive(cardActive));
                return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw()+1, page);
            } else {
                List<CardActive> cardActiveList = cardActiveMapper.getAllCardActive(cardActive);

                return YiKeMoHelper.getInstance().notPagingResult(cardActiveList, cardActiveList.size());
            }
    }

    //查卡类型
    public Map<String, Object> getCardType() {
        List<RechargeType> rechargeTypeList = rechargeTypeMapper.getCardType();
        return YiKeMoHelper.getInstance().successJsonResultMap(rechargeTypeList, Constant.QUERY_SUCCESS);
    }

    //开卡操作
    @Transactional
    public Map<String, Object> activateCard(HttpHeaders headers,User userToken, CardActive cardActive) {
            String platform= YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,headers.USER_AGENT);
            cardActive.setStatus(Constant.CARDACTIVATE_STATUS_CR);//开卡状态
            cardActive.setCardChannel(Constant.CARDACTIVE_CHANNEL_BK);//平台开卡
            YiKeMoHelper.getInstance().createHelper(cardActive,userToken.getUserId(),null,platform,null);
        cardActiveMapper.insertSelective(cardActive);
            for (int i = 0; i < cardActive.getCardNumber(); i++) {
                Card card = new Card();
                String cardNo = YiKeMoHelper.getInstance().getCardNo(cardActiveMapper, "CARD_NO", "C");//获取cardNo流水
                card.setCardNo(cardNo);
                card.setCardPassword(cardActive.getCardPassword());
                card.setCardActivateId(cardActive.getCardActivateId());
                card.setRechargeType(cardActive.getRechargeType());
                card.setOrgId(cardActive.getOrgId());
                card.setSchoolId(cardActive.getSchoolId());
                card.setStatus(Constant.CARD_STATUS_CR);
                YiKeMoHelper.getInstance().createHelper(card,userToken.getUserId(),null,platform,null);
                cardMapper.insertSelective(card);
            }
            return YiKeMoHelper.getInstance().successJsonResultMap(null, "开卡操作成功");

    }

    //作废会员卡
    @Transactional
    public Map<String, Object> cardCancel( HttpHeaders headers,Card card,User userToken) {
            String userAgent=   YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,headers.USER_AGENT);//取出浏览器等的header
            YiKeMoHelper.getInstance().updateHelper(card,userToken.getUserId(),null,userAgent,null);
            card.setStatus(Constant.CARD_STATUS_CL);
            if (card.getCardId() == null) {
                if(card.getCardIds().length>0)
                {
                    //批量作废CARD
                    for (int i = 0; i < card.getCardIds().length; i++) {
                        card.setCardId((long)card.getCardIds()[i]);
                        if(cardMapper.cardCancel(card)<=0)
                        {
                            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_008);
                        }
                    }
                    return  YiKeMoHelper.getInstance().successJsonResultMap(Constant.OPERATION_SUCCESS);
                }else {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);
                }
            } else {
                //单条作废
                int i= cardMapper.cardCancel(card);
                if(i>0)
                {
                    return YiKeMoHelper.getInstance().successJsonResultMap( Constant.OPERATION_SUCCESS);
                }else {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_008);
                }
            }
    }
    //分页查询所有会员卡
    public Map<String, Object> getAllCards(HttpPages httpPages, Card card) {
        if (YiKeMoHelper.getInstance().isPaging(httpPages)) {
            Page<CardActive> page = PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(() -> cardMapper.getAllCards(card));
            return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw() + 1, page);
        } else {
            List<Card> cardList = cardMapper.getAllCards(card);
            return YiKeMoHelper.getInstance().notPagingResult(cardList, cardList.size());
        }

    }
    //修改会员卡
    @Transactional
    public Map<String, Object> editCard(HttpHeaders headers,User userToken,Card card) {
        if (card.getCardId() == null) {
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_004);
        } else {
            String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,headers.USER_AGENT);  //取出浏览器等的header
            YiKeMoHelper.getInstance().updateHelper(card,userToken.getUserId(),null,userAgent,null);
            cardMapper.updateByPrimaryKeySelective(card);
            return YiKeMoHelper.getInstance().successJsonResultMap(null, Constant.OPERATION_SUCCESS);
        }
    }
    //查看不同类型不同状态会员卡的数量名称
    public Map<String, Object> getCardCounts(Integer orgId) {
        if(orgId==null)
        {
            return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);
        }
        List<CardTypeCount>  cardStatusLists=  cardMapper.selectCardStatus();
        List<CardTypeCount> cardTypeLists=cardMapper.selectCardTypes();
       /* Stream.of(cardStatusLists,cardTypeLists).flatMap(Collection::stream).forEach(
                System.out::println);*/
        List<CardTypeCount> queryObj=new ArrayList<>(); //定义 cardStatus*cardType 种名称和code的集合
        cardStatusLists.stream().forEach(cardstatus->{
                cardTypeLists.stream().forEach(cardType->{
                CardTypeCount cardTypeCount=new CardTypeCount();
                cardTypeCount.setStatus(cardstatus.getStatus());
                cardTypeCount.setStatusValue(cardstatus.getStatusValue());
                cardTypeCount.setRechargeType(cardType.getRechargeType());
                cardTypeCount.setRechargeName(cardType.getRechargeName());
                cardTypeCount.setOrgId((long)orgId);
                queryObj.add(cardTypeCount);
            }); //取值结束
        });
        queryObj.stream().forEach(
                q->q.setNum(cardMapper.getCardCounts(q)) //最后拿到num
        );
        return YiKeMoHelper.getInstance().successJsonResultMap(queryObj, Constant.QUERY_SUCCESS);
    }
    //通过cardActiveId 作废cardActive 和 card
   @Transactional
    public Map<String,Object> cardActiveCancelAndCards( HttpHeaders headers,User userToken,CardActive cardActive) {
         String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,headers.USER_AGENT); //取出浏览器等的header
           if(cardActive.getCardActivateId()!=null) //cardActivateId必有参数
            {
                List<Card> cardList= cardMapper.checkIsHaveAlreadyUsedCard(cardActive.getCardActivateId());
                if(cardList.size()>0)//找到CardActiveId为前台传过来的 CardActiveId的card已经使用   所以不能废除
                {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_005);
                }else {    //ardActiveId  的cardActiveId
                    cardActive.setStatus(Constant.CARD_STATUS_CL);
                    YiKeMoHelper.getInstance().updateHelper(cardActive,userToken.getUserId(),null,userAgent,null);
                    if(cardActiveMapper.cardActiveCancel(cardActive)>0)
                    {
                        Card card =new Card();
                        card.setStatus(Constant.CARD_STATUS_CL);
                        card.setCardActivateId(cardActive.getCardActivateId());
                        YiKeMoHelper.getInstance().updateHelper(card,userToken.getUserId(),null,userAgent,null);
                        cardMapper.cardActiveCancelAndCards(card);
                        return YiKeMoHelper.getInstance().successJsonResultMap(null,Constant.OPERATION_SUCCESS);
                    }else
                        {
                        return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_005);
                        }
                }
            }else {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);//没有传参
            }
   }
}
