package com.offcn.webui.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.ProjectServiceFeignException;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 项目接口
 */
@FeignClient(value = "SCW-PROJECT", configuration = FeignConfig.class, fallback = ProjectServiceFeignException.class)
public interface ProjectServiceFeign {
    /**
     * 查询所有项目
     * @return
     */
    @GetMapping("/project/all")
    public AppResponse<List<ProjectVo>> all();

    /**
     * 根据项目id查询项目详细信息
     * @param projectId
     * @return
     */
    @GetMapping("/project/details/info/{projectId}")
    public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId);

    /**
     * 根据回报id查询回报的详情
     * @param returnId
     * @return
     */
    @GetMapping("/project/returns/info/{returnId}")
    public AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("returnId") Integer returnId);
}
