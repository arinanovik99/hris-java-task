package com.asti.departments.exception;

public class DepartmentNotFoundException extends RuntimeException {

  public DepartmentNotFoundException(String id) {
    super("Department with ID " + id + " not found");
  }
}
