package com.offcn.order.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.service.impl.ProjectServiceFeignException;
import com.offcn.order.vo.resp.TReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
//通过feign调用SCW-PROJECT模块的服务
@FeignClient(value = "SCW-PROJECT", fallback = ProjectServiceFeignException.class)
public interface ProjectServiceFeign {

    /**
     * 获取项目回报的信息
     * @param projectId
     * @return
     */
    @GetMapping("/project/details/returns/{projectId}")
    public AppResponse<List<TReturn>> returnInfo(@PathVariable(name="projectId") Integer projectId);
}
