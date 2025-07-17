package com.example.userservice.controller;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user and integrate with Talon.One")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or Talon.One error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO responseDTO = userService.getUserById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO responseDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(responseDTO);
    }
}
