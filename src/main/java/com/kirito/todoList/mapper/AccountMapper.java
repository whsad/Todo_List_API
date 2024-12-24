package com.kirito.todoList.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kirito.todoList.common.pojos.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<User> {

}
