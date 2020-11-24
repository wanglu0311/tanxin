package com.tx.test.freemarker.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @ClassName People
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 10:43
 */
@Data
@ToString
public class People {

    private String name;

    private Integer age;

    private Date birthday;

    private Float money;

    private List<People> relative;

    private People only;

}
