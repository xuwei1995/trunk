package com.serviceindeed.yike.yikemo.domain.model;

public class UserInfo {
    private Long userId;
    private String ifkCode;
    private String userName;
    private String gender;
    private String userType;
    private Long orgId;
    private String orgAbbrname;
    private String schoolName;
    private Long schoolId;
    private Double score;
    private Integer dan;
    private String gradeName;
    private String photo;
    private String signature;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIfkCode() {
        return ifkCode;
    }

    public void setIfkCode(String ifkCode) {
        this.ifkCode = ifkCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgAbbrname() {
        return orgAbbrname;
    }

    public void setOrgAbbrname(String orgAbbrname) {
        this.orgAbbrname = orgAbbrname;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getDan() {
        return dan;
    }

    public void setDan(Integer dan) {
        this.dan = dan;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}