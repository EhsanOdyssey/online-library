package neo.ehsanodyssey.library.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import neo.ehsanodyssey.library.dal.model.Book;
import neo.ehsanodyssey.library.dal.repository.BookRepository;
import neo.ehsanodyssey.library.exception.BookNotFoundException;
import neo.ehsanodyssey.library.service.BookService;
import neo.ehsanodyssey.library.service.dto.BookDto;
import neo.ehsanodyssey.library.service.mapper.BookMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
@RequiredArgsConstructor
public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BiFunction<Book, BookDto, Book> updateModelFromDTO = this::updateModelWithExpectedDTOValues;
    private final Function<Book, BookDto> updateAndReturnDTO = this::updateAndThenMapToDTO;
    private final BiFunction<Book, BookDto, BookDto> updateFunction = updateModelFromDTO.andThen(updateAndReturnDTO);


    @Override
    @Cacheable(value = "userCache", key = "'book_' + #id")
    public BookDto getBook(String id) {
        return this.bookRepository
                .findById(id)
                .map(this.bookMapper::toDto)
                .orElseThrow(() -> this.createNotFoundException(id));
    }

    @Override
    public List<BookDto> getBooks() {
        var books = this.bookRepository.findAll();
        return StreamSupport
                .stream(books.spliterator(), false)
                .map(this.bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(BookDto dto) {
        if (dto.getId() != null) {
            dto.setId(null);
        }
        return this.bookMapper.toDto(
                this.bookRepository.save(
                        this.bookMapper.toModel(dto)
                )
        );
    }

    @Override
    public BookDto updateBook(BookDto dto) {
        return this.bookRepository
                .findById(dto.getId())
                .map(model -> this.updateFunction.apply(model, dto))
                .orElseThrow(() -> this.createNotFoundException(dto.getId().toString()));
    }

    @Override
    public void deleteBook(String id) {
        this.bookRepository.deleteById(id);
    }

    private Book updateModelWithExpectedDTOValues(Book model, BookDto dto) {
        model.setTitle(dto.getTitle());
        model.setIsbn13(dto.getIsbn13());
        model.setPages(dto.getPages());
        return model;
    }

    private BookDto updateAndThenMapToDTO(Book model) {
        return this.bookMapper.toDto(
                this.bookRepository.save(model)
        );
    }

    private BookNotFoundException createNotFoundException(String id) {
        return new BookNotFoundException(
                String.format("book with id '%s' not found", id)
        );
    }
}
