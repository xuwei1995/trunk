package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.TempClassify;
import com.serviceindeed.yike.yikemo.domain.TempPraxis;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.TempClassifyMapper;
import com.serviceindeed.yike.yikemo.mapper.TempPraxisMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.MyException;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class TempService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${sgfFileUploadPath}")
    private String sgfFileUploadPath;
    @Autowired
    TempClassifyMapper tempClassifyMapper;
    @Autowired
    TempPraxisMapper tempPraxisMapper;

    /**
     * 分页查询题目列表
     *
     * @param httpPages
     * @param tempPraxis
     * @return
     */
    public Page<TempPraxis> queryPagePraxis(HttpPages httpPages, TempPraxis tempPraxis) {
        return PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(
                () -> tempPraxisMapper.getAllPraxis(tempPraxis));
    }

    /**
     * 查询题目列表
     *
     * @param tempPraxis
     * @return
     */
    public List<TempPraxis> queryPraxisList(TempPraxis tempPraxis) {
        return tempPraxisMapper.getAllPraxis(tempPraxis);
    }
    /**
     * 保存分类信息
     * @param tempPraxis
     * @param userToken
     * @param headers
     * @return
     */
    @Transactional
    public void savePraxis(TempPraxis tempPraxis, User userToken, HttpHeaders headers) {
        MultipartFile sgfFile = tempPraxis.getSgfFile();
        String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
        if (tempPraxis.getPraxisId() == null) { //新增
            tempPraxis.setSgf(YiKeMoHelper.getInstance().generateFileName(sgfFile.getOriginalFilename(), "SGF"));
            tempPraxisMapper.insertSelective(tempPraxis);
            File destFile = new File(sgfFileUploadPath+"temp_praxis/"+tempPraxis.getSgf());
            // 检测是否存在目录
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                FileUtils.copyInputStreamToFile(sgfFile.getInputStream(), destFile);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
                throw    new MyException(Constant.SERVER_MSG_007);
            }

        } else {  //修改
            if( tempPraxis.getSgfFile()!=null) //修改提交了sgf文件
            {
                String sfgName= tempPraxisMapper.selectByPrimaryKey(tempPraxis.getPraxisId()).getSgf();

                tempPraxis.setSgf(sfgName);
                File destFile = new File(sgfFileUploadPath+"temp_praxis/"+tempPraxis.getSgf());
                // 检测是否存在目录
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                try {
                    FileUtils.copyInputStreamToFile(sgfFile.getInputStream(), destFile);
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                    throw    new MyException(Constant.SERVER_MSG_007);
                }
            }
            tempPraxisMapper.updateByPrimaryKeySelective(tempPraxis);
        }
    }
    /**
     * 获取sgf文件信息
     * @param sgfName
     * @return
     */
    public String getSgfFileInfo(String sgfName) {
        File file = new File(sgfFileUploadPath + sgfName);
        String sgfInfo="";
        // 检测是否存在目录
        if (file.exists()) {
            sgfInfo= YiKeMoHelper.getInstance().readFile(file);
        }
        return sgfInfo;
    }
    /**
     * 获取所有分类信息
     * @return
     */
    @Transactional
    public List<TempClassify> getTempClassify() {
        return tempClassifyMapper.getTempClassify();
    }
}
