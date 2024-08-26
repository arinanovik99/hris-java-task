package com.asti.departments.service;

import com.asti.departments.dto.DepartmentDto;
import java.util.List;

public interface DepartmentService {

  List<DepartmentDto> findAllDepartments();

  DepartmentDto findDepartmentById(String id);

  DepartmentDto createDepartment(DepartmentDto departmentDto);

  DepartmentDto updateDepartment(String id, DepartmentDto departmentDto);
}
