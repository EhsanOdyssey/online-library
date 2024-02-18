package neo.ehsanodyssey.library.service;

import neo.ehsanodyssey.library.service.dto.LibraryDto;

import java.util.List;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
public interface LibraryService {
    LibraryDto getUserLibrary(String userId, String libraryId);

    List<LibraryDto> getUserLibraries(String userId);

    LibraryDto createLibrary(String userId, LibraryDto dto);

    LibraryDto addBook(String userId, String libraryId, String isbn13);

    LibraryDto removeBook(String userId, String libraryId, String isbn13);

    void deleteLibrary(String userId, String libraryId);
}
