package neo.ehsanodyssey.library.service.mapper;

import neo.ehsanodyssey.library.dal.model.User;
import neo.ehsanodyssey.library.service.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toModel(UserDto dto) {
        return this.modelMapper.typeMap(UserDto.class, User.class)
                .map(dto);
    }

    public UserDto toDto(User model) {
        return this.modelMapper.typeMap(User.class, UserDto.class)
                .map(model);
    }
}
