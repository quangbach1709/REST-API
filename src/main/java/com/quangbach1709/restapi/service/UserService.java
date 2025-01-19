package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.PersonMapper;
import com.quangbach1709.restapi.dto.UserDTO;
import com.quangbach1709.restapi.dto.UserMapper;
import com.quangbach1709.restapi.entity.User;
import com.quangbach1709.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(userDTO.getEmail());
                    existingUser.setPassword(userDTO.getPassword());
                    existingUser.setIsActive(userDTO.getIsActive());
                    if (userDTO.getPerson() != null) {
                        existingUser.setPerson(PersonMapper.toEntity(userDTO.getPerson()));
                    }
                    User updatedUser = userRepository.save(existingUser);
                    return UserMapper.toDTO(updatedUser);
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
