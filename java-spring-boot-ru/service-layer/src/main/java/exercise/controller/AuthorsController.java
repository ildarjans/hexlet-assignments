package exercise.controller;

import exercise.dto.*;
import exercise.mapper.AuthorMapper;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @Autowired
    private AuthorMapper authorMapper;

    @GetMapping
    public List<AuthorDTO> getAuthors() {
        return authorService.getAll();
    }

    @GetMapping(path = "/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorCreateDTO dto) {
        return authorService.create(dto);
    }

    @PutMapping(path = "/{id}")
    public AuthorDTO update(@PathVariable Long id, @Valid @RequestBody AuthorUpdateDTO dto) {
        return authorService.update(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

    // END
}
