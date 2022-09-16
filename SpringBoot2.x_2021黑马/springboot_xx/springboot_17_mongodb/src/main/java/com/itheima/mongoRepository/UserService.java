package com.itheima.mongoRepository;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/23 10:56
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public void insertUser(User user) {
        userRepository.save(user);
        //userRepository.
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * 根据id查询评论
     */
    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    /**
     * 分页
     */
    public Page<User> getPageByid(String parentId, int page, int size) {
        return userRepository.getPageById(parentId, PageRequest.of(page - 1, size));
    }
}