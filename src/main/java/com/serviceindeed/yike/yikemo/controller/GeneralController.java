package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.General;
import com.serviceindeed.yike.yikemo.service.GeneralService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth/general")
public class GeneralController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GeneralService generalService;
    /*
   *查询General表KEY_TYPE
   *@Author xw
   *@Date 2017/12/14 11:10
   */
    @RequestMapping("/getGeneralByKeyType")
    @ResponseBody
    public Map<String,Object> getGeneralByKeyType(@RequestParam("keyType")String queryStatus)
    {
        Map<String,Object> map=null;
        List<General> statusList=null;
        if(!StringUtils.isEmpty(queryStatus))
        {
            try{
                statusList = generalService.queryGeneralByKeyType(queryStatus);

                return  YiKeMoHelper.getInstance().successJsonResultMap(statusList,Constant.QUERY_SUCCESS);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                log.error("查询General表失败",e);
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
            }
        }else {
            //请求参数为空
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);
        }
    }
}
