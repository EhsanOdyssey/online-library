package neo.ehsanodyssey.library.dal.repository;

import neo.ehsanodyssey.library.dal.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
public interface BookRepository extends CrudRepository<Book, String> {
    Optional<Book> findByIsbn13(String isbn13);
}
