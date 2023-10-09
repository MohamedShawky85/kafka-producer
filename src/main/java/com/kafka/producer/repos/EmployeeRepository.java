package com.kafka.producer.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kafka.producer.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Page<Employee> findAll(Pageable pageable);
}
