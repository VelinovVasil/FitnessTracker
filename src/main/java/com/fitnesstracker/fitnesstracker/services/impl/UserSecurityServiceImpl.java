package com.fitnesstracker.fitnesstracker.services.impl;


import com.fitnesstracker.fitnesstracker.models.dto.UserDTO;
import com.fitnesstracker.fitnesstracker.services.UserSecurityService;
import com.fitnesstracker.fitnesstracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {


        private final UserService userService;

        public boolean isOwner(String username, Long userId) {

            UserDTO user = userService.getUserById(userId);
            return user != null && user.getUsername().equals(username);
        }


}
