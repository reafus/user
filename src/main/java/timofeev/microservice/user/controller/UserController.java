package timofeev.microservice.user.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timofeev.microservice.user.dto.UserDTO;
import timofeev.microservice.user.exceptions.ResourceNotFoundException;
import timofeev.microservice.user.model.User;
import timofeev.microservice.user.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {

        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        UserDTO userDTO = convertToUserDTO(user);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(convertToUser(userDTO));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(value = "id") Long userId, @Valid @RequestBody UserDTO userDTO)
            throws ResourceNotFoundException {

        User userToUpdate = convertToUser(userDTO);
        User updatedUser = userService.updateUser(userId, userToUpdate);

        UserDTO updatedUserDTO = convertToUserDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        userService.deleteUser(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
