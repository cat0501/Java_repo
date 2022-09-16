package com.itheima;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/23 10:56
 */

import com.itheima.mongoRepository.User;
import com.itheima.mongoRepository.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    private UserService userService;

    @Test
    public void saveUser() {
        User user = new User();
        user.setName("李华").setAge(24).setPhone("10001");
        userService.insertUser(user);
    }

    @Test
    public void getUser() {
        List<User> list = userService.getAllUser();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId("61e3d9ac57b3da05fd0fffed");
        user.setName("云韵");
        user.setAge(29);
        user.setPhone("18525351592");
        user.setParentId("1");
        userService.updateUser(user);
    }

    @Test
    public void getUserPage() {
        Page<User> pageResponse = userService.getPageByid("1", 1, 2);
        System.out.println("----总记录数：" + pageResponse.getTotalElements());
        System.out.println("----当前页数据：" + pageResponse.getContent());

    }

    @Test
    public void deleteUser() {
        String id = "61e3daf31faa1f5b0e6849f8";
        userService.deleteUserById(id);
    }
}
