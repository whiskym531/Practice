package com.example.carrot.service;

import com.example.carrot.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    public static final String s = "111";//interface变量自带public static final
    Boolean create(User user);

    User findById(Long id);

    Boolean delete(Long id);

    @Transactional(propagation = Propagation.MANDATORY)
    Boolean update (User user);

    List<User> paging();

    Boolean mq(Long id);
}
