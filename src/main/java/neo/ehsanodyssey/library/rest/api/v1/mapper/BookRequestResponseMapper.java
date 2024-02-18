package neo.ehsanodyssey.library.rest.api.v1.mapper;

import neo.ehsanodyssey.library.rest.api.v1.request.CreateBookRequest;
import neo.ehsanodyssey.library.rest.api.v1.request.UpdateBookRequest;
import neo.ehsanodyssey.library.rest.api.v1.response.BookResponse;
import neo.ehsanodyssey.library.service.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Component
public class BookRequestResponseMapper {

    private final ModelMapper modelMapper;

    public BookRequestResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookDto toDto(CreateBookRequest createBookRequest) {
        return this.modelMapper.typeMap(CreateBookRequest.class, BookDto.class)
                .map(createBookRequest);
    }

    public BookDto toDto(String id, UpdateBookRequest updateUserRequest) {
        var dto = this.modelMapper.typeMap(UpdateBookRequest.class, BookDto.class)
                .map(updateUserRequest);
        dto.setId(id);
        return dto;
    }

    public BookResponse toResponse(BookDto dto) {
        return this.modelMapper.typeMap(BookDto.class, BookResponse.class)
                .map(dto);
    }
}
