package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.*;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.domain.model.UserInfo;
import com.serviceindeed.yike.yikemo.mapper.*;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.MyException;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    RechargeTypeMapper rechargeTypeMapper;
    @Autowired
    CardMapper cardMapper;
    @Autowired
    RechargeMapper rechargeMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Value("${userPhotoPath}")
    private String userPhotoPath;
  
    /**
     * 分页查询学生信息
     * @param student
     * @return
     */
    public Map<String,Object> queryPageStudentList(Student student, HttpPages httpPages){
        if(YiKeMoHelper.getInstance().isPaging(httpPages)){ //分页
            Page<Student> page = PageHelper.offsetPage(httpPages.getStart(),httpPages.getLength()).doSelectPage(() -> studentMapper.queryPageStudentView(student));
            return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw()+1,page);
        }
        else
        {
            List<Student> studentList= studentMapper.queryPageStudentView(student);
            return YiKeMoHelper.getInstance().notPagingResult(studentList,studentList.size());
        }

    }
    /**
     * 先现在一条用户
     * 新增学生信息
     * @param student
     */
    @Transactional
    public Map<String,Object> addStudent(User userToken,Student student,HttpHeaders headers) {
        Integer dan=student.getDan();
        if(dan==null)
        {
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
        }
        String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
        String ifkCode= YiKeMoHelper.getInstance().GetIfkCode(studentMapper,Constant.IFK_CODE,Constant.S);//获取ifkCode

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String userType= Constant.USER_STUDENT_TYPE;
        User user=new User();
        user.setIfkCode(ifkCode);
        user.setPassword(encoder.encode(ifkCode));
        user.setUserName(student.getUserName());
        user.setMobile(student.getMobile());
        user.setGender(student.getGender());
        user.setIsEnable("1");
        user.setUserType(userType);
        user.setCreateBy(userToken.getUserId());
        user.setCreateDate(new Date());
        user.setCreatePlatform(userAgent);
        userMapper.insertSelective(user);
        //新增用户完成
        //新增学生
        student.setIfkCode(ifkCode);
        student.setUserId(user.getUserId());
        student.setStatus("UC");
        student.setCreateBy(userToken.getUserId());
        student.setCreateDate(new Date());
        student.setScore( gradeMapper.selectScoreByGradeName(dan).getStandardIntegrate().doubleValue());//通过段位查积分
        student.setCreatePlatform(userAgent);
        studentMapper.insertSelective(student);
        return    YiKeMoHelper.getInstance().successJsonResultMap(null,Constant.OPERATION_SUCCESS);

    }
    @Transactional
    public Map<String,Object> editStudent(User userToken,Student student,HttpHeaders headers) {

        if (student.getStudentId() == null&&student.getUserId()!=null) {
           return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_004);
        } else
            {
                String userAgent=  YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
                student.setUpdateBy(userToken.getUserId());
                student.setUpdateDate(new Date());
                student.setUpdatePlatform(userAgent);
                studentMapper.updateByPrimaryKeySelective(student);
                User user=new User();
                user.setUserId(student.getUserId());
                user.setUserName(student.getUserName());
                user.setEmail(student.getEmail());
                user.setIsEnable(student.getIsEnable());
                user.setUpdateDate(new Date());
                user.setGender(student.getGender());
                user.setUpdateBy(userToken.getUserId());
                user.setUpdatePlatform(userAgent);
                user.setMobile(student.getMobile());
                userMapper.updateByPrimaryKeySelective(user);
                return YiKeMoHelper.getInstance().successJsonResultMap(null, Constant.OPERATION_SUCCESS);
        }
    }
  //修改用户密码
    public Map<String,Object> editStudentPwd(User userToken, User user,HttpHeaders headers) {
        if(user.getUserId()!=null&&user.getPassword()!=null)
        {
            String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));//token加密
            user.setUpdateBy(userToken.getUserId());
            user.setUpdateDate(new Date());
            user.setUpdatePlatform(userAgent);
            userMapper.updateByPrimaryKeySelective(user);
            return YiKeMoHelper.getInstance().successJsonResultMap(null,"密码修改成");
        }else {
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);
        }

    }
    //批量充值
    @Transactional
    public synchronized Map<String,Object> batchRecharge(User userToken, Student student, HttpHeaders headers) {
      String userAgent=  YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
        if(student.getUserIds().length>0&student.getStudentIds().length>0&&student.getCurrentCardIds().length>0) {//userId  studentId cardId 为数组
            for (int i = 0; i <student.getStudentIds().length ; i++) {
                student.setUserId(student.getUserIds()[i]);
                student.setStudentId(student.getStudentIds()[i]);
                student.setCurrentCardId(student.getCurrentCardIds()[i]);
                Card tempCard= cardMapper.selectByPrimaryKey(student.getCurrentCardId());
                if(Constant.CARD_STATUS_FN.equals(tempCard.getStatus()))
                {
                    return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_012,"卡号"+tempCard.getCardNo());
                }
                Date  date=new Date();
                Student tempStudent=studentMapper.selectByPrimaryKey(student.getStudentIds()[i]);   // 首次激活日期	如果数据库中为空则更新为当前日期，如果不为空则不处理

                if(tempStudent.getFirstActivateDate()==null) {
                    student.setFirstActivateDate(date); //数据库中为空则更新为当前日期
                }else {
                    student.setCurrentActivatDate(date); //当前激活日期=当前日期
                }
                Integer monthValue= rechargeTypeMapper.queryCardTypeMonth(student.getCurrentCardId());//获得类型表的月份
                if(tempStudent.getValidDate()==null||tempStudent.getValidDate().compareTo(date)<0) {
                    student.setValidDate(YiKeMoHelper.getInstance().calculatingTime(date,monthValue));//  有效截止日期	=如果数据库中为空或小于当前日期则为当前日期+类型表中有效月份
                }else {
                    student.setValidDate(YiKeMoHelper.getInstance().calculatingTime(tempStudent.getValidDate(),monthValue));//如果数据库中的日期大于当前,数据库中日期+类型表中有效月份
                }
                student.setStatus(Constant.STUDENT_STATUS_CH); //学生状态更新为已经充值
                student.setUpdatePlatform(userAgent);
                studentMapper.updateByPrimaryKeySelective(student);
                Card card=new Card();
                card.setActivateBy(userToken.getUserId());//激活人=前台操作人
                card.setActivateDate(date);//  激活日期=当前日期
                card.setValidDate(student.getValidDate());//有效截止日期=刚更新学生表中的有效截止日期
                card.setStatus(Constant.CARD_STATUS_FN);// 状态=已使用
                card.setStudentId(student.getStudentIds()[i]);//对应学生ID=前台传入学生ID
                card.setUserId(student.getUserIds()[i]);//学生对应用户ID	= 前台传入学生用户ID
                card.setCardId(student.getCurrentCardIds()[i]);  // 充值卡ID=前台传入充值卡ID
                card.setUpdatePlatform(userAgent);
                cardMapper.updateByPrimaryKeySelective(card);
                Recharge recharge=new Recharge();
                recharge.setUserId(student.getUserIds()[i]);
                recharge.setStudentId(student.getStudentIds()[i]);
                recharge.setCardId(student.getCurrentCardIds()[i]);
                recharge.setCreateBy(userToken.getUserId());
                recharge.setCreateDate(date);
                recharge.setCreatePlatform(userAgent);
                rechargeMapper.insertSelective(recharge);
            }
            return YiKeMoHelper.getInstance().successJsonResultMap(null,"充值成功");
        }else {
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_002);
        }

    }

    //新增用户
    @Transactional
    public Map addUser(HttpHeaders headers, User userToken, User user) {
        MultipartFile userHeadImg=user.getPhotoImg();
        if(userHeadImg!=null) //头像信息
        {
            user.setPhoto(YiKeMoHelper.getInstance().generateFileName(userHeadImg.getOriginalFilename(),"img"));
        }
        Date date=new Date();
        String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
        YiKeMoHelper.getInstance().createHelper(user,userToken.getUserId(),null,userAgent,null);
        String ifkCode=YiKeMoHelper.getInstance().GetIfkCode(studentMapper, Constant.IFK_CODE,Constant.P);//获取ifkCode
        user.setIfkCode(ifkCode);
        user.setIsEnable("1");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));//token加密
        // user.setUserType("PT");
        userMapper.insertSelective(user);
        UserRole userRole=new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(user.getRoleId());
        YiKeMoHelper.getInstance().createHelper(userRole,user.getUserId(),null,userAgent,null);
        userRoleMapper.insertSelective(userRole);
        Teacher teacher=new Teacher();
        teacher.setUserId(user.getUserId());
        teacher.setSchoolId( 1L);
        teacher.setOrgId(1L);
        YiKeMoHelper.getInstance().createHelper(teacher,userToken.getUserId(),null,userAgent,null);
        teacherMapper.insertSelective(teacher);
        if(userHeadImg!=null) //头像信息
        {
            File destFile = new File(userPhotoPath+user.getPhoto());
            // 检测是否存在目录
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                FileUtils.copyInputStreamToFile(userHeadImg.getInputStream(), destFile);

            } catch (IOException e) {
                e.printStackTrace();
                throw    new MyException(Constant.SERVER_MSG_007);
            }
        }
        return YiKeMoHelper.getInstance().successJsonResultMap(null,"新增用户成功");

    }
   @Transactional
    public Map editUser(HttpHeaders headers, User userToken, User user) {

       String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
       YiKeMoHelper.getInstance().updateHelper(user,userToken.getUserId(),null,userAgent,null);
       MultipartFile imgFile=user.getPhotoImg();
       if(imgFile!=null&&user.getUserId()!=null)//用户修改了头像
       {
           String headImgName= userMapper.selectByPrimaryKey(user.getUserId()).getPhoto();

           if(StringUtils.isEmpty(headImgName))
           {
               user.setPhoto(YiKeMoHelper.getInstance().generateFileName(imgFile.getOriginalFilename(),"img"));
           }else {
               user.setPhoto(headImgName);
           }

       }
       userMapper.updateByPrimaryKeySelective(user);
       UserRole userRole=new UserRole();
       userRole.setRoleId(user.getRoleId());
       userRole.setUserId(user.getUserId());
//       YiKeMoHelper.getInstance().updateHelper(userRole,userToken.getUserId(),null,userAgent,null);
       userRoleMapper.updateByPrimaryKeySelective(userRole);
       if(imgFile!=null&&user.getUserId()!=null)
       {
           File destFile = new File(userPhotoPath+user.getPhoto());
           // 检测是否存在目录
           if (!destFile.getParentFile().exists()) {
               destFile.getParentFile().mkdirs();
           }
           try {
               FileUtils.copyInputStreamToFile(imgFile.getInputStream(), destFile);
              // throw new IOException("test"); //用于测试发生IO异常
           } catch (IOException e) {
               e.printStackTrace();
            throw     new MyException(Constant.SERVER_MSG_007);
           }
       }
       return YiKeMoHelper.getInstance().successJsonResultMap(null,"修改成功");
   }
    /**
     * 根据条件，查询单个学生
     * @param userId
     * @return
     */
    public UserInfo queryStudentById(Long userId) {
        return studentMapper.queryStudentInfo(userId);
    }
}
