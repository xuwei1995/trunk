package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.Grade;
import com.serviceindeed.yike.yikemo.mapper.GradeMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth/grade")
public class GradeController {
    @Autowired
    GradeMapper gradeMapper;
    /**
     * 获取段位接口
     *@Author xw
     *@Date 2017/12/15 21:05
     */
    @RequestMapping("getGradeName")
    @ResponseBody
    public Map getGradeName()
    {
        try {
            List<Grade> gradeNameList=gradeMapper.selectAllGradeName();


            return   YiKeMoHelper.getInstance().successJsonResultMap(gradeNameList, Constant.QUERY_SUCCESS);
        }
        catch (Exception e)
        {
            return   YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }
}
