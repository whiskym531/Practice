package com.example.carrot.service.Impl;

import com.example.carrot.Dao.UserDao;
import com.example.carrot.model.User;
import com.example.carrot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
//    @Transactional
    public Boolean delete(Long id) {
        userDao.delete(id);
//        int i = 1/0;
        return true;
    }

    @Override
    public List<User> paging() {
        return userDao.findAll();
    }

    @Override
    public Boolean mq(Long id) {
        return userDao.mq(id);
    }

    @Override
    public Boolean update(User user) {
        userDao.update(user);
//        int i = 1/0;
        return true;
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Boolean create(User user) {
        return userDao.create(user);
    }
}
