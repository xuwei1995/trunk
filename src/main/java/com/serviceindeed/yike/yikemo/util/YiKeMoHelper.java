package com.serviceindeed.yike.yikemo.util;

import com.github.pagehelper.Page;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.CardActiveMapper;
import com.serviceindeed.yike.yikemo.mapper.StudentMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class YiKeMoHelper {


    private static YiKeMoHelper instance ;
    public Map serverExceptionMap=errorJsonResultMap(Constant.SERVER_MSG_001);
    private  Logger log = LoggerFactory.getLogger(this.getClass());
    private YiKeMoHelper(){

    }
    public static YiKeMoHelper getInstance(){
        if (instance == null) {
            synchronized (YiKeMoHelper.class){
                if (instance == null) {
                    instance = new YiKeMoHelper() ;
                }
            }
        }
        return instance ;
    }
    /**
     * MD5加密
     *
     * @param message 要进行MD5加密的字符串
     * @return 加密结果为32位字符串
     */
    public  String getMD5(String message) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(message.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                }
                else
                {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return md5StrBuff.toString().toUpperCase();// 字母大写
    }
    /**
     *存储过程GetSequence 共通
     *@Author xw
     *@Date 2017/12/14 16:43
     *@Result IFK编号
     */
    public String GetIfkCode(StudentMapper studentMapper, String i_seqtype, String i_seqname) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("i_seqtype", i_seqtype);
        map.put("i_seqname", i_seqname);
        map.put("o_sequence","");
        studentMapper.getIfkCode(map);
        return  map.get("o_sequence").toString();
    }
    public String getCardNo(CardActiveMapper cardActiveMapper, String card_no, String c) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("i_seqtype", card_no);
        map.put("i_seqname", c);
        map.put("o_sequence","");
        cardActiveMapper.getCardNo(map);
        return  map.get("o_sequence").toString();
    }
    /**
    *errorJResult
    *@Author xw
    *@Date 2017/12/14 18:06
    */
   public Map<String,Object> errorJsonResultMap(String code,String ... errorMsg)
   {
       Map<String,Object> map=new HashMap<>();
       map.put("code",code);
       map.put("status",Constant.ERROR);
       if(errorMsg.length>0)
       {
           map.put("msg",errorMsg[0]);
       }else {
           map.put("msg","");
       }
       return  map;
   }
   /**
    *successMsg
    *@Author xw
    *@Date 2017/12/14 18:10
    */
    public Map<String,Object> successJsonResultMap(Object object,String ... successMsg)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("code","");
        map.put("status",Constant.OK);
        map.put("data",object);
        if(successMsg.length>0)
        {
            map.put("msg",successMsg[0]);
        }else {
            map.put("msg","");
        }
        return  map;
    }
    /**
     * 返回到前台分页查询成功的数据封装
     * @param draw
     * @return
     */
    public  Map<String,Object> getSuccessPageQueryJson(Integer draw,Page page){
        Map<String,Object> map = new HashMap<String, Object>();
         map.put("code","");
         map.put("status",Constant.OK);
        if(page.getResult().size()==0)
        {
            map.put("msg",Constant.QUERY_RESULT_IS_NULL);
        }else {
            map.put("msg",Constant.QUERY_SUCCESS);
        }
        //以下返回参数必须是固定的
            map.put("draw", draw);
            map.put("recordsTotal", page.getTotal());//数据总条数
            map.put("recordsFiltered", page.getTotal());//显示的条数
            map.put("aData", page.getResult());//数据集合
        return map;
    }
    /**
     * 返回到前台分页查询失敗的数据封装
     * @param draw
     * @return
     */
    public  Map<String,Object> getFailPageQueryJson(Integer draw ){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",Constant.ERROR);
        map.put("code",Constant.SERVER_MSG_001);
        map.put("msg","无数据");
        //以下返回参数必须是固定的
        map.put("draw", draw);
        map.put("recordsTotal",0);//数据总条数
        map.put("recordsFiltered",0);//显示的条数
        map.put("aData",null);//数据集合
        return map;
    }


    public Map<String,Object> notPagingResult(Object object,int size)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("status",Constant.OK);
        map.put("code","");
        map.put("msg",Constant.QUERY_SUCCESS);
        map.put("aData",object);
        map.put("total",size);
        return  map;
    }

    public boolean isPaging(HttpPages httpPages)
    {
        if(httpPages.getDraw()!=null&&httpPages.getLength()!=null&&httpPages.getStart()!=null)
        {
            return  true;
        }else {
            return  false;
        }
    }
    /**
     * 驼峰格式转换为下划线
     * @param camelCaseName
     * @return
     */
    public  String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }else {
            return  null;
        }
        return result.toString().toUpperCase();
    }
    /**
     *生成文件名
     *@Author xw
     *@Date 2017/12/26 11:58
     */
    public String generateFileName(String fileName, String index)
    {
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重命名图片
        fileName = index + System.currentTimeMillis() + "-" + CommonFunction.getRandom(4) + suffixName;
        return   fileName;
    }
    /**
     * 随机文件名称 randomGetFileName
     * @param pathFile
     * @return
     */
    public  void uploadFile(String pathFile, MultipartFile file)
    {
        File destFile = new File(pathFile);
        // 检测是否存在目录
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

        } catch (IOException e) {
            e.printStackTrace();
       throw    new RuntimeException(Constant.SERVER_MSG_007);
    }

    }
    /**
     *date n个月后的日期
     *@Author xw
     *@Date 2017/12/22 19:41
     */
    public Date calculatingTime(Date date,/* Integer year,*/ Integer Moth/*, Integer days*/){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
     //   c.add(Calendar.YEAR, null == year ? 0 : year);
        c.add(Calendar.MONTH, null == Moth ? 0 : Moth);
     //   c.add(Calendar.DATE, null == days ? 0 : days);
        Date time = c.getTime();
        return time;
    }
    /**
     *根据key拿出HttpHeaders的info
     *@Author xw
     *@Date 2017/12/22 19:36
     *@Param headers
     *@Param key[]
     */
  public String getHttpHeaderInfo(HttpHeaders headers,String ...keys){
      StringBuffer stringBuffer=new StringBuffer();
        if(keys.length>0){
            for (String key:keys)
            {   stringBuffer.append(key);
                stringBuffer.append("  ");
                stringBuffer.append(headers.get(key)==null?"":headers.get(key)+"\r\n");
            }
        }
      return  stringBuffer.toString().trim();
  }
   //读取文件
    public String readFile(File file) {
        StringBuffer stringBuffer = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                stringBuffer.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            throw  new MyException(Constant.SERVER_MSG_014);
        }
        return stringBuffer.toString();
    }
    //删除文件
    public void deleteFile(String path)
    {
        File file=new File(path);
        if(file.exists()){
            file.delete();
        }
    }
    /**
     * 创建
     * @param object
     * @param by
     * @param device
     * @param platform
     * @param version
     */
    public  void createHelper(Object object,Long by,String device,
                                    String platform,String version){
        try {
            BeanUtils.setProperty(object, "createBy", by);// 创建人
            BeanUtils.setProperty(object, "createDate", new Date());// 创建时间
            BeanUtils.setProperty(object, "createDevice", device);// 创建设备
            BeanUtils.setProperty(object, "createPlatform", platform);// 创建设备平台
            BeanUtils.setProperty(object, "createVersion", version);// 创建版本号
        } catch (Exception e) {
            log.error("error createHelper : " + e);
        }
    }

    /**
     * 修改
     * @param object
     * @param by
     * @param device
     * @param platform
     * @param version
     */
    public  void updateHelper(Object object,Long by,String device,
                                    String platform,String version){
        try {
            BeanUtils.setProperty(object, "updateBy", by);// 更新人
            BeanUtils.setProperty(object, "updateDate", new Date());// 更新时间
            BeanUtils.setProperty(object, "updateDevice", device);// 更新设备
            BeanUtils.setProperty(object, "updatePlatform", platform);// 更新设备平台
            BeanUtils.setProperty(object, "updateVersion", version);// 更新版本号
        } catch (Exception e) {
            log.error("error UpdateHelper : " + e);
        }
    }
  /**
   *token 加密密码
   *@Author xw
   *@Date 2017/12/27 14:16
   */
  public  String getTokenPassword(String passwordStr)
  {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      return   encoder.encode(passwordStr);
  }

}
