package com.asti.departments.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.asti.departments.client.EmployeeServiceClient;
import com.asti.departments.dto.DepartmentDto;
import com.asti.departments.entity.Department;
import com.asti.departments.repository.DepartmentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTests {

  @Mock private DepartmentRepository departmentRepository;

  @Mock private EmployeeServiceClient employeeServiceClient;

  @Mock private ModelMapper modelMapper;

  @InjectMocks private DepartmentServiceImpl departmentService;

  @Test
  void findAllDepartments() {

    Department department = new Department();
    DepartmentDto departmentDto = new DepartmentDto();

    when(departmentRepository.findAll()).thenReturn(List.of(department));
    when(modelMapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);

    var result = departmentService.findAllDepartments();

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(departmentRepository, times(1)).findAll();
    verify(modelMapper, times(1)).map(department, DepartmentDto.class);
  }

  @Test
  void findDepartmentById() {
    String id = "department-1";
    Department department = new Department();
    DepartmentDto departmentDto = new DepartmentDto();

    when(departmentRepository.findByIdOrThrow(id)).thenReturn(department);
    when(modelMapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);
    when(employeeServiceClient.getEmployeesByDepartmentId(id)).thenReturn(List.of());

    var result = departmentService.findDepartmentById(id);

    assertNotNull(result);
    verify(departmentRepository, times(1)).findByIdOrThrow(id);
    verify(modelMapper, times(1)).map(department, DepartmentDto.class);
    verify(employeeServiceClient, times(1)).getEmployeesByDepartmentId(id);
  }

  @Test
  void createDepartment() {
    DepartmentDto departmentDto = new DepartmentDto();
    Department department = new Department();

    when(modelMapper.map(departmentDto, Department.class)).thenReturn(department);
    when(departmentRepository.save(department)).thenReturn(department);
    when(modelMapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);

    var result = departmentService.createDepartment(departmentDto);

    assertNotNull(result);
    verify(departmentRepository, times(1)).save(department);
    verify(modelMapper, times(1)).map(departmentDto, Department.class);
    verify(modelMapper, times(1)).map(department, DepartmentDto.class);
  }

  @Test
  void updateDepartment() {
    String id = "department-1";
    DepartmentDto departmentDto = new DepartmentDto();
    departmentDto.setName("Updated Department");

    Department department = new Department();
    department.setName("Old Department");

    when(departmentRepository.findByIdOrThrow(id)).thenReturn(department);
    when(departmentRepository.save(department)).thenReturn(department);
    when(modelMapper.map(department, DepartmentDto.class)).thenReturn(departmentDto);

    var result = departmentService.updateDepartment(id, departmentDto);

    assertNotNull(result);
    assertEquals("Updated Department", result.getName());
    verify(departmentRepository, times(1)).findByIdOrThrow(id);
    verify(departmentRepository, times(1)).save(department);
    verify(modelMapper, times(1)).map(department, DepartmentDto.class);
  }
}
