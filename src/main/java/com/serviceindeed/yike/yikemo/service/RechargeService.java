package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.Card;
import com.serviceindeed.yike.yikemo.domain.CardActive;
import com.serviceindeed.yike.yikemo.domain.Recharge;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.RechargeMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class RechargeService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RechargeMapper rechargeMapper;

    public Map<String,Object> getAllRecharges(HttpPages httpPages, Recharge recharge) {
        if(YiKeMoHelper.getInstance().isPaging(httpPages))//分页
        {
            Page<Recharge> page= PageHelper.offsetPage(httpPages.getStart(),
                    httpPages.getLength()).doSelectPage(() -> rechargeMapper.getAllRecharges(recharge));
            return  YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw()+1,page);
        }
        else {
            List<Recharge> rechargeList=rechargeMapper.getAllRecharges(recharge);
            return  YiKeMoHelper.getInstance().notPagingResult(rechargeList,rechargeList.size());
        }

    }
}
