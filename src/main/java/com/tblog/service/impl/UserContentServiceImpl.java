package com.tblog.service.impl;

import com.tblog.common.PageHelper;
import com.tblog.entity.Comment;
import com.tblog.entity.UserContent;
import com.tblog.service.UserContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tyc
 * @date 2019/7/12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserContentServiceImpl implements UserContentService {
    @Override
    public PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<UserContent> findByUserId(Long uid) {
        return null;
    }

    @Override
    public List<UserContent> findAll() {
        return null;
    }
}
