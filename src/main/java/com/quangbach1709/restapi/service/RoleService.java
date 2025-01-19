package com.quangbach1709.restapi.service;

import com.quangbach1709.restapi.dto.RoleDTO;
import com.quangbach1709.restapi.dto.RoleMapper;
import com.quangbach1709.restapi.entity.Role;
import com.quangbach1709.restapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(RoleMapper::toDTO)
                .orElse(null);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        return RoleMapper.toDTO(role);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setRole(roleDTO.getRole());
                    existingRole.setDescription(roleDTO.getDescription());
                    Role updatedRole = roleRepository.save(existingRole);
                    return RoleMapper.toDTO(updatedRole);
                })
                .orElse(null);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}