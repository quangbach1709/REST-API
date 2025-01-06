package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Role;
import com.quangbach1709.restapi.entity.User;

import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        dto.setDescription(role.getDescription());

        // Lấy danh sách userIds từ các User có Role này
        if (role.getUsers() != null) {
            dto.setUserIds(role.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setRole(dto.getRole());
        role.setDescription(dto.getDescription());
        return role;
    }
}
