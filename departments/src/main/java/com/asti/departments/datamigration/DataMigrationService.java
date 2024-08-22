package com.asti.departments.datamigration;

import com.asti.departments.entity.Department;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataMigrationService {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void migrateData() {
        final var department1 = new Department("HR");
        final var department2 = new Department("Marketing");
        final var department3 = new Department("IT");
        final var department4 = new Department("Security");

        mongoTemplate.dropCollection("departments");
        mongoTemplate.insertAll(List.of(department1, department2, department3, department4));
    }
}
