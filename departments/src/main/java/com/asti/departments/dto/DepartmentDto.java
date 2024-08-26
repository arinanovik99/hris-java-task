package com.asti.departments.dto;

import java.util.List;
import lombok.Data;

@Data
public class DepartmentDto {

  private String id;
  private String name;
  private List<EmployeeDto> employees;
}
