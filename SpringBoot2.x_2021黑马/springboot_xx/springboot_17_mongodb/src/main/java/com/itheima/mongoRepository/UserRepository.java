package com.itheima.mongoRepository;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/23 10:55
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

//Repository： 仅仅是一个标识，表明任何继承它的均为仓库接口类
//CrudRepository： 继承 Repository，实现了一组 CRUD 相关的方法
//PagingAndSortingRepository： 继承 CrudRepository，实现了一组分页排序相关的方法
//MongoRepository： 继承 PagingAndSortingRepository，实现一组 mongodb规范相关的方法

//自定义的 XxxxRepository 需要继承 MongoRepository，这样的 XxxxRepository 接口就具备了通用的数据访问控制层的能力(CURD的操作功能)。
public interface UserRepository extends MongoRepository<User,String> {
    Page<User> getPageById(String parentId, Pageable pageable);
}
