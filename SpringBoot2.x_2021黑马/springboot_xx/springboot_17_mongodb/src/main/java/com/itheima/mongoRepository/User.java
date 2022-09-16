package com.itheima.mongoRepository;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/8/23 10:55
 */

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    private String id;//主键
    //该属性对应mongodb的字段的名字，如果一致，则无需该注解
    private String name;
    private Integer age;
    private String phone;
    private String parentId;//上级ID


}
