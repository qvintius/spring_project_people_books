package mainpackage.dao;

import mainpackage.models.Book;
import mainpackage.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> allBooks(){
        return jdbcTemplate.query("SELECT * FROM Book ORDER BY id", new BeanPropertyRowMapper<>(Book.class));
    }
    public Book idBook(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES(?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }
    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?", updatedBook.getTitle(),
                updatedBook.getAuthor(), updatedBook.getYear(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }



    public void assign(int id, Person selectedPerson){//человек взял книгу
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }

    public void release(int id){//человек вернул книгу
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
    }


    public Optional<Person> getBookOwner(int id){//получить человека, которому принадлежит книга
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Person.id=Book.person_id WHERE Book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
