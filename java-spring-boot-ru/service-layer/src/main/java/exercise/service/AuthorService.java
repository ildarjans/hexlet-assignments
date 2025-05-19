package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAll() {
        return authorRepository
            .findAll()
            .stream()
            .map(authorMapper::map)
            .toList();
    }

    public AuthorDTO findById(Long id) {
        var author = authorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

        return authorMapper.map(author);
    }

    public AuthorDTO create(AuthorCreateDTO dto) {
        var author = authorMapper.map(dto);
        Author model = authorRepository.save(author);

        return authorMapper.map(model);
    }

    public AuthorDTO update(Long id, AuthorUpdateDTO dto) {
        var model = authorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        authorMapper.update(dto, model);

        return authorMapper.map(authorRepository.save(model));
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
    // END
}
