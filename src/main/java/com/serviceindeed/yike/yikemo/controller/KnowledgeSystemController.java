package com.serviceindeed.yike.yikemo.controller;

import com.github.pagehelper.Page;
import com.serviceindeed.yike.yikemo.domain.*;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.KnowledgeSystemService;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth/knowledgeSystem")
public class KnowledgeSystemController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    KnowledgeSystemService knowledgeSystemService;

    @RequestMapping("/getAllKnowledgeSystem")
    @ResponseBody
    public Map getAllKnowledgeSystem(KnowledgeSystem knowledgeSystem, HttpPages httpPages) {
        try {
            if (YiKeMoHelper.getInstance().isPaging(httpPages)) {
                Page<KnowledgeSystem> pages = knowledgeSystemService.queryPageKnowledgeSystem(httpPages, knowledgeSystem);
                return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw() + 1, pages);
            } else {
                List<KnowledgeSystem> knowledgeSystemList = knowledgeSystemService.queryKnowledgeSystemList(knowledgeSystem);
                return YiKeMoHelper.getInstance().notPagingResult(knowledgeSystemList, knowledgeSystemList.size());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }

    /**
     * 新增知识体系信息
     *
     * @param knowledgeSystem
     * @return
     */
    @RequestMapping("/addKnowledgeSystem")
    @ResponseBody
    public Map addKnowledgeSystem(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, KnowledgeSystem knowledgeSystem) {
        try {
            //设置基础信息
            YiKeMoHelper.getInstance().createHelper(knowledgeSystem, userToken.getUserId(), "",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT), "");
            //新增时默认启用
            knowledgeSystem.setIsEnable("1");
            knowledgeSystemService.saveKnowledgeSystem(knowledgeSystem);
            return YiKeMoHelper.getInstance().successJsonResultMap(null);
        } catch (Exception e) {
            log.error(" addKnowledgeSystem:新增知识体系失败:", e);
            e.printStackTrace();
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }

    /**
     * 编辑知识体系信息
     *
     * @param knowledgeSystem
     * @return
     */
    @RequestMapping("/editKnowledgeSystem")
    @ResponseBody
    public Map editKnowledgeSystem(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, KnowledgeSystem knowledgeSystem) {
        try {
            //判断判断是否将已存在子类的大类改为子类
            KnowledgeSystem knowledgeSystemEdit=knowledgeSystemService.hasChild(userToken,headers,knowledgeSystem);
            //是,返回，给出提示
            if(knowledgeSystemEdit==null){
                return YiKeMoHelper.getInstance().errorJsonResultMap(null,Constant.SYSTEM_MSG_002);
            }
           // 否
            else{
                knowledgeSystemService.updateKnowledgeSystem(knowledgeSystem);
            }
            return YiKeMoHelper.getInstance().successJsonResultMap(null);
        } catch (Exception e) {
            log.error("editKnowledgeSystem--知识体系修改失败:", e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
}
