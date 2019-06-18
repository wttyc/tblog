package com.tblog.service;

import com.tblog.entity.User;

/**
 * @author tyc
 * @date 2019/5/9
 */
public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    int regist(User user);

    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    User login(String email,String password);

    /**
     * 按邮箱查询用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 按手机号查询用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 按ID查询用户
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 按邮箱删除用户
     * @param email
     */
    void deleteByEmail(String email);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);

}
