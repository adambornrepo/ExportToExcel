package com.abtech;

import com.abtech.entity.Gender;
import com.abtech.payload.SaveRequest;
import com.abtech.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@SpringBootApplication
public class ExportToExcelApplication {

    @Autowired
    private EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(ExportToExcelApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void export() {
        employeeService.exportAllToExcel();
    }

    @Bean
    public CommandLineRunner runner(EmployeeService employeeService) {
        return args -> {
            var john = SaveRequest.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .birthDate(LocalDate.of(2000, 1, 1))
                    .gender(Gender.MALE)
                    .build();

            var jane = SaveRequest.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .birthDate(LocalDate.of(2000, 1, 1))
                    .gender(Gender.FEMALE)
                    .build();

            employeeService.saveEmployee(john);
            employeeService.saveEmployee(jane);

        };
    }

}
