package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberAddressMapper;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.po.TMember;
import com.offcn.user.po.TMemberAddress;
import com.offcn.user.po.TMemberAddressExample;
import com.offcn.user.po.TMemberExample;
import com.offcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TMemberMapper memberMapper;

    @Autowired
    private TMemberAddressMapper memberAddressMapper;

    /**
     * 用户注册
     * @param member
     */
    @Override
    public void registerUser(TMember member) {
        //1、检查数据库中此手机是否已经被注册
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(member.getLoginacct());
        long l = memberMapper.countByExample(example);
        //如果数据库中这个手机号的数量大于0，就代表数据库中已经存在这个手机号了
        if (l > 0){
            //抛出自定义异常
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        //2、手机号未被注册，设置相关参数，保存注册信息
        //加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(member.getUserpswd());
        //设置密码
        member.setUserpswd(encode);
        member.setUsername(member.getLoginacct());
        member.setEmail(member.getEmail());
        //实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
        member.setAuthstatus("0");
        //用户类型: 0 - 个人， 1 - 企业
        member.setUsertype("0");
        //账户类型: 0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府
        member.setAccttype("2");
        System.out.println("插入数据:"+member.getLoginacct());
        memberMapper.insertSelective(member);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public TMember login(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        //根据传入的用户名 即手机号 查找用户
        criteria.andLoginacctEqualTo(username);
        List<TMember> list = memberMapper.selectByExample(example);
        if(list!=null && list.size()==1){
            TMember member = list.get(0);
            //将传入的密码与查找出来的用户的密码对比，是否相等
            boolean matches = encoder.matches(password, member.getUserpswd());
            return matches?member:null;
        }
        return null;
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public TMember findTmemberById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取地址列表
     * @param memberId
     * @return
     */
    @Override
    public List<TMemberAddress> addressList(Integer memberId) {
        TMemberAddressExample example = new TMemberAddressExample();
        example.createCriteria().andMemberidEqualTo(memberId);
        return memberAddressMapper.selectByExample(example);
    }
}
