package mainpackage.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Setter
    @Getter
    private int id;

    @Setter
    @Getter
    @NotEmpty(message = "title shouldn't be empty")
    @Size(min = 2, max = 100, message = "title should be from 2 to 100 characters")
    private String title;

    @Setter
    @Getter
    @NotEmpty(message = "author shouldn't be empty")
    @Size(min = 2, max = 100, message = "author should be from 2 to 100 characters")
    private String author;

    @Setter
    @Getter
    @Min(value = 1100, message = "year should be greater than 1900")
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

}
