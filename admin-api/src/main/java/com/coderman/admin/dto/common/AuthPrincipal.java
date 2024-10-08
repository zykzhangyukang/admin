package com.coderman.admin.dto.common;

import lombok.Data;

import java.security.Principal;

/**
 * @author ：zhangyukang
 * @date ：2023/10/19 14:45
 */
@Data
public class AuthPrincipal implements Principal {

    private final Integer userId;

    public AuthPrincipal(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return String.valueOf(userId);
    }
}