package com.asti.departments.service.impl;

import com.asti.departments.client.EmployeeServiceClient;
import com.asti.departments.dto.DepartmentDto;
import com.asti.departments.entity.Department;
import com.asti.departments.repository.DepartmentRepository;
import com.asti.departments.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentRepository departmentRepository;

  private final EmployeeServiceClient employeeServiceClient;

  private final ModelMapper mapper;

  @Override
  public List<DepartmentDto> findAllDepartments() {
    return departmentRepository.findAll().stream()
        .map(department -> mapper.map(department, DepartmentDto.class))
        .toList();
  }

  @Override
  public DepartmentDto findDepartmentById(final String id) {
    final var departmentDto =
        mapper.map(departmentRepository.findByIdOrThrow(id), DepartmentDto.class);
    departmentDto.setEmployees(employeeServiceClient.getEmployeesByDepartmentId(id));
    return departmentDto;
  }

  @Override
  public DepartmentDto createDepartment(DepartmentDto departmentDto) {
    final var departmentToSave = mapper.map(departmentDto, Department.class);
    final var department = departmentRepository.save(departmentToSave);
    return mapper.map(department, DepartmentDto.class);
  }

  @Override
  public DepartmentDto updateDepartment(String id, DepartmentDto departmentDto) {
    final var department = departmentRepository.findByIdOrThrow(id);
    department.setName(departmentDto.getName());
    final var updatedDepartment = departmentRepository.save(department);
    return mapper.map(updatedDepartment, DepartmentDto.class);
  }
}
