package com.tblog.service;

import com.tblog.common.PageHelper;
import com.tblog.entity.Comment;
import com.tblog.entity.UserContent;

/**
 * @author tyc
 * @date 2019/5/9
 */
public interface UserContentService {

    /**
     * 查询所有Content并分页
     * @param content
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize);
}
