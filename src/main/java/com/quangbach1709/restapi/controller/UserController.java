package com.quangbach1709.restapi.controller;

import com.quangbach1709.restapi.dto.UserDTO;
import com.quangbach1709.restapi.service.PersonService;
import com.quangbach1709.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

//    @PostMapping("/person/{personId}")
//    public UserDTO createUserPerson(@PathVariable Long personId, @RequestBody UserDTO userDTO) {
//        userDTO.setPerson(personService.getPersonById(personId));
//        return userService.createUser(userDTO);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUserDTO = userService.updateUser(id, userDTO);
        if (updatedUserDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{id}/person/{personId}")
//    public ResponseEntity<UserDTO> updateUserPerson(@PathVariable Long id, @PathVariable Long personId) {
//        UserDTO userDTO = userService.getUserById(id);
//        if (userDTO != null) {
//            userDTO.setPerson(personService.getPersonById(personId));
//            return ResponseEntity.ok(userService.updateUser(id, userDTO));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
