package com.home.shop.AUTH_SERVICE.dto;


import com.home.shop.AUTH_SERVICE.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    @NotNull
    private Role.RoleName role;
}
