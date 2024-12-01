package com.codingshuttle.w2p2.department.w2p2_department.respositories;

import com.codingshuttle.w2p2.department.w2p2_department.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    // List<EmployeeEntity> findByName(String name);
}
