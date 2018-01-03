package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.Organization;
import com.serviceindeed.yike.yikemo.domain.School;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.OrganizationMapper;
import com.serviceindeed.yike.yikemo.mapper.SchoolMapper;
import com.serviceindeed.yike.yikemo.util.CommonFunction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class OrganizationService {
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    SchoolMapper schoolMapper;
    @Value("${orgLogoUploadPath}")
    private String orgLogoUploadPath;
    /**
     * 分页查询机构列表
     *
     * @param httpPages
     * @param organization
     * @return
     */
    public Page<Organization> queryPageOrganization(HttpPages httpPages, Organization organization) {
        return PageHelper.offsetPage(httpPages.getStart(), httpPages.getLength()).doSelectPage(
                () -> organizationMapper.queryOrganizationList(organization));
    }

    /**
     * 查询机构列表
     *
     * @param organization
     * @return
     */
    public List<Organization> queryOrganizationList(Organization organization) {
        return organizationMapper.queryOrganizationList(organization);
    }
    /**
     * 根据orgId查询代理的信息
     *
     * @param orgId
     * @return
     */
    public Organization queryOrganizationById(Long orgId) {
        return organizationMapper.selectByPrimaryKey(orgId);
    }

    /**
     * @param organization
     */
    @Transactional
    public void updateOrganization(Organization organization) {
        Organization organizationEdit = this.queryOrganizationById(organization.getOrgId());
        BeanUtils.copyProperties(organization, organizationEdit, new String[]{"orgLogo", "auditBy", "auditDate", "createBy", "createDate", "createPlatform", "createVersion"});
        organizationMapper.updateByPrimaryKey(organizationEdit);
        //上传图片
        uploadFile(organizationEdit);
        //更新图片路径
        organizationMapper.updateByPrimaryKey(organizationEdit);
    }

    /**
     * 保存机构信息
     *
     * @param organization
     */
    @Transactional
    public void saveOrganization(Organization organization) {
        organizationMapper.insertSelective(organization);
        //是否创建同名学校
        if (organization.getIsCreateSchool().equals("1")) {
            School school = new School();
            BeanUtils.copyProperties(organization, school);
            school.setSchoolName(organization.getOrgName());
            school.setSchoolAddress(organization.getOrgAddress());
            school.setSchoolContact(organization.getOrgContact());
            school.setSchoolTel(organization.getOrgTel());
            school.setSchoolWebsite(organization.getOrgWebsite());
            schoolMapper.insertSelective(school);
        }
        //上传图片
        uploadFile(organization);
        //更新图片路径
        organizationMapper.updateByPrimaryKey(organization);
    }

    /**
     * 上传图片
     *
     * @param organization
     */
    private void uploadFile(Organization organization) {
        try {
            MultipartFile logoFile = organization.getFileOrgLogo();
            String logoStr = organization.getOrgLogo();
            if (logoFile != null) {
                // 判断该机构图片是否有图片，若有则删除
                if (StringUtils.isNotEmpty(logoStr)) {
                    // 判断文件是否存在
                    File file = new File(orgLogoUploadPath + logoStr);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                // 获取文件名
                String fileName = logoFile.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重命名图片
                fileName = "LOGO" + System.currentTimeMillis() + "-" + CommonFunction.getRandom(4) + suffixName;
                // 文件上传后的路径
                String filePath = orgLogoUploadPath;
                File destFile = new File(filePath + fileName);
                // 检测是否存在目录
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                FileUtils.copyInputStreamToFile(logoFile.getInputStream(), destFile);
                organization.setOrgLogo(fileName);
            } else {
                //是否删除图片
                if (organization.getDelImage()) {
                    // 判断该机构图片是否有图片，若有则删除
                    if (StringUtils.isNotEmpty(logoStr)) {
                        // 判断文件是否存在
                        File file = new File(orgLogoUploadPath + logoStr);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    organization.setOrgLogo(null);
                }
            }
        } catch (IOException e) {
            //抛出异常
            throw new RuntimeException("图片上传失败");
        }
    }

    /**
     * 校验机构名称是否存在
     *
     * @param organization
     * @return
     */
    public Integer checkOrganizationIsExist(Organization organization) {

        return organizationMapper.checkOrganizationIsExist(organization);
    }

    /**
     * 校验机构简称是否存在
     *
     * @param organization
     * @return
     */
    public Integer checkOrgAbbrnameIsExist(Organization organization) {

        return organizationMapper.checkOrgAbbrnameIsExist(organization);
    }
}
