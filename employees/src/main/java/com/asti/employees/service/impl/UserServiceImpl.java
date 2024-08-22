package com.asti.employees.service.impl;

import com.asti.employees.dto.UserDto;
import com.asti.employees.entity.UserEntity;
import com.asti.employees.repository.UserRepository;
import com.asti.employees.service.UserService;
import com.asti.employees.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    public PageImpl<UserDto> findAllUsers(final int pageNumber, final int pageSize, final String searchString) {
        log.info(
                "Fetching all employees with search parameters: [pageNumber = {}, pageSize = {}, searchString = {}]",
                pageNumber,
                pageSize,
                searchString);
        final var pageable = PageRequest.of(pageNumber, pageSize);

        final var spec = Specification.where(UserSpecification.hasName(searchString));

        final var page = userRepository.findAll(spec, pageable);
        final var content = page.getContent().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .toList();
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public List<UserDto> findUsersByDepartmentId(String departmentId) {
        log.info("Fetching employees for department id: {}", departmentId);
        return userRepository.findAllByDepartmentId(departmentId).stream()
                .map(user -> mapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto findUserById(Long id) {
        return mapper.map(userRepository.findByIdOrThrow(id), UserDto.class);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        final var user = userRepository.save(mapper.map(userDto, UserEntity.class));
        return mapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        final var user = userRepository.findByIdOrThrow(id);

        user.setName(user.getName());
        user.setDepartmentId(userDto.getDepartmentId());
        user.setRole(userDto.getRole());

        final var updatedUser = userRepository.save(user);

        return mapper.map(updatedUser, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
