package com.asti.employees.service.impl;

import com.asti.employees.dto.UserDto;
import com.asti.employees.entity.UserEntity;
import com.asti.employees.enumiration.Role;
import com.asti.employees.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindAllUsers() {
        int pageNumber = 0;
        int pageSize = 10;
        String searchString = "John";
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        UserEntity userEntity = new UserEntity();
        userEntity.setName("John Doe");
        Page<UserEntity> page = new PageImpl<>(List.of(userEntity), pageable, 1);
        UserDto userDto = new UserDto();

        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        var result = userService.findAllUsers(pageNumber, pageSize, searchString);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(modelMapper, times(1)).map(userEntity, UserDto.class);
    }

    @Test
    void findUsersByDepartmentId() {
        String departmentId = "department-1";
        UserEntity userEntity = new UserEntity();
        UserDto userDto = new UserDto();

        when(userRepository.findAllByDepartmentId(departmentId)).thenReturn(List.of(userEntity));
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        var result = userService.findUsersByDepartmentId(departmentId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAllByDepartmentId(departmentId);
        verify(modelMapper, times(1)).map(userEntity, UserDto.class);
    }

    @Test
    void findUserById() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        UserDto userDto = new UserDto();

        when(userRepository.findByIdOrThrow(id)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        var result = userService.findUserById(id);

        assertNotNull(result);
        verify(userRepository, times(1)).findByIdOrThrow(id);
        verify(modelMapper, times(1)).map(userEntity, UserDto.class);
    }

    @Test
    void createUser() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        var result = userService.createUser(userDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(userEntity);
        verify(modelMapper, times(1)).map(userDto, UserEntity.class);
        verify(modelMapper, times(1)).map(userEntity, UserDto.class);
    }

    @Test
    void updateUser() {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setDepartmentId("department-1");
        userDto.setRole(Role.HR);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setDepartmentId("department-1");
        userEntity.setRole(Role.EMPLOYEE);

        when(userRepository.findByIdOrThrow(id)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        var result = userService.updateUser(id, userDto);

        assertNotNull(result);
        assertEquals(Role.HR, result.getRole());
        verify(userRepository, times(1)).findByIdOrThrow(id);
        verify(userRepository, times(1)).save(userEntity);
        verify(modelMapper, times(1)).map(userEntity, UserDto.class);
    }

    @Test
    void deleteUser() {
        Long id = 1L;

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}