package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.KnowledgeSystem;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.KnowledgeSystemMapper;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
@Service
public class KnowledgeSystemService {

    @Autowired
    KnowledgeSystemMapper knowledgeSystemMapper;

    /**
     * 分页查询知识体系列表
     *
     * @param httpPages
     * @param knowledgeSystem
     * @return
     */
    public Page<KnowledgeSystem> queryPageKnowledgeSystem(HttpPages httpPages, KnowledgeSystem knowledgeSystem) {
        return PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(
                () -> knowledgeSystemMapper.queryKnowledgeSystemList(knowledgeSystem));
    }

    /**
     * 查询知识体系列表
     *
     * @param knowledgeSystem
     * @return
     */
    public List<KnowledgeSystem> queryKnowledgeSystemList(KnowledgeSystem knowledgeSystem) {
        return knowledgeSystemMapper.queryKnowledgeSystemList(knowledgeSystem);
    }

    /**
     * 根据知识体系ID查询知识体系信息
     *
     * @param knowledgeSystemId
     * @return
     */
    public KnowledgeSystem queryKnowledgeSystemById(Long knowledgeSystemId) {
        return knowledgeSystemMapper.selectByPrimaryKey(knowledgeSystemId);
    }

    /**
     * 修改知识体系信息
     *
     * @param knowledgeSystem
     */
    @Transactional
    public void updateKnowledgeSystem(KnowledgeSystem knowledgeSystem) {
        knowledgeSystemMapper.updateByPrimaryKey(knowledgeSystem);
    }

    /**
     * 保存知识体系信息
     *
     * @param knowledgeSystem
     */
    @Transactional
    public void saveKnowledgeSystem(KnowledgeSystem knowledgeSystem) {
        knowledgeSystemMapper.insert(knowledgeSystem);
    }

    /**
     * 判断是否将已存在子类的大类改为子类
     * 如果是则返回null，否则返回要更新的知识体系对象
     * @param knowledgeSystem
     * @return
     */
    public KnowledgeSystem hasChild(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, KnowledgeSystem knowledgeSystem){
        KnowledgeSystem knowledgeSystemEdit = this.queryKnowledgeSystemById(knowledgeSystem.getKnowledgeSystemId());
        //如果知识体系原是大类
        if(knowledgeSystemEdit.getSystemLevel()==1&& knowledgeSystem.getParentSystemId()!=null){
            //判断原知识体系下是否存在子类
            KnowledgeSystem knowledgeSystemQuery=new KnowledgeSystem();
            knowledgeSystemQuery.setParentSystemId(knowledgeSystem.getKnowledgeSystemId());
            List<KnowledgeSystem> knowledgeSystemList=this.queryKnowledgeSystemList(knowledgeSystemQuery);
            //如果将已存在子类的大类改为子类，则返回
            if(knowledgeSystemList!=null&&knowledgeSystemList.size()>0){
                return null;
            }
        }
        //设置基础信息
        YiKeMoHelper.getInstance().updateHelper(knowledgeSystem, userToken.getUserId(), "",
                YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT), "");
        BeanUtils.copyProperties(knowledgeSystem, knowledgeSystemEdit, new String[]{"createBy", "createDate", "createPlatform", "createVersion"});
        return knowledgeSystemEdit;
    }
}
