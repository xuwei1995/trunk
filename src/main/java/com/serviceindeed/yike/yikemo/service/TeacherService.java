package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.Teacher;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.model.UserInfo;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.GradeMapper;
import com.serviceindeed.yike.yikemo.mapper.StudentMapper;
import com.serviceindeed.yike.yikemo.mapper.TeacherMapper;
import com.serviceindeed.yike.yikemo.mapper.UserMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TeacherService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    GradeMapper gradeMapper;
    private static final String TAG = "TeacherService";

    public Map<String, Object> queryPageTeacherList(Teacher teacher, HttpPages httpPages) {
        if(YiKeMoHelper.getInstance().isPaging(httpPages)){ //分页

            Page<Teacher> page =PageHelper.offsetPage(httpPages.getStart(),httpPages.getLength()).doSelectPage(()  -> teacherMapper.queryPageTeacherList(teacher));
            return YiKeMoHelper.getInstance().getSuccessPageQueryJson( httpPages.getDraw()+1,page);
        }
        else {
            List<Teacher> teacherList=teacherMapper.queryPageTeacherList(teacher);
            return  YiKeMoHelper.getInstance().notPagingResult(teacherList,teacherList.size());
        }

    }
    /**
     * 新增教师
     *@Author xw
     *@Date 2017/12/14 15:42
     *@result jsonMap
     */
    @Transactional
    public Map<String,Object> addTeacher(User userToken, Teacher teacher, HttpHeaders headers) {
        String ifkCode= YiKeMoHelper.getInstance().GetIfkCode(studentMapper,Constant.IFK_CODE,Constant.T);//获取ifkCode
        String userPass=YiKeMoHelper.getInstance().getTokenPassword(ifkCode);//ifkCode md5加密
        String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
        User user=new User();
        user.setIfkCode(ifkCode);
        user.setPassword(userPass);
        user.setUserName(teacher.getUserName());
        user.setMobile(teacher.getMobile());
        user.setGender(teacher.getGender());
        user.setIsEnable("1");
        YiKeMoHelper.getInstance().createHelper(user,userToken.getUserId(),null,userAgent,null);
        user.setUserType(teacher.getUserType());

        userMapper.insertSelective(user);
        //新增用户完成
        //新增教师
        teacher.setUserId(user.getUserId());
        Integer dan=teacher.getDan();
        teacher.setCreateBy(userToken.getUserId());
        teacher.setCreateDate(new Date());
        teacher.setScore( gradeMapper.selectScoreByGradeName(dan).getStandardIntegrate().doubleValue());//通过段位查积分
        teacher.setCreatePlatform(userAgent);
        teacherMapper.insertSelective(teacher);

        return    YiKeMoHelper.getInstance().successJsonResultMap(null,Constant.OPERATION_SUCCESS);

    }
    //编辑教师
    @Transactional
    public Map<String,Object> editTeacher(Teacher teacher,HttpHeaders headers) {
        if(teacher.getTeacherId()==null||teacher.getUserId()==null) {//没有传入teacher id
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_004);
        }
        else {
            String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
            teacher.setUpdatePlatform(userAgent);
            teacherMapper.updateByPrimaryKeySelective(teacher);
            User user=new User();
            user.setIsEnable(teacher.getIsEnable());
            user.setUserName(teacher.getUserName());
            user.setMobile(teacher.getMobile());
            user.setEmail(teacher.getEmall());
            user.setUserId(teacher.getUserId());
            user.setUpdatePlatform(userAgent);
            userMapper.updateByPrimaryKeySelective(user);
            return YiKeMoHelper.getInstance().successJsonResultMap(null,Constant.OPERATION_SUCCESS);

        }
    }

    /**
     * 根据条件，查询单个教师
     * @param userId
     * @return
     */
    public UserInfo queryTeacherById(Long userId) {
           return teacherMapper.queryTeacherInfo(userId);
        }
}
