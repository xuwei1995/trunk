package com.serviceindeed.yike.yikemo.controller;

import com.github.pagehelper.Page;
import com.serviceindeed.yike.yikemo.domain.TempClassify;
import com.serviceindeed.yike.yikemo.domain.TempPraxis;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.TempService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.MyException;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth/temp")
public class TempController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TempService tempService;

    @RequestMapping("/getAllPraxis")
    @ResponseBody
    public Map getAllPraxis(HttpPages httpPages,TempPraxis tempPraxis){
        try {
            if (YiKeMoHelper.getInstance().isPaging(httpPages)) {
                Page<TempPraxis> page = tempService.queryPagePraxis(httpPages,tempPraxis);
                return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw() + 1, page);
            } else {
                List<TempPraxis> tempPraxisList = tempService.queryPraxisList(tempPraxis);
                return YiKeMoHelper.getInstance().notPagingResult(tempPraxisList, tempPraxisList.size());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    @RequestMapping("/getTempClassify")
    @ResponseBody
    public Map getTempClassify(){
        try {
            List<TempClassify> tempClassifyList =  tempService.getTempClassify();
            return YiKeMoHelper.getInstance().successJsonResultMap(tempClassifyList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    @RequestMapping("/savePraxis")
    @ResponseBody
    public Map savePraxis(@RequestHeader HttpHeaders headers, TempPraxis tempPraxis, @AuthenticationPrincipal User userToken){
        try {
            if (tempPraxis.getPraxisId() == null) { //新增
                if (tempPraxis.getSgf() == null) {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_011, "请上传sgf文件");
                }
            }
             tempService.savePraxis(tempPraxis,userToken,headers);
            return YiKeMoHelper.getInstance().successJsonResultMap(null);
        }
        catch (MyException e)
        {
            log.error(e.getMessage() ,e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_007,"文件");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }

    @RequestMapping("/getSgfFileInfo")
    @ResponseBody
    public Map<String,Object> getSgfFileInfo(@RequestParam("sgfName")String sgfName){
        try {
            //获取sgf文件信息
            String sgfInfo= tempService.getSgfFileInfo(sgfName);
            if(StringUtils.isNotEmpty(sgfInfo)){
                return YiKeMoHelper.getInstance().successJsonResultMap(sgfInfo,"获取sgf信息成功");
            }else{
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_013, sgfName + "文件");
            }
        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }
}
