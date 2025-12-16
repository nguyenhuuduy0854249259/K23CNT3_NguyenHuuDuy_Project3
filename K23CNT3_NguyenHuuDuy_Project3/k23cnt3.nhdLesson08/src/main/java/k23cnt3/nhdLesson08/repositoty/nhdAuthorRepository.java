package k23cnt3.nhdLesson08.repositoty;

import k23cnt3.nhdLesson08.entity.nhdAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nhdAuthorRepository extends JpaRepository<nhdAuthor, Long> {
}
