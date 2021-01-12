package com.offcn.webui.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.OrderServiceFeignException;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.TOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SCW-ORDER",fallback = OrderServiceFeignException.class,configuration= FeignConfig.class)
public interface OrderServiceFeign {
    /**
     * 创建订单
     * @param vo
     * @return
     */
    @PostMapping("/order/createOrder")
    AppResponse<TOrder> createOrder(@RequestBody OrderFormInfoSubmitVo vo);
}
