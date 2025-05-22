package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream().map(bookMapper::map).toList();
    }

    public BookDTO getById(Long id) {
        var book = bookRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

        return bookMapper.map(book);
    }

    public BookDTO create(BookCreateDTO dto) {
        authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new ConstraintViolationException("author with id " + dto.getAuthorId() + " not found", null));

        var model = bookRepository.save(bookMapper.map(dto));

        return bookMapper.map(model);
    }

    public BookDTO update(Long id, BookUpdateDTO dto) {
        var model = bookRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

        var isNewAuthor = dto.getAuthorId().get() != null && !dto.getAuthorId().get().equals(model.getAuthor().getId()) ;

        if (isNewAuthor) {
            var nextAuthor = authorRepository
                    .findById(dto.getAuthorId().get())
                    .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

            model.getAuthor().getBooks().remove(model);

            nextAuthor.getBooks().add(model);

            authorRepository.save(nextAuthor);
        }

        bookMapper.update(dto, model);

        var updatedModel = bookRepository.save(model);

        return bookMapper.map(updatedModel);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    // END
}