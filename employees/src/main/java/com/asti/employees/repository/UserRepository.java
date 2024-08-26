package com.asti.employees.repository;

import com.asti.employees.entity.UserEntity;
import com.asti.employees.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    default UserEntity findByIdOrThrow(final Long id) {
        return findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    default UserEntity findByEmailOrThrow(final String username) {
        return findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    Optional<UserEntity> findByEmail(final String username);

    List<UserEntity> findAllByDepartmentId(final String departmentId);
}
