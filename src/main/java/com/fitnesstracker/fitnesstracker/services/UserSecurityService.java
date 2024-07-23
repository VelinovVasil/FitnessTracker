package com.fitnesstracker.fitnesstracker.services;

public interface UserSecurityService {

    boolean isOwner(String username, Long userId);
}
