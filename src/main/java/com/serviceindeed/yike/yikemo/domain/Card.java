package com.serviceindeed.yike.yikemo.domain;

import com.serviceindeed.yike.yikemo.domain.helper.Columns;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Card  extends Columns implements Serializable{
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CARD_ID
     *
     * @mbg.generated
     */
    private Integer[] cardIds;

    public Integer[] getCardIds() {
        return cardIds;
    }

    public void setCardIds(Integer[] cardIds) {
        this.cardIds = cardIds;
    }

    private Long cardId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CARD_NO
     *
     * @mbg.generated
     */
    private String cardNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CARD_PASSWORD
     *
     * @mbg.generated
     */
    private String cardPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CARD_ACTIVATE_ID
     *
     * @mbg.generated
     */
    private Long cardActivateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.RECHARGE_TYPE
     *
     * @mbg.generated
     */
    private Long rechargeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.ACTIVATE_BY
     *
     * @mbg.generated
     */
    private Long activateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.ACTIVATE_DATE
     *
     * @mbg.generated
     */
    private Date activateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.VALID_DATE
     *
     * @mbg.generated
     */
    private Date validDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.ORG_ID
     *
     * @mbg.generated
     */
    private Long orgId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.SCHOOL_ID
     *
     * @mbg.generated
     */
    private Long schoolId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.STUDENT_ID
     *
     * @mbg.generated
     */
    private Long studentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.USER_ID
     *
     * @mbg.generated
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.STATUS
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CREATE_BY
     *
     * @mbg.generated
     */
    private Long createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CREATE_DATE
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CREATE_PLATFORM
     *
     * @mbg.generated
     */
    private String createPlatform;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.CREATE_VERSION
     *
     * @mbg.generated
     */
    private String createVersion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.UPDATE_BY
     *
     * @mbg.generated
     */
    private Long updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.UPDATE_DATE
     *
     * @mbg.generated
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.UPDATE_PLATFORM
     *
     * @mbg.generated
     */
    private String updatePlatform;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_card.UPDATE_VERSION
     *
     * @mbg.generated
     */
    private String updateVersion;

    private String rechargeTypeName;
    private String statusValue;
    private String schoolName;
    private String orgAbbrname;
    private String orgName;
    private Integer validMonth;

    public Integer getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(Integer validMonth) {
        this.validMonth = validMonth;
    }

    public String getRechargeTypeName() {
        return rechargeTypeName;
    }

    public void setRechargeTypeName(String rechargeTypeName) {
        this.rechargeTypeName = rechargeTypeName;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getOrgAbbrname() {
        return orgAbbrname;
    }

    public void setOrgAbbrname(String orgAbbrname) {
        this.orgAbbrname = orgAbbrname;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CARD_ID
     *
     * @return the value of t_card.CARD_ID
     *
     * @mbg.generated
     */
    public Long getCardId() {
        return cardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CARD_ID
     *
     * @param cardId the value for t_card.CARD_ID
     *
     * @mbg.generated
     */
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CARD_NO
     *
     * @return the value of t_card.CARD_NO
     *
     * @mbg.generated
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CARD_NO
     *
     * @param cardNo the value for t_card.CARD_NO
     *
     * @mbg.generated
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CARD_PASSWORD
     *
     * @return the value of t_card.CARD_PASSWORD
     *
     * @mbg.generated
     */
    public String getCardPassword() {
        return cardPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CARD_PASSWORD
     *
     * @param cardPassword the value for t_card.CARD_PASSWORD
     *
     * @mbg.generated
     */
    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword == null ? null : cardPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CARD_ACTIVATE_ID
     *
     * @return the value of t_card.CARD_ACTIVATE_ID
     *
     * @mbg.generated
     */
    public Long getCardActivateId() {
        return cardActivateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CARD_ACTIVATE_ID
     *
     * @param cardActivateId the value for t_card.CARD_ACTIVATE_ID
     *
     * @mbg.generated
     */
    public void setCardActivateId(Long cardActivateId) {
        this.cardActivateId = cardActivateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.RECHARGE_TYPE
     *
     * @return the value of t_card.RECHARGE_TYPE
     *
     * @mbg.generated
     */
    public Long getRechargeType() {
        return rechargeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.RECHARGE_TYPE
     *
     * @param rechargeType the value for t_card.RECHARGE_TYPE
     *
     * @mbg.generated
     */
    public void setRechargeType(Long rechargeType) {
        this.rechargeType = rechargeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.ACTIVATE_BY
     *
     * @return the value of t_card.ACTIVATE_BY
     *
     * @mbg.generated
     */
    public Long getActivateBy() {
        return activateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.ACTIVATE_BY
     *
     * @param activateBy the value for t_card.ACTIVATE_BY
     *
     * @mbg.generated
     */
    public void setActivateBy(Long activateBy) {
        this.activateBy = activateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.ACTIVATE_DATE
     *
     * @return the value of t_card.ACTIVATE_DATE
     *
     * @mbg.generated
     */
    public Date getActivateDate() {
        return activateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.ACTIVATE_DATE
     *
     * @param activateDate the value for t_card.ACTIVATE_DATE
     *
     * @mbg.generated
     */
    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.VALID_DATE
     *
     * @return the value of t_card.VALID_DATE
     *
     * @mbg.generated
     */
    public Date getValidDate() {
        return validDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.VALID_DATE
     *
     * @param validDate the value for t_card.VALID_DATE
     *
     * @mbg.generated
     */
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.ORG_ID
     *
     * @return the value of t_card.ORG_ID
     *
     * @mbg.generated
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.ORG_ID
     *
     * @param orgId the value for t_card.ORG_ID
     *
     * @mbg.generated
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.SCHOOL_ID
     *
     * @return the value of t_card.SCHOOL_ID
     *
     * @mbg.generated
     */
    public Long getSchoolId() {
        return schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.SCHOOL_ID
     *
     * @param schoolId the value for t_card.SCHOOL_ID
     *
     * @mbg.generated
     */
    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.STUDENT_ID
     *
     * @return the value of t_card.STUDENT_ID
     *
     * @mbg.generated
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.STUDENT_ID
     *
     * @param studentId the value for t_card.STUDENT_ID
     *
     * @mbg.generated
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.USER_ID
     *
     * @return the value of t_card.USER_ID
     *
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.USER_ID
     *
     * @param userId the value for t_card.USER_ID
     *
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.STATUS
     *
     * @return the value of t_card.STATUS
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.STATUS
     *
     * @param status the value for t_card.STATUS
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CREATE_BY
     *
     * @return the value of t_card.CREATE_BY
     *
     * @mbg.generated
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CREATE_BY
     *
     * @param createBy the value for t_card.CREATE_BY
     *
     * @mbg.generated
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CREATE_DATE
     *
     * @return the value of t_card.CREATE_DATE
     *
     * @mbg.generated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CREATE_DATE
     *
     * @param createDate the value for t_card.CREATE_DATE
     *
     * @mbg.generated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CREATE_PLATFORM
     *
     * @return the value of t_card.CREATE_PLATFORM
     *
     * @mbg.generated
     */
    public String getCreatePlatform() {
        return createPlatform;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CREATE_PLATFORM
     *
     * @param createPlatform the value for t_card.CREATE_PLATFORM
     *
     * @mbg.generated
     */
    public void setCreatePlatform(String createPlatform) {
        this.createPlatform = createPlatform == null ? null : createPlatform.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.CREATE_VERSION
     *
     * @return the value of t_card.CREATE_VERSION
     *
     * @mbg.generated
     */
    public String getCreateVersion() {
        return createVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.CREATE_VERSION
     *
     * @param createVersion the value for t_card.CREATE_VERSION
     *
     * @mbg.generated
     */
    public void setCreateVersion(String createVersion) {
        this.createVersion = createVersion == null ? null : createVersion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.UPDATE_BY
     *
     * @return the value of t_card.UPDATE_BY
     *
     * @mbg.generated
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.UPDATE_BY
     *
     * @param updateBy the value for t_card.UPDATE_BY
     *
     * @mbg.generated
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.UPDATE_DATE
     *
     * @return the value of t_card.UPDATE_DATE
     *
     * @mbg.generated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.UPDATE_DATE
     *
     * @param updateDate the value for t_card.UPDATE_DATE
     *
     * @mbg.generated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.UPDATE_PLATFORM
     *
     * @return the value of t_card.UPDATE_PLATFORM
     *
     * @mbg.generated
     */
    public String getUpdatePlatform() {
        return updatePlatform;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.UPDATE_PLATFORM
     *
     * @param updatePlatform the value for t_card.UPDATE_PLATFORM
     *
     * @mbg.generated
     */
    public void setUpdatePlatform(String updatePlatform) {
        this.updatePlatform = updatePlatform == null ? null : updatePlatform.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_card.UPDATE_VERSION
     *
     * @return the value of t_card.UPDATE_VERSION
     *
     * @mbg.generated
     */
    public String getUpdateVersion() {
        return updateVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_card.UPDATE_VERSION
     *
     * @param updateVersion the value for t_card.UPDATE_VERSION
     *
     * @mbg.generated
     */
    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion == null ? null : updateVersion.trim();
    }
}