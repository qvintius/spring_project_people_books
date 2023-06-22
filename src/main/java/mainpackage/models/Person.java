package mainpackage.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 100, message = "name should be from 2 to 100 characters")
    private String fullName;

    @Setter
    @Getter
    @Min(value = 1900, message = "year should be greater than 1900")
    private int yearOfBirth;

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }
}
