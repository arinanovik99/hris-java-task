package com.asti.departments.repository;

import com.asti.departments.entity.Department;
import com.asti.departments.exception.DepartmentNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {

  default Department findByIdOrThrow(final String id) {
    return findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
  }
}
