package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.School;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.SchoolMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    @Autowired
    SchoolMapper schoolMapper;
    /**
     * 分页查询学校列表
     *
     * @param httpPages
     * @param school
     * @return
     */
    public Page<School> queryPageSchool(HttpPages httpPages, School school) {
        return PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(
                () -> schoolMapper.querySchoolList(school));
    }

    /**
     * 查询学校列表
     *
     * @param school
     * @return
     */
    public List<School> querySchoolList(School school) {
        return schoolMapper.querySchoolList(school);
    }
    /**
     * 根据学校ID查询学校信息
     *
     * @param schoolId
     * @return
     */
    public School querySchoolById(Long schoolId) {
        return schoolMapper.selectByPrimaryKey(schoolId);
    }

    /**
     * 新增学校信息
     *
     * @param school
     */
    public void saveSchool(School school) {
        schoolMapper.insert(school);
    }

    /**
     * 更新学校信息
     *
     * @param school
     */
    public void updateSchool(School school) {
        School schoolEdit = this.querySchoolById(school.getSchoolId());
        BeanUtils.copyProperties(school, schoolEdit);
        schoolMapper.updateByPrimaryKeySelective(school);
    }

    /**
     * 校验学校名称是否存在
     *
     * @param school
     * @return
     */
    public Integer checkSchoolIsExist(School school) {
        return schoolMapper.checkSchoolIsExist(school);
    }
}
