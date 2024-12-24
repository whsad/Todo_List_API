package com.kirito.todoList.common.pojos;

import com.baomidou.mybatisplus.annotation.TableId;
import com.kirito.todoList.common.dtos.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    @TableId
    private String id;
    private String name;
    private String email;
    private String password;

    public User(RegisterDto dto){
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }


}
