package com.offcn.webui.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.UserRespVo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * web的核心处理器
 */
@Controller
@Slf4j
public class DispathcherController {
    @Autowired
    private MemberServiceFeign memberServiceFeign;
    @Autowired
    private ProjectServiceFeign projectServiceFeign;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 跳转到index.html
     * @return
     */
    @RequestMapping("/")
    public String toIndex(Model model){
        //从Redis中读取项目集合
        List<ProjectVo> projectVoList = (List<ProjectVo>) redisTemplate.opsForValue().get("projectStr");
        //redis项目集合为空，就调用项目服务，读取全部项目
        if (projectVoList == null){
            AppResponse<List<ProjectVo>> appResponse = projectServiceFeign.all();
            List<ProjectVo> projectVoList1 = appResponse.getData();
            //将获取到的全部项目数据存入Redis
            redisTemplate.opsForValue().set("projectStr", projectVoList1, 1, TimeUnit.HOURS);
        }
        //如果Redis当中有项目数据，就将读取到的项目数据传到前端渲染
        model.addAttribute("projectList", projectVoList);
        return "index";
    }

    /**
     * 处理登录请求方法
     * @param loginacct 用户名称（必须和前端name属性的值一致）
     * @param password 密码
     * @param session 存储登录用户信息
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String password, HttpSession session){
        //执行登录方法
        AppResponse<UserRespVo> userRespVoAppResponse = memberServiceFeign.login(loginacct, password);
        //获取登录用户的数据
        UserRespVo userRespVo  = userRespVoAppResponse.getData();
        //如果用户数据不存在，就表明登陆失败
        if (userRespVo == null){
            //就重新跳转到login.html
            return "redirect:/login.html";
        }
        //如果用户数据存在，就将用户数据存储在session中
        session.setAttribute("sessionMember", userRespVo);
        log.info("登录账号:{},密码:{}",loginacct,password);
        log.info("登录用户数据:{}",userRespVo);
        //从session中获取前缀
        String preUrl = (String) session.getAttribute("preUrl");
        if (StringUtils.isEmpty(preUrl)){
            return "redirect:/";
        }
        //如果存在就跳转到前缀的地址
        return "redirect:/" + preUrl;
    }
}
