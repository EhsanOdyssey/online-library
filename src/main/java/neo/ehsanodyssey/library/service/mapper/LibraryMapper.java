package neo.ehsanodyssey.library.service.mapper;

import neo.ehsanodyssey.library.dal.model.Library;
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
public class LibraryMapper {

    private final ModelMapper modelMapper;

    public LibraryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Library toModel(LibraryDto dto) {
        return this.modelMapper.typeMap(LibraryDto.class, Library.class)
                .map(dto);
    }

    public LibraryDto toDto(Library model) {
        return this.modelMapper.typeMap(Library.class, LibraryDto.class)
                .map(model);
    }
}
