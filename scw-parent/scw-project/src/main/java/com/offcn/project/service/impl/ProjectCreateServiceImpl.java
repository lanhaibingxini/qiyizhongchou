package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.contants.ProjectConstant;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TProjectMapper projectMapper;

    @Autowired
    private TProjectImagesMapper projectImagesMapper;

    @Autowired
    private TProjectTagMapper projectTagMapper;

    @Autowired
    private TProjectTypeMapper projectTypeMapper;

    @Autowired
    private TReturnMapper tReturnMapper;

    /**
     * 初始化项目
     *
     * @param memberId
     * @return
     */
    @Override
    public String initCreateProject(Integer memberId) {
        //初始化一个令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        //项目的临时对象
        ProjectRedisStorageVo initVo = new ProjectRedisStorageVo();
        initVo.setMemberid(memberId);
        //initVo转为json字符串
        String jsonString = JSON.toJSONString(initVo);
        //TEMP_PROJECT_PREFIX = "project:create:temp:";
        stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + token, jsonString);

        return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo projectvo) {
        //1)、保存项目的基本信息，获取到数据库的id
        //页面考过来的：name,remark,money,day,memberid
        //我们自己后来操作：status,deploydate,createdate
        //自动化修改：supportmoney（已支持的金额），supporter（支持人数） ,completion（完成度）,follower（关注者人数）
        TProject projectBase = new TProject();
        BeanUtils.copyProperties(projectvo, projectBase);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        projectBase.setCreatedate(simpleDateFormat.format(new Date()));
        //1.1)、基本的信息插入，获取到刚才保存好的项目的自增id
        projectMapper.insertSelective(projectBase);
        //1.2)、获取到刚才的id
        Integer projectId  = projectBase.getId();

        String headerImage = projectvo.getHeaderImage();
        TProjectImages images = new TProjectImages(null, projectId, headerImage, ProjectImageTypeEnume.HEADER.getCode());
        //2、将项目上传的图片保存起来
        //2.1）、保存头图
        projectImagesMapper.insertSelective(images);

        List<String> detailsImage = projectvo.getDetailsImage();
        //2.2)、保存详情图
        for (String oneImage : detailsImage) {
            TProjectImages img = new TProjectImages(null, projectId, oneImage, ProjectImageTypeEnume.DETAILS.getCode());
            projectImagesMapper.insertSelective(img);
        }
        //3、保存项目的标签信息
        List<Integer> tagids = projectvo.getTagids();
        for (Integer tagid : tagids) {
            TProjectTag tProjectTag = new TProjectTag(null,projectId,tagid);
            projectTagMapper.insertSelective(tProjectTag);
        }
        //4、保存项目的分类信息
        List<Integer> typeids = projectvo.getTypeids();
        for (Integer typeid : typeids) {
            TProjectType tProjectType = new TProjectType(null,projectId,typeid);
            projectTypeMapper.insertSelective(tProjectType);
        }
        //5、保存回报信息
        List<TReturn> treturns = projectvo.getProjectReturns();
        //设置项目的id
        for(TReturn tReturn : treturns){
            tReturn.setProjectid(projectId);
            tReturnMapper.insertSelective(tReturn);
        }
        //6、删除临时数据
        stringRedisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX+projectvo.getProjectToken());
    }
}
