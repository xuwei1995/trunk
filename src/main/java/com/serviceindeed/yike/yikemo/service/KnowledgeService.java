package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.KnowledgePoint;
import com.serviceindeed.yike.yikemo.domain.KnowledgePointDetail;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.KnowledgePointDetailMapper;
import com.serviceindeed.yike.yikemo.mapper.KnowledgePointMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class KnowledgeService {
    @Autowired
    KnowledgePointMapper knowledgePointMapper;
    @Autowired
    KnowledgePointDetailMapper knowledgePointDetailMapper;
    /**
     * 获取sgf上传路径
     */
    @Value("${sgfFileUploadPath}")
    private String sgfFileUploadPath;
    /**
     * 分页查询知识点列表
     *
     * @param httpPages
     * @param knowledgePoint
     * @return
     */
    public Page<KnowledgePoint> queryPageKnowledgePoint(HttpPages httpPages, KnowledgePoint knowledgePoint) {
        return PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(
                () -> knowledgePointMapper.getAllKnowledgePoints(knowledgePoint));
    }

    /**
     * 查询知识点列表
     *
     * @param knowledgePoint
     * @return
     */
    public List<KnowledgePoint> queryKnowledgePointList(KnowledgePoint knowledgePoint) {
        return knowledgePointMapper.getAllKnowledgePoints(knowledgePoint);
    }

    /**
     * 根据知识体系ID查询知识体系信息
     * @param knowledgePointId
     * @return
     */
    public KnowledgePoint queryKnowledgePointById(Long knowledgePointId){
        return knowledgePointMapper.selectByPrimaryKey(knowledgePointId);
    }
    /**
     * 保存知识点信息
     * @param knowledgePoint
     */
    @Transactional
    public void saveKnowledgePoint(KnowledgePoint knowledgePoint) {
        knowledgePointMapper.insert(knowledgePoint);
    }
    /**
     *修改知识点信息
     * @param knowledgePoint
     */
    @Transactional
    public void updateKnowledgePoint(KnowledgePoint knowledgePoint){
        KnowledgePoint knowledgePointEdit= this.queryKnowledgePointById(knowledgePoint.getKnowledgePointId());
        BeanUtils.copyProperties(knowledgePoint,knowledgePointEdit,new String[]{"createBy","createDate","createPlatform","createVersion"});
        knowledgePointMapper.updateByPrimaryKey(knowledgePointEdit);
    }
    /**
     * 分页查询知识点明细列表
     *
     * @param httpPages
     * @param knowledgePointDetail
     * @return
     */
    public Page<KnowledgePointDetail> queryPageKnowledgePointDetail(HttpPages httpPages, KnowledgePointDetail knowledgePointDetail) {
        return PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(
                () -> knowledgePointDetailMapper.queryKnowledgePointDetailList(knowledgePointDetail));
    }

    /**
     * 查询知识点明细列表
     *
     * @param knowledgePointDetail
     * @return
     */
    public List<KnowledgePointDetail> queryKnowledgePointDetailList(KnowledgePointDetail knowledgePointDetail) {
        return knowledgePointDetailMapper.queryKnowledgePointDetailList(knowledgePointDetail);
    }

    /**
     * 根据知识体系详情ID查询知识体系信息
     * @param knowledgePointDetailId
     * @return
     */
    public KnowledgePointDetail queryKnowledgePointDetailById(Long knowledgePointDetailId){
        return knowledgePointDetailMapper.selectByPrimaryKey(knowledgePointDetailId);
    }
    /**
     * 保存知识点明细信息
     * @param knowledgePointDetail
     */
    @Transactional
    public void saveKnowledgePointDetail(KnowledgePointDetail knowledgePointDetail) {
       //获取上传的sgf文件
        MultipartFile sgfFile = knowledgePointDetail.getSgfFile();
            if (sgfFile == null) {
                throw new RuntimeException(Constant.SERVER_MSG_011);
            }
            knowledgePointDetail.setSgfPath(YiKeMoHelper.getInstance().generateFileName(sgfFile.getOriginalFilename(), "SGF"));
            knowledgePointDetailMapper.insert(knowledgePointDetail);
            YiKeMoHelper.getInstance().uploadFile(sgfFileUploadPath+"knowledge_point_detail/"+knowledgePointDetail.getSgfPath(),sgfFile);
    }
    /**
     *修改知识点明细信息
     * @param knowledgePointDetail
     */
    @Transactional
    public void updateKnowledgePointDetail(KnowledgePointDetail knowledgePointDetail){
        //获取上传的sgf文件
        MultipartFile sgfFile = knowledgePointDetail.getSgfFile();
        if( knowledgePointDetail.getSgfPath()!=null)
        {
            String sfgName= knowledgePointDetailMapper.selectByPrimaryKey(knowledgePointDetail.getKnowledgePointDetailId()).getSgfPath();
            File file=new File(sgfFileUploadPath+"knowledge_point_detail/"+sfgName);
            if(file.exists())
            {
                file.delete();
            }
            knowledgePointDetail.setSgfPath(YiKeMoHelper.getInstance().generateFileName(sgfFile.getOriginalFilename(), "SGF"));
        }
        knowledgePointDetailMapper.updateByPrimaryKeySelective(knowledgePointDetail);
    }
    /**
     *删除知识点明细信息
     * @param knowledgePointDetail
     */
    @Transactional
    public void deleteKnowledgePointDetail(KnowledgePointDetail knowledgePointDetail){
        knowledgePointDetailMapper.deleteByPrimaryKey(knowledgePointDetail.getKnowledgePointDetailId());
    }
}
