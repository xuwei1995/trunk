package com.serviceindeed.yike.yikemo.util;

public class Constant {
   /**数据操作成功状态*/
   public static final String OK = "success";
   /*数据操作失败状态*/
   public static final String ERROR = "fail";
   /**General表中的KEY_TYPE--机构主表状态**/
   public static final String GENERAL_ORGANIZATION_STATUS="ORG_STATUS";
   /**General表中的KEY_TYPE--学生主表状态**/
   public static final String GENERAL_STUDENT_STATUS="STUDENT_STATUS";
   //用户类型常量
   public static final String USER_STUDENT_TYPE="ST";
   //存储过程常量
   public static final String IFK_CODE="IFK_CODE";
   public static final String S="S";
   public static final String T="T";
   public static final String P="P";
   //开卡渠道类型常量
   public static final String CARDACTIVE_CHANNEL_BK="BK";//平台开卡
   public static final String CARDACTIVE_CHANNEL_FK="FR";//网页充值
   public static final String CARDACTIVATE_STATUS_CR="CR";//平台开卡
   public static final String CARDACTIVATE_STATUS_CL="CL";//网页充值
   //卡状态
   public static final String CARD_STATUS_CR="CR";//未使用
   public static final String CARD_STATUS_FN="FN";//已使用
   public static final String CARD_STATUS_CL="CL";//作废

   public static final String QUERY_RESULT_IS_NULL="查询结果为空";
   public static final String REQUEST_ID_IS_NULL="请求Id为空";
   public static final String OPERATION_SUCCESS="操作成功";
   public static final String QUERY_SUCCESS="查询成功";
   public static final String REQUEST_PARAM_IS_NULL="请求参数为空";
   public static final String REQUEST_PARAM_IS_ERROR="请求参数错误";
   public static final String SERVER_EXCEPTION="服务器异常";

   //学生状态
   public static final String STUDENT_STATUS_UC="UC";//未充值
   public static final String STUDENT_STATUS_CH="CH";//已充值
   public static final String STUDENT_STATUS_EP="EP";//已过期

   public static final String SERVER_MSG_001="SERVER_MSG_001";//服务器异常
   public static final String SERVER_MSG_002="SERVER_MSG_002";//请求参数为空
   public static final String SERVER_MSG_003="SERVER_MSG_003";//请求参数错误
   public static final String SERVER_MSG_004="SERVER_MSG_004";//请求Id为空
   public static final String SERVER_MSG_005="SERVER_MSG_005";//有会员卡已经使用,不能批量作废
   public static final String SERVER_MSG_006="SERVER_MSG_006";//排序列名不存在
   public static final String SERVER_MSG_007="SERVER_MSG_007";//文件上传失败
   public static final String SERVER_MSG_008="SERVER_MSG_008";//此会员卡已使用不能作废
   public static final String SERVER_MSG_009="SERVER_MSG_009";
   public static final String SERVER_MSG_010="SERVER_MSG_010";//{0}已经存在
   public static final String SERVER_MSG_011="SERVER_MSG_011";//请上传sgf文件
   public static final String SERVER_MSG_012="SERVER_MSG_012";//{0}已经充值
   public static final String SERVER_MSG_013="SERVER_MSG_013";//{0}不存在
   public static final String SERVER_MSG_014="SERVER_MSG_014";//读取{0}文件失败
   //用户
   public static final String USER_MSG_001="USER_MSG_001";//用户不存在
   public static final String USER_MSG_002="USER_MSG_002";//用户Id无效
   //知识体系
   public static final String SYSTEM_MSG_002="SYSTEM_MSG_002";//该知识体系已存在子体系，不能更改为子体系


   //角色状态常量
   public static final String ROLE_TYPE_PT="PT";

}
