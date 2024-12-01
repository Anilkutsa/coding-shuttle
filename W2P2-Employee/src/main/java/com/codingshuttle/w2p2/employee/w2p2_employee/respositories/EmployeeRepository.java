package com.codingshuttle.w2p2.employee.w2p2_employee.respositories;

import com.codingshuttle.w2p2.employee.w2p2_employee.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    // List<EmployeeEntity> findByName(String name);
}
