package k23cnt3.nhdLesson08.repositoty;

import k23cnt3.nhdLesson08.entity.nhdBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface nhdBookRepository extends JpaRepository<nhdBook, Long> {
}
