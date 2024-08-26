package com.asti.employees.service;

import com.asti.employees.dto.UserDto;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface UserService {

    PageImpl<UserDto> findAllUsers(int pageNumber, int pageSize, String searchString);

    List<UserDto> findUsersByDepartmentId(String departmentId);

    UserDto findUserById(Long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
