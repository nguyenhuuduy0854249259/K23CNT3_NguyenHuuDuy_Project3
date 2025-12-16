package k23cnt3.nhdLesson08.service;

import k23cnt3.nhdLesson08.entity.nhdAuthor;
import k23cnt3.nhdLesson08.repositoty.nhdAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class nhdAuthorService {
    @Autowired
    private nhdAuthorRepository authorRepository;
    public List<nhdAuthor> getAllAuthors() {
        return authorRepository.findAll();
    }
    public nhdAuthor saveAuthor(nhdAuthor author) {
        return authorRepository.save(author);
    }
    public nhdAuthor getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    public List<nhdAuthor> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }
}
