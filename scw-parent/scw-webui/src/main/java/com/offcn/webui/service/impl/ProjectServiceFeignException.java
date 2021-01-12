package com.offcn.webui.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目异常处理类
 */
@Component
public class ProjectServiceFeignException implements ProjectServiceFeign {
    @Override
    public AppResponse<List<ProjectVo>> all() {
        AppResponse<List<ProjectVo>> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【查询首页热点项目】");
        return response;
    }

    @Override
    public AppResponse<ProjectDetailVo> detailsInfo(Integer projectId) {
        AppResponse<ProjectDetailVo> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【查询项目详情】");
        return response;
    }

    @Override
    public AppResponse<ReturnPayConfirmVo> returnInfo( Integer returnId) {
        AppResponse<ReturnPayConfirmVo> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【项目回报】");
        return response;
    }
}
