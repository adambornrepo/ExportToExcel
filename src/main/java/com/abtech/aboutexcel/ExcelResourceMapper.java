package com.abtech.aboutexcel;

import com.abtech.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class ExcelResourceMapper {
    public ExportResource buildExportResource(Employee employee){
        return ExportResource.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthDate(employee.getBirthDate())
                .gender(employee.getGender())
                .isActive(!employee.isDisabled())
                .createdAt(employee.getCreatedAt())
                .build();
    }
}
