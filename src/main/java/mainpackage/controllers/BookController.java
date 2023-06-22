package mainpackage.controllers;

import jakarta.validation.Valid;
import mainpackage.dao.BookDAO;
import mainpackage.dao.PersonDAO;
import mainpackage.models.Book;
import mainpackage.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
   private final BookDAO bookDAO;
   private final PersonDAO personDAO;

   @Autowired
   public BookController(BookDAO bookDAO, PersonDAO personDAO) {
       this.bookDAO = bookDAO;
       this.personDAO = personDAO;
   }

   @GetMapping("")
   public String allBooks(Model model){
       model.addAttribute("books", bookDAO.allBooks());
       return "/books/all";
   }

   @GetMapping("/{id}")
   public String idBook(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model){
       model.addAttribute("book", bookDAO.idBook(id));
       Optional<Person> bookOwner = bookDAO.getBookOwner(id);
       if (bookOwner.isPresent())
           model.addAttribute("owner", bookOwner.get());
       else
           model.addAttribute("people", personDAO.allPeople());
       return "/books/idBook";
   }

   @GetMapping("/new")
   public String newBook(@ModelAttribute("book") Book book){
       return "/books/new";
   }

   @PostMapping("")
   public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
       if (bindingResult.hasErrors())
           return "/books/new";
       bookDAO.save(book);
       return "redirect:/books";
   }

   @GetMapping("/{id}/edit")
   public String editBook(@PathVariable("id") int id, Model model){
       model.addAttribute("book", bookDAO.idBook(id));
       return "/books/edit";
   }

   @PatchMapping("/{id}")
   public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
       if (bindingResult.hasErrors())
           return "/books/edit";
       bookDAO.update(id, book);
       return "redirect:/books";
   }

   @DeleteMapping("/{id}")
   public String deleteBook(@PathVariable("id") int id){
       bookDAO.delete(id);
       return "redirect:/books";
   }

   @PatchMapping("/{id}/release")
   public String releaseBook(@PathVariable("id") int id){
       bookDAO.release(id);
       return "redirect:/books/" + id;
   }

   @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
       bookDAO.assign(id, selectedPerson);
       return "redirect:/books/" + id;
   }

}