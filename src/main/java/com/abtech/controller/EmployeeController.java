package com.abtech.controller;

import com.abtech.entity.Employee;
import com.abtech.payload.SaveRequest;
import com.abtech.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/emp")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody SaveRequest request){
        return employeeService.saveEmployee(request);
    }

    @PostMapping("/export")
    public List<Employee> exportToExcel(){
        return employeeService.exportAllToExcel();
    }

}
