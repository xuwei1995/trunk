package com.serviceindeed.yike.yikemo.controller;

import com.github.pagehelper.Page;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.domain.Organization;
import com.serviceindeed.yike.yikemo.service.OrganizationService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 机构管理
 */

@Controller
@RequestMapping("auth/organization")
public class OrganizationController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrganizationService organizationService;

    /**
     * 获取前端过来的参数,下面三个参数是 dataTable默认的，不要随便更改
     *
     * @return
     */
    @RequestMapping("/getAllOrganization")
    @ResponseBody
    public Map<String, Object> getAllOrganization(@RequestHeader HttpHeaders headers, HttpPages httpPages, Organization organization) {
        try {
            //分页不分页
            //分页
            if (YiKeMoHelper.getInstance().isPaging(httpPages)) {
                Page<Organization> page= organizationService.queryPageOrganization(httpPages,organization);
                return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw() + 1, page);
            }else{
                List<Organization> organizationList = organizationService.queryOrganizationList(organization);
                return YiKeMoHelper.getInstance().notPagingResult(organizationList, organizationList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAllOrganization--查询数据失败:", e);
            if (!YiKeMoHelper.getInstance().isPaging(httpPages)) {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
            }
            return YiKeMoHelper.getInstance().getFailPageQueryJson(httpPages.getDraw());
        }
    }

    /**
     * 新增机构信息
     *
     * @param organization
     * @return
     */
    @RequestMapping("/addOrganization")
    @ResponseBody
    public Map addOrganization(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, Organization organization) {

        if (!StringUtils.isEmpty(organization.getOrgName()) && !StringUtils.isEmpty(organization.getOrgAbbrname())) {
            try {
                Integer isExist = organizationService.checkOrganizationIsExist(organization);
                if (isExist > 0) {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_010, organization.getOrgName() + "机构名称");
                }
                if (organizationService.checkOrgAbbrnameIsExist(organization) > 0) {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_010, organization.getOrgName() + "机构简称");
                }
                organization.setApplyBy("");
                organization.setApplyDate(new Date());
                organization.setAuditBy(userToken.getUserId());
                organization.setAuditDate(new Date());
                //设置基础信息
                YiKeMoHelper.getInstance().createHelper(organization,userToken.getUserId(),"",
                        YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT),"");
                organizationService.saveOrganization(organization);
                return YiKeMoHelper.getInstance().successJsonResultMap(null);
            } catch (Exception e) {
                log.error(" addOrganization:新增机构失败:", e);
                e.printStackTrace();
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
            }
        } else {

            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
        }

    }

    /**
     * 编辑机构信息
     *
     * @param organization
     * @return
     */
    @RequestMapping("/editOrganization")
    @ResponseBody
    public Map editOrganization(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, Organization organization) {
        if (!StringUtils.isEmpty(organization.getOrgName()) && !StringUtils.isEmpty(organization.getOrgAbbrname())) {
            try {
                Integer isExist = organizationService.checkOrganizationIsExist(organization);
                if (isExist > 0) {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_010, organization.getOrgName() + "机构名称");
                }
                if (organizationService.checkOrgAbbrnameIsExist(organization) > 0) {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_010, organization.getOrgName() + "机构简称");
                }
                //设置基础信息
                YiKeMoHelper.getInstance().updateHelper(organization,userToken.getUserId(),"",
                        YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT),"");
                organizationService.updateOrganization(organization);
                return YiKeMoHelper.getInstance().successJsonResultMap(null);
            } catch (Exception e) {
                log.error("editOrganization--机构修改失败:", e);
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
            }
        } else {
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
        }
    }


}