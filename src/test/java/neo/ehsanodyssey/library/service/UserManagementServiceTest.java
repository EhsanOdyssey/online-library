package neo.ehsanodyssey.library.service;

import neo.ehsanodyssey.library.dal.model.User;
import neo.ehsanodyssey.library.dal.repository.UserRepository;
import neo.ehsanodyssey.library.service.dto.UserDto;
import neo.ehsanodyssey.library.service.impl.DefaultUserManagementService;
import neo.ehsanodyssey.library.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-20 Feb/Tue
 **/
@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {

    @InjectMocks
    DefaultUserManagementService userManagementService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;

    @Test
    void testGetUser() {
        User user = new User();
        user.setId("6f154940-b713-4819-a135-3ce4accabbad");
        user.setFirstName("ehsan");
        user.setLastName("shahmirzaloo");
        user.setUsername("ehsan.odyssey");
        when(this.userRepository.findById(any())).thenReturn(Optional.of(user));

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        when(this.userMapper.toDto(any())).thenReturn(dto);

        UserDto userDto = this.userManagementService.getUser("123");
        assertEquals("ehsan.odyssey", userDto.getUsername());
    }
}
