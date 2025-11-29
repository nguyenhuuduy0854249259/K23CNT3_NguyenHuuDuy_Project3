package k23cnt3.nhdLesson08.service;

import k23cnt3.nhdLesson08.entity.nhdBook;
import k23cnt3.nhdLesson08.repositoty.nhdBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class nhdBookService {
    @Autowired
    private nhdBookRepository bookRepository;
    public List<nhdBook> getAllBooks() {
        return bookRepository.findAll();
    }
    public nhdBook saveBook(nhdBook book) {
        return bookRepository.save(book);
    }
    public nhdBook getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
