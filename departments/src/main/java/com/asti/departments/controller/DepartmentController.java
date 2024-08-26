package com.asti.departments.controller;

import com.asti.departments.dto.DepartmentDto;
import com.asti.departments.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "Department Controller", description = "Handles department-related operations")
public class DepartmentController {

  private final DepartmentService departmentService;

  @Operation(summary = "Get all departments", description = "Returns a list of all departments.")
  @GetMapping
  public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
    return ResponseEntity.ok(departmentService.findAllDepartments());
  }

  @Operation(summary = "Get department by ID", description = "Returns a department by its ID.")
  @GetMapping("/{id}")
  public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable String id) {
    return ResponseEntity.ok(departmentService.findDepartmentById(id));
  }

  @Operation(summary = "Create a new department", description = "Creates a new department.")
  @PostMapping
  public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
    return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
  }

  @Operation(
      summary = "Update department",
      description = "Updates an existing department by its ID.")
  @PutMapping("/{id}")
  public ResponseEntity<DepartmentDto> updateDepartment(
      @PathVariable String id, @RequestBody DepartmentDto departmentDto) {
    return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDto));
  }
}
