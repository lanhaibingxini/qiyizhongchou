package com.offcn.project.vo.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 项目封装类的响应对象
 */
@Data
public class ProjectVo implements Serializable {
    // 会员id
    private Integer memberid;
    //项目id
    private Integer id;
    // 项目名称
    private String name;
    // 项目简介
    private String remark;
    // 项目头部图片
    private String headerImage;
}
