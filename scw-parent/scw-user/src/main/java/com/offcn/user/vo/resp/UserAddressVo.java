package com.offcn.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户地址封装类
 */
@Data
@ApiModel
public class UserAddressVo {
    @ApiModelProperty("地址id")
    private Integer id ;

    @ApiModelProperty("会员地址")
    private String address ;
}
