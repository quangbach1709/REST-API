package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.PersonMapper;
import com.quangbach1709.restapi.dto.UserDTO;
import com.quangbach1709.restapi.dto.UserMapper;
import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.entity.Role;
import com.quangbach1709.restapi.entity.User;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.RoleRepository;
import com.quangbach1709.restapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    // Fetch roles and set them in the user entity
                    Set<Role> roles = roleRepository.findAllById(
                            user.getRoles().stream().map(Role::getId).collect(Collectors.toSet())
                    ).stream().collect(Collectors.toSet());
                    user.setRoles(roles);
                    return UserMapper.toDTO(user);
                })
                .orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        // Link Person if available
        if (userDTO.getPersonId() != null) {
            Person person = personRepository.findById(userDTO.getPersonId())
                    .orElseThrow(() -> new RuntimeException("Person not found"));
            user.setPerson(person);
        }

        // Link Roles if available
        if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findAllById(userDTO.getRoleIds()).stream()
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        user = userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(userDTO.getEmail());
                    existingUser.setPassword(userDTO.getPassword());
                    existingUser.setIsActive(userDTO.getIsActive());

                    // Tạo một bản sao của roles hiện tại
                    Set<Role> currentRoles = new HashSet<>(existingUser.getRoles());

                    // Xóa tất cả roles hiện tại
                    existingUser.getRoles().clear();

                    // Thêm roles mới
                    if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
                        Set<Role> newRoles = roleRepository.findAllById(userDTO.getRoleIds()).stream()
                                .collect(Collectors.toSet());
                        existingUser.getRoles().addAll(newRoles);
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
