package com.serviceindeed.yike.yikemo.mapper;

import com.serviceindeed.yike.yikemo.domain.KnowledgePoint;
import com.serviceindeed.yike.yikemo.domain.KnowledgePointDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgePointDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_knowledge_point_detail
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long knowledgePointDetailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_knowledge_point_detail
     *
     * @mbg.generated
     */
    int insert(KnowledgePointDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_knowledge_point_detail
     *
     * @mbg.generated
     */
    int insertSelective(KnowledgePointDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_knowledge_point_detail
     *
     * @mbg.generated
     */
    KnowledgePointDetail selectByPrimaryKey(Long knowledgePointDetailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_knowledge_point_detail
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(KnowledgePointDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_knowledge_point_detail
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(KnowledgePointDetail record);

    /**
     * 根据条件获取指定知识点详情
     * @param knowledgePointDetail
     * @return
     */
    List<KnowledgePointDetail> queryKnowledgePointDetailList(KnowledgePointDetail knowledgePointDetail);
}