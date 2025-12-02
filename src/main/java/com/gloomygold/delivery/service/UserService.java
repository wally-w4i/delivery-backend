package com.gloomygold.delivery.service;

import com.gloomygold.delivery.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(User user);
}
