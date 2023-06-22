package mainpackage.dao;

import mainpackage.controllers.PersonController;
import mainpackage.models.Book;
import mainpackage.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> allPeople(){
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY id", new BeanPropertyRowMapper<>(Person.class));
    }
    public Person idPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void save(Person person){
        jdbcTemplate.update("INSERT INTO PERSON (full_name, year_of_birth) VALUES(?,?)", person.getFullName(), person.getYearOfBirth());
    }
    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?", updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }



    public List<Book> getBooksByPersonId(int id){//получить все книги человека
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    //валидация уникальности ФИО
     public Optional<Person> getPersonByFullName(String fullName){
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
     }
}
