package com.codingshuttle.w2p2.department.w2p2_department.controllers;

import com.codingshuttle.w2p2.department.w2p2_department.dto.EmployeeDTO;
import com.codingshuttle.w2p2.department.w2p2_department.entities.EmployeeEntity;
import com.codingshuttle.w2p2.department.w2p2_department.respositories.EmployeeRepository;
import com.codingshuttle.w2p2.department.w2p2_department.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);
    }

}
