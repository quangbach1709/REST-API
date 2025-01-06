package com.quangbach1709.restapi.dto;

import com.quangbach1709.restapi.entity.Person;
import com.quangbach1709.restapi.entity.Role;
import com.quangbach1709.restapi.entity.User;
import com.quangbach1709.restapi.repository.PersonRepository;
import com.quangbach1709.restapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {


    private static PersonRepository personService;


    private static RoleRepository roleRepository;

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setIsActive(user.getIsActive());
        if (user.getPerson() != null) {
            dto.setPersonId(user.getPerson().getId());
        }
        if (user.getRoles() != null) {
            Set<Long> roleIds = user.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());
            dto.setRoleIds(roleIds);
        }
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setIsActive(dto.getIsActive());
        if (dto.getPersonId() != null) {
            Person person = personService.findById(dto.getPersonId())
                    .orElseThrow(() -> new RuntimeException("Person not found"));
            user.setPerson(person);
        }
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findAllById(dto.getRoleIds()).stream()
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        return user;
    }
}
