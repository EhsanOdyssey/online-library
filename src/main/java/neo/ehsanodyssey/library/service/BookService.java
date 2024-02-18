package neo.ehsanodyssey.library.service;

import neo.ehsanodyssey.library.service.dto.BookDto;

import java.util.List;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
public interface BookService {
    BookDto getBook(String id);

    List<BookDto> getBooks();

    BookDto createBook(BookDto dto);

    BookDto updateBook(BookDto dto);

    void deleteBook(String id);
}
