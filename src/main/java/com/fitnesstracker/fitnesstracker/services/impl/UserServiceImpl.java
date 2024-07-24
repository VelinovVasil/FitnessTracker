package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.UserDTO;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<UserDTO> getAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map((element) -> {
                    UserDTO dto = modelMapper.map(element, UserDTO.class);
                    dto.setRole(element.getRole().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return modelMapper.map(this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with such id not found")), UserDTO.class);
    }
}
