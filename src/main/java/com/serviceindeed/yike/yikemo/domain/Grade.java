package com.serviceindeed.yike.yikemo.domain;

import java.math.BigDecimal;

public class Grade {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.GRADE_ID
     *
     * @mbg.generated
     */
    private Integer gradeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.GRADE_NAME
     *
     * @mbg.generated
     */
    private String gradeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.GRADE_DESC
     *
     * @mbg.generated
     */
    private String gradeDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.INTEGRATE_FROM
     *
     * @mbg.generated
     */
    private BigDecimal integrateFrom;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.INTEGRATE_TO
     *
     * @mbg.generated
     */
    private BigDecimal integrateTo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.STANDARD_INTEGRATE
     *
     * @mbg.generated
     */
    private BigDecimal standardIntegrate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.MCMAHON_SCORE
     *
     * @mbg.generated
     */
    private Integer mcmahonScore;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.GRADE_INDEX
     *
     * @mbg.generated
     */
    private Integer gradeIndex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_grade.IS_ENABLE
     *
     * @mbg.generated
     */
    private String isEnable;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.GRADE_ID
     *
     * @return the value of t_grade.GRADE_ID
     *
     * @mbg.generated
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.GRADE_ID
     *
     * @param gradeId the value for t_grade.GRADE_ID
     *
     * @mbg.generated
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.GRADE_NAME
     *
     * @return the value of t_grade.GRADE_NAME
     *
     * @mbg.generated
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.GRADE_NAME
     *
     * @param gradeName the value for t_grade.GRADE_NAME
     *
     * @mbg.generated
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.GRADE_DESC
     *
     * @return the value of t_grade.GRADE_DESC
     *
     * @mbg.generated
     */
    public String getGradeDesc() {
        return gradeDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.GRADE_DESC
     *
     * @param gradeDesc the value for t_grade.GRADE_DESC
     *
     * @mbg.generated
     */
    public void setGradeDesc(String gradeDesc) {
        this.gradeDesc = gradeDesc == null ? null : gradeDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.INTEGRATE_FROM
     *
     * @return the value of t_grade.INTEGRATE_FROM
     *
     * @mbg.generated
     */
    public BigDecimal getIntegrateFrom() {
        return integrateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.INTEGRATE_FROM
     *
     * @param integrateFrom the value for t_grade.INTEGRATE_FROM
     *
     * @mbg.generated
     */
    public void setIntegrateFrom(BigDecimal integrateFrom) {
        this.integrateFrom = integrateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.INTEGRATE_TO
     *
     * @return the value of t_grade.INTEGRATE_TO
     *
     * @mbg.generated
     */
    public BigDecimal getIntegrateTo() {
        return integrateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.INTEGRATE_TO
     *
     * @param integrateTo the value for t_grade.INTEGRATE_TO
     *
     * @mbg.generated
     */
    public void setIntegrateTo(BigDecimal integrateTo) {
        this.integrateTo = integrateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.STANDARD_INTEGRATE
     *
     * @return the value of t_grade.STANDARD_INTEGRATE
     *
     * @mbg.generated
     */
    public BigDecimal getStandardIntegrate() {
        return standardIntegrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.STANDARD_INTEGRATE
     *
     * @param standardIntegrate the value for t_grade.STANDARD_INTEGRATE
     *
     * @mbg.generated
     */
    public void setStandardIntegrate(BigDecimal standardIntegrate) {
        this.standardIntegrate = standardIntegrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.MCMAHON_SCORE
     *
     * @return the value of t_grade.MCMAHON_SCORE
     *
     * @mbg.generated
     */
    public Integer getMcmahonScore() {
        return mcmahonScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.MCMAHON_SCORE
     *
     * @param mcmahonScore the value for t_grade.MCMAHON_SCORE
     *
     * @mbg.generated
     */
    public void setMcmahonScore(Integer mcmahonScore) {
        this.mcmahonScore = mcmahonScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.GRADE_INDEX
     *
     * @return the value of t_grade.GRADE_INDEX
     *
     * @mbg.generated
     */
    public Integer getGradeIndex() {
        return gradeIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.GRADE_INDEX
     *
     * @param gradeIndex the value for t_grade.GRADE_INDEX
     *
     * @mbg.generated
     */
    public void setGradeIndex(Integer gradeIndex) {
        this.gradeIndex = gradeIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_grade.IS_ENABLE
     *
     * @return the value of t_grade.IS_ENABLE
     *
     * @mbg.generated
     */
    public String getIsEnable() {
        return isEnable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_grade.IS_ENABLE
     *
     * @param isEnable the value for t_grade.IS_ENABLE
     *
     * @mbg.generated
     */
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }
}