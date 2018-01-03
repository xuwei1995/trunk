package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.General;
import com.serviceindeed.yike.yikemo.domain.GeneralKey;
import com.serviceindeed.yike.yikemo.domain.General;
import com.serviceindeed.yike.yikemo.mapper.GeneralMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeneralService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GeneralMapper generalMapper;
    /**
     * 根据KeyType查询基础信息列表
     * @param keyType
     * @return
     */
    public List<General> queryGeneralByKeyType(String keyType) {
        return generalMapper.selectByKeyType(keyType);
    }
    /**
     * 根据主键查询基础信息
     * @param generalKey
     * @return
     */
    public General queryGeneralByKey(GeneralKey generalKey) {
        return generalMapper.selectByPrimaryKey(generalKey);
    }
    /**
     * 修改基础信息
     * @param general
     */
    @Transactional
    public void updateGeneral(General general){
        generalMapper.updateByPrimaryKeySelective(general);
    }

    /**
     * 保存基础信息
     * @param general
     */
    @Transactional
    public void saveGeneral(General general){
        generalMapper.insertSelective(general);
    }
}
