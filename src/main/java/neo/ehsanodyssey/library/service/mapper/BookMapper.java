package neo.ehsanodyssey.library.service.mapper;

import neo.ehsanodyssey.library.dal.model.Book;
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
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Book toModel(BookDto dto) {
        return this.modelMapper.typeMap(BookDto.class, Book.class)
                .map(dto);
    }

    public BookDto toDto(Book model) {
        return this.modelMapper.typeMap(Book.class, BookDto.class)
                .map(model);
    }
}
