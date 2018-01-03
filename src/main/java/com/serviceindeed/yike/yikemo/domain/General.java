package com.serviceindeed.yike.yikemo.domain;

import com.serviceindeed.yike.yikemo.domain.helper.Columns;

import java.io.Serializable;

public class General extends GeneralKey  implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_general.KEY_VALUE
     *
     * @mbg.generated
     */
    private String keyValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_general.SORT_INDEX
     *
     * @mbg.generated
     */
    private Integer sortIndex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_general.IS_ENABLE
     *
     * @mbg.generated
     */
    private String isEnable;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_general.REMARK
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_general.KEY_VALUE
     *
     * @return the value of t_general.KEY_VALUE
     *
     * @mbg.generated
     */
    public String getKeyValue() {
        return keyValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_general.KEY_VALUE
     *
     * @param keyValue the value for t_general.KEY_VALUE
     *
     * @mbg.generated
     */
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_general.SORT_INDEX
     *
     * @return the value of t_general.SORT_INDEX
     *
     * @mbg.generated
     */
    public Integer getSortIndex() {
        return sortIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_general.SORT_INDEX
     *
     * @param sortIndex the value for t_general.SORT_INDEX
     *
     * @mbg.generated
     */
    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_general.IS_ENABLE
     *
     * @return the value of t_general.IS_ENABLE
     *
     * @mbg.generated
     */
    public String getIsEnable() {
        return isEnable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_general.IS_ENABLE
     *
     * @param isEnable the value for t_general.IS_ENABLE
     *
     * @mbg.generated
     */
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_general.REMARK
     *
     * @return the value of t_general.REMARK
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_general.REMARK
     *
     * @param remark the value for t_general.REMARK
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}