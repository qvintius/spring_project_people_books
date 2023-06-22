package mainpackage.controllers;

import jakarta.validation.Valid;
import mainpackage.PersonValidator;
import mainpackage.dao.PersonDAO;
import mainpackage.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping("")
    public String allPeople(Model model){
        model.addAttribute("people", personDAO.allPeople());
        List<Person> people = personDAO.allPeople();
        return "/people/all";
    }

    @GetMapping("/{id}")
    public String idPerson(@PathVariable ("id") int id, Model model){
        model.addAttribute("person", personDAO.idPerson(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "/people/idPerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.idPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/people/edit";
        personDAO.update(id, person);
        return "redirect:/people";

    }


    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";

    }

}
