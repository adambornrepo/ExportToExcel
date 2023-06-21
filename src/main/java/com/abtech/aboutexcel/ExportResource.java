package com.abtech.aboutexcel;

import com.abtech.entity.Gender;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportResource implements Serializable {

    @ExportToExcel(index = 0,headerText = "EMPLOYEE ID", width = 5000)
    private Long id;

    @ExportToExcel(index = 1,headerText = "FIRSTNAME", width = 5000)
    private String firstName;

    @ExportToExcel(index = 2,headerText = "LASTNAME", width = 5000)
    private String lastName;

    @ExportToExcel(index = 3,headerText = "BIRTHDATE", width = 5000)
    private LocalDate birthDate;

    @ExportToExcel(index = 4,headerText = "GENDER", width = 5000)
    private Gender gender;

    @ExportToExcel(index = 5,headerText = "WORKING", width = 5000)
    private boolean isActive;

    @ExportToExcel(index = 6,headerText = "EMPLOYEE ID", width = 10000)
    private LocalDateTime createdAt;

}

