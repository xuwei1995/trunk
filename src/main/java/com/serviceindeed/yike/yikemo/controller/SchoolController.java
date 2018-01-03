package com.serviceindeed.yike.yikemo.controller;

import com.github.pagehelper.Page;
import com.serviceindeed.yike.yikemo.domain.*;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.SchoolService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Author:lxl
 * create on:2017-12-12
 */
@Controller
@RequestMapping("auth/school")
public class SchoolController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SchoolService schoolService;

    /**
     * 分页查询学校
     * 获取前端过来的参数,下面三个参数是 dataTable默认的，不要随便更改
     *
     * @param httpPages// 分页参数
     * @param school
     * @return
     */
    @RequestMapping("/getAllSchool")
    @ResponseBody
    public Map<String, Object> getAllSchool(HttpPages httpPages, School school) {
        try {
            //分页不分页
            //分页
            if (YiKeMoHelper.getInstance().isPaging(httpPages)) {
                Page<School> page = schoolService.queryPageSchool(httpPages, school);
                return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw() + 1, page);
            } else {
                List<School> schoolList = schoolService.querySchoolList(school);
                return YiKeMoHelper.getInstance().notPagingResult(schoolList, schoolList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAllSchool--查询数据失败:", e);
            if (!YiKeMoHelper.getInstance().isPaging(httpPages)) {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
            }
            return YiKeMoHelper.getInstance().getFailPageQueryJson(httpPages.getDraw());
        }
    }

    /**
     * 新增学校信息
     *
     * @param school
     * @return
     */
    @RequestMapping("/addSchool")
    @ResponseBody
    public Map addSchool(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, School school) {
        try {
            //校验同一机构下学校名称是否已经存在
            Integer isExist = schoolService.checkSchoolIsExist(school);
            if (isExist > 0) {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_010, school.getSchoolName() + "学校");
            }
            //设置基础信息
            YiKeMoHelper.getInstance().createHelper(school, userToken.getUserId(), "",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT), "");
            schoolService.saveSchool(school);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" addSchool:新增学校失败:", e);
        }
        return YiKeMoHelper.getInstance().successJsonResultMap(null);
    }

    /**
     * 编辑学校信息
     *
     * @param school
     * @return
     */
    @RequestMapping("/editSchool")
    @ResponseBody
    public Map editSchool(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, School school) {

        try {
            Integer isExist = schoolService.checkSchoolIsExist(school);
            if (isExist > 0) {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_010, school.getSchoolName() + "学校");
            }
            //设置基础信息
            YiKeMoHelper.getInstance().updateHelper(school, userToken.getUserId(), "",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT), "");
            schoolService.updateSchool(school);

        } catch (Exception e) {
            log.error("editSchool--学校修改失败:", e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
        return YiKeMoHelper.getInstance().successJsonResultMap(null);
    }
}
