package com.abtech.payload;

import com.abtech.entity.Employee;
import com.abtech.entity.Gender;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveRequest implements Serializable , Supplier<Employee> {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    @Override
    public Employee get() {
        return Employee.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .birthDate(getBirthDate())
                .gender(getGender())
                .isDisabled(false)
                .build();
    }
}
