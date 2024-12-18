package com.codingshuttle.jpa.mappings.repositories;

import com.codingshuttle.jpa.mappings.entities.DepartmentEntity;
import com.codingshuttle.jpa.mappings.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByManager(EmployeeEntity employeeEntity);
}
