package neo.ehsanodyssey.library.dal.repository;

import neo.ehsanodyssey.library.dal.model.Library;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
public interface LibraryRepository extends CrudRepository<Library, String> {
    Optional<Library> findByUser_IdAndId(String userId, String id);
    List<Library> findAllByUser_Id(String userId);
    void deleteByUser_IdAndId(String userId, String id);
}
