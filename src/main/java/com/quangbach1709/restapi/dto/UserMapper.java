package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setIsActive(user.getIsActive());
        if (user.getPerson() != null) {
            dto.setPerson(PersonMapper.toDTO(user.getPerson()));
        }
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(RoleMapper::toDTO)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setIsActive(dto.getIsActive());
        if (dto.getPerson() != null) {
            user.setPerson(PersonMapper.toEntity(dto.getPerson()));
        }
        if (dto.getRoles() != null) {
            user.setRoles(dto.getRoles().stream()
                    .map(RoleMapper::toEntity)
                    .collect(Collectors.toSet()));
        }
        return user;
    }
}
