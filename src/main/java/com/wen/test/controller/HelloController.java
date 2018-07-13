package com.wen.test.controller;

import com.wen.test.entity.ResultBean;
import com.wen.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 随便写一个Controller,为了简单点，
 * 方法就一个，传id来判断要抛什么异常
 */
@RestController
public class HelloController {

    @Autowired
    UserService userService;

    /**
     * 传id来判断抛什么异常
     * @param id
     * @return
     */
    @RequestMapping(value="test")
    public ResultBean hello(Integer id ){

        return new ResultBean(userService.throwException(id));
    }


}
