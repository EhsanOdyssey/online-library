package neo.ehsanodyssey.library.dal.repository;

import neo.ehsanodyssey.library.dal.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
public interface UserRepository extends CrudRepository<User, String> {
}
