package com.asti.employees.dto;

import com.asti.employees.enumiration.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String departmentId;

    @NotNull
    private Role role;
}
