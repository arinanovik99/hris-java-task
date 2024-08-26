package com.asti.employees.controller;

import com.asti.employees.dto.UserDto;
import com.asti.employees.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User management operations")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Retrieve all users", description = "Returns a paginated and optionally filtered list of all users.")
    @GetMapping
    public ResponseEntity<PageImpl<UserDto>> findUsers(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") final int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") final int pageSize,
            @RequestParam(name = "searchString", required = false, defaultValue = "") final String searchString) {
        return ResponseEntity.ok(userService.findAllUsers(pageNumber, pageSize, searchString));
    }

    @Operation(summary = "Get users by department ID", description = "Returns a list of users belonging to a specific department")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<UserDto>> findUsersByDepartmentId(@PathVariable final String departmentId) {
        return ResponseEntity.ok(userService.findUsersByDepartmentId(departmentId));
    }

    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @Operation(summary = "Create a new user", description = "Creates a new user. Accessible only by HR.")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid final UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @Operation(summary = "Update a user", description = "Updates an existing user by ID. Accessible only by HR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<UserDto> updateUser(@PathVariable final Long id, @RequestBody @Valid final UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by ID. Accessible only by HR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
