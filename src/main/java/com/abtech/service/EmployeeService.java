package com.abtech.service;

import com.abtech.aboutexcel.ExcelResourceMapper;
import com.abtech.aboutexcel.ExcelWriteService;
import com.abtech.aboutexcel.ExportResource;
import com.abtech.entity.Employee;
import com.abtech.payload.SaveRequest;
import com.abtech.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ExcelResourceMapper mapper;
    private final ExcelWriteService excelWriteService;

    public ResponseEntity<?> saveEmployee(SaveRequest request) {
        Employee employee = request.get();
        Employee saved = employeeRepository.save(employee);
        System.out.println("saved = " + saved);
        return ResponseEntity.ok(saved);
    }

    public List<Employee> exportAllToExcel() {
        List<Employee> all = employeeRepository.findAll();
        List<ExportResource> exportData = all
                .stream()
                .map(mapper::buildExportResource)
                .toList();
        excelWriteService.writeToExcel(exportData,"file_name", "sheet_name");

        return all;
    }
}
