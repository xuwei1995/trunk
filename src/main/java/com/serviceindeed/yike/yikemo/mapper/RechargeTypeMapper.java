package com.serviceindeed.yike.yikemo.mapper;

import com.serviceindeed.yike.yikemo.domain.RechargeType;
import com.serviceindeed.yike.yikemo.domain.helper.CardType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RechargeTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge_type
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long rechargeTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge_type
     *
     * @mbg.generated
     */
    int insert(RechargeType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge_type
     *
     * @mbg.generated
     */
    int insertSelective(RechargeType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge_type
     *
     * @mbg.generated
     */
    RechargeType selectByPrimaryKey(Long rechargeTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RechargeType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recharge_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RechargeType record);

    List<RechargeType> getCardType();

    Integer queryCardTypeMonth(@Param("cardId") Long currentCardId);
}