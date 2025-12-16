package k23cnt3.nhdLesson07.repository;


import k23cnt3.nhdLesson07.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends
        JpaRepository<Category, Long> {
}