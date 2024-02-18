package neo.ehsanodyssey.library.rest.api.v1.mapper;

import neo.ehsanodyssey.library.rest.api.v1.request.CreateUserLibraryRequest;
import neo.ehsanodyssey.library.rest.api.v1.response.LibraryResponse;
import neo.ehsanodyssey.library.service.dto.LibraryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Component
public class LibraryRequestResponseMapper {

    private final ModelMapper modelMapper;

    public LibraryRequestResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LibraryDto toDto(CreateUserLibraryRequest createUserLibraryRequest) {
        return this.modelMapper.typeMap(CreateUserLibraryRequest.class, LibraryDto.class)
                .map(createUserLibraryRequest);
    }

    public LibraryResponse toResponse(LibraryDto dto) {
        return this.modelMapper.typeMap(LibraryDto.class, LibraryResponse.class)
                .map(dto);
    }
}
