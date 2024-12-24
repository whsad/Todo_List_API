package com.kirito.todoList;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kirito.todoList.common.dtos.PageResult;
import com.kirito.todoList.common.dtos.ResponseResult;
import com.kirito.todoList.common.pojos.Todo;
import com.kirito.todoList.mapper.TodoMapper;
import com.kirito.todoList.service.TodoService;
import com.kirito.todoList.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TodoListApiApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private TodoService todoService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testJwtCreationAndParsing() {
        String subject = "test-subject";
        String jwt = JwtUtils.createJWT(subject);

        assertNotNull(jwt);

        Claims claims = JwtUtils.parseJWT(jwt);
        assertEquals(subject, claims.getSubject());
    }

    @Test
    public void testBrc(){
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        String encode3 = passwordEncoder.encode("123456");

        System.out.println(encode1);
        System.out.println(encode2);
        System.out.println(encode3);

        boolean result1 = passwordEncoder.matches("123456", "$2a$10$JlEcY6Ln9ysMtQhuykEv7OS7XrZ0Tud8Syg50BAOs9NEEoJRBhiai");
        boolean result2 = passwordEncoder.matches("123456", "$2a$10$Ni2WUCnFDELpqZiqOGdNdeaz1Mmp/BVxno/v6CmLWcjI3zYJoPG/y");
        boolean result3 = passwordEncoder.matches("123456", "$2a$10$3fOPNZcYcSCfeLOl9ihiCuRJ7hw40oQA.TUNom0V/pPigw.zVifOS");
        boolean result4 = passwordEncoder.matches("123456", "$12$lCd2bv.aohBaC6.RlyMMqeIqM7JjjfZr2MvykUWVGEqHKh.65YupS.TUNom0V/pPigw.zVifOS");

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }

    @Test
    public void testTodo(){
        List<Todo> todos = todoMapper.selectList(null);
        System.out.println(todos);
    }

    @Test
    public void testSelectPage(){
        IPage<Todo> iPage = new Page<>(1,3);
        todoService.page(iPage, null);
        System.out.println("当前页码值："+iPage.getCurrent());
        System.out.println("每页显示数："+iPage.getSize());
        System.out.println("一共多少页："+iPage.getPages());
        System.out.println("一共多少条数据："+iPage.getTotal());
        System.out.println("数据："+iPage.getRecords());

        ResponseResult responseResult = new PageResult(1, 3, (int) iPage.getTotal());
        responseResult.setData(iPage.getRecords());
        System.out.println(JSON.toJSONString(responseResult));
    }
}
