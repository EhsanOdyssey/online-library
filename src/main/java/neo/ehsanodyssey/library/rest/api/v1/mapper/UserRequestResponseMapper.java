package neo.ehsanodyssey.library.rest.api.v1.mapper;

import neo.ehsanodyssey.library.rest.api.v1.request.CreateUserRequest;
import neo.ehsanodyssey.library.rest.api.v1.request.UpdateUserRequest;
import neo.ehsanodyssey.library.rest.api.v1.response.UserResponse;
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
public class UserRequestResponseMapper {

    private final ModelMapper modelMapper;

    public UserRequestResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDto(CreateUserRequest createUserRequest) {
        return this.modelMapper.typeMap(CreateUserRequest.class, UserDto.class)
                .map(createUserRequest);
    }

    public UserDto toDto(String id, UpdateUserRequest updateUserRequest) {
        var dto = this.modelMapper.typeMap(UpdateUserRequest.class, UserDto.class)
                .map(updateUserRequest);
        dto.setId(id);
        return dto;
    }

    public UserResponse toResponse(UserDto dto) {
        return this.modelMapper.typeMap(UserDto.class, UserResponse.class)
                .map(dto);
    }
}
