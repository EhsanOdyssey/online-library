package neo.ehsanodyssey.library.service;

import neo.ehsanodyssey.library.service.dto.UserDto;

import java.util.List;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
public interface UserManagementService {
    UserDto getUser(String id);

    List<UserDto> getUsers();

    UserDto createUser(UserDto dto);

    UserDto updateUser(UserDto dto);

    void deleteUser(String id);
}
