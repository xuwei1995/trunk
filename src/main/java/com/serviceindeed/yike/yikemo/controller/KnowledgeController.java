package com.serviceindeed.yike.yikemo.controller;
import com.github.pagehelper.Page;
import com.serviceindeed.yike.yikemo.domain.KnowledgePoint;
import com.serviceindeed.yike.yikemo.domain.KnowledgePointDetail;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.KnowledgeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
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
@RequestMapping("auth/knowledge")
public class KnowledgeController {
    @Autowired
    KnowledgeService knowledgeService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/getAllKnowledgePoints")
    @ResponseBody
    public Map getAllKnowledgePoints(KnowledgePoint knowledgePoint, HttpPages httpPages) {
        try {
            //分页不分页
            //分页
            if(YiKeMoHelper.getInstance().isPaging(httpPages)){
                Page<KnowledgePoint> pages=knowledgeService.queryPageKnowledgePoint(httpPages,knowledgePoint);
                return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw() + 1, pages);
            }
            //不分页
            else{
                List<KnowledgePoint> knowledgePointList=knowledgeService.queryKnowledgePointList(knowledgePoint);
                return  YiKeMoHelper.getInstance().notPagingResult(knowledgePointList,knowledgePointList.size());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    /**
     * 新增知识点信息
     *
     * @param knowledgePoint
     * @return
     */
    @RequestMapping("/addKnowledgePoint")
    @ResponseBody
    public Map addKnowledgePoint(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, KnowledgePoint knowledgePoint) {
        try {
            //设置基础信息
            YiKeMoHelper.getInstance().createHelper(knowledgePoint,userToken.getUserId(),"",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT),"");
             knowledgeService.saveKnowledgePoint(knowledgePoint);
             //返回刚保存的知识点信息
            KnowledgePoint knowledgePointQuery=new KnowledgePoint();
            knowledgePointQuery.setKnowledgePointId(knowledgePoint.getKnowledgePointId());
            List<KnowledgePoint> knowledgePointList=knowledgeService.queryKnowledgePointList(knowledgePointQuery);
            return  YiKeMoHelper.getInstance().successJsonResultMap(knowledgePointList.get(0));
        } catch (Exception e) {
            log.error(" addKnowledgePoints:新增知识点失败:", e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }

    /**
     * 编辑知识点信息
     *
     * @param knowledgePoint
     * @return
     */
    @RequestMapping("/editKnowledgePoint")
    @ResponseBody
    public Map editKnowledgePoints(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, KnowledgePoint knowledgePoint) {
        try {
            //设置基础信息
            YiKeMoHelper.getInstance().updateHelper(knowledgePoint,userToken.getUserId(),"",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT),"");
            knowledgeService.updateKnowledgePoint(knowledgePoint);
            //返回刚修改的知识点信息
            KnowledgePoint knowledgePointQuery=new KnowledgePoint();
            knowledgePointQuery.setKnowledgePointId(knowledgePoint.getKnowledgePointId());
            List<KnowledgePoint> knowledgePointList=knowledgeService.queryKnowledgePointList(knowledgePointQuery);
            return  YiKeMoHelper.getInstance().successJsonResultMap(knowledgePointList.get(0));
        } catch (Exception e) {
            log.error("editKnowledgePoints--知识点修改失败:", e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    @RequestMapping("/getAllKnowledgePointDetail")
    @ResponseBody
    public Map getAllKnowledgePointDetail(KnowledgePointDetail knowledgePointDetail, HttpPages httpPages) {
        try {
            //分页不分页
            //分页
            if(YiKeMoHelper.getInstance().isPaging(httpPages)) {
                Page<KnowledgePointDetail> pages=knowledgeService.queryPageKnowledgePointDetail(httpPages, knowledgePointDetail);
                return  YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw()+1,pages);
            }
            //不分页
            else{
                List<KnowledgePointDetail> knowledgePointDetailList=  knowledgeService.queryKnowledgePointDetailList(knowledgePointDetail);
                return  YiKeMoHelper.getInstance().notPagingResult(knowledgePointDetailList,knowledgePointDetailList.size());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    /**
     * 新增知识详情信息
     *
     * @param knowledgePointDetail
     * @return
     */
    @RequestMapping("/addKnowledgePointDetail")
    @ResponseBody
    public Map addKnowledgePointDetail(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, KnowledgePointDetail knowledgePointDetail) {
        try {
            //设置基础信息
            YiKeMoHelper.getInstance().createHelper(knowledgePointDetail,userToken.getUserId(),"",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT),"");
              knowledgeService.saveKnowledgePointDetail(knowledgePointDetail);
            return YiKeMoHelper.getInstance().successJsonResultMap(null);
        } catch (Exception e) {
            log.error(" addKnowledgePoints:新增知识点明细失败:", e);
            if(StringUtils.isNotEmpty(e.getMessage())) {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_011);
            }else{
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
            }
        }
    }

    /**
     * 编辑知识点信息
     *
     * @param knowledgePointDetail
     * @return
     */
    @RequestMapping("/editKnowledgePointDetail")
    @ResponseBody
    public Map editKnowledgePointDetail(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, KnowledgePointDetail knowledgePointDetail) {
        try {
            //设置基础信息
            YiKeMoHelper.getInstance().updateHelper(knowledgePointDetail,userToken.getUserId(),"",
                    YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT),"");
            knowledgeService.updateKnowledgePointDetail(knowledgePointDetail);
            return YiKeMoHelper.getInstance().successJsonResultMap(null);
        } catch (Exception e) {
            log.error("editKnowledgePoints--知识点明细修改失败:", e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    @RequestMapping("/deleteKnowledgePointDetail")
    @ResponseBody
    public Map deleteKnowledgePointDetail(KnowledgePointDetail knowledgePointDetail) {
        try {
            knowledgeService.deleteKnowledgePointDetail(knowledgePointDetail);
            return YiKeMoHelper.getInstance().successJsonResultMap(null);
        } catch (Exception e) {
            log.error("editKnowledgePoints--知识点明细删除失败:", e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
}
