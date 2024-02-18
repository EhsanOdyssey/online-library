package neo.ehsanodyssey.library.service.impl;

import jakarta.transaction.Transactional;
import neo.ehsanodyssey.library.dal.model.Book;
import neo.ehsanodyssey.library.dal.model.Library;
import neo.ehsanodyssey.library.dal.repository.BookRepository;
import neo.ehsanodyssey.library.dal.repository.LibraryRepository;
import neo.ehsanodyssey.library.exception.BookNotFoundException;
import neo.ehsanodyssey.library.exception.LibraryNotFoundException;
import neo.ehsanodyssey.library.service.LibraryService;
import neo.ehsanodyssey.library.service.UserManagementService;
import neo.ehsanodyssey.library.service.dto.LibraryDto;
import neo.ehsanodyssey.library.service.dto.UserDto;
import neo.ehsanodyssey.library.service.mapper.LibraryMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
@CacheConfig(cacheNames="librariesCache")
public class DefaultLibraryService implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;
    private final UserManagementService userManagementService;
    private final BookRepository bookRepository;
    private final Function<String, UserDto> findUserFromId;
    private final BiFunction<LibraryDto, UserDto, LibraryDto> createFunction;

    public DefaultLibraryService(LibraryRepository libraryRepository,
                                 UserManagementService userManagementService,
                                 BookRepository bookRepository,
                                 LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
        this.userManagementService = userManagementService;
        this.bookRepository = bookRepository;
        this.findUserFromId = this.userManagementService::getUser;
        BiFunction<LibraryDto, UserDto, Library> updateCreateModelFromDTO = this::updateCreateDTOAndMapToModel;
        Function<Library, LibraryDto> updateAndReturnDTO = this::createAndThenMapToDTO;
        this.createFunction = updateCreateModelFromDTO.andThen(updateAndReturnDTO);
    }

    @Override
    @Cacheable(key = "'library_' + #userId + '_' + #libraryId")
    public LibraryDto getUserLibrary(String userId, String libraryId) {
        return this.findUserFromId
                .andThen(user ->
                        this.libraryRepository
                                .findByUser_IdAndId(
                                        user.getId(),
                                        libraryId
                                )
                                .map(this.libraryMapper::toDto)
                                .orElseThrow(() -> this.createNotFoundException(userId, libraryId))
                )
                .apply(userId);
    }

    @Override
    @Cacheable(key = "'libraries_' + #userId")
    public List<LibraryDto> getUserLibraries(String userId) {
        return this.findUserFromId
                .andThen(user ->
                        this.libraryRepository
                                .findAllByUser_Id(
                                        user.getId()
                                )
                                .stream()
                                .map(this.libraryMapper::toDto)
                                .collect(Collectors.toList())
                )
                .apply(userId);
    }

    @Override
    @CachePut(key = "'libraries_' + #userId")
    public LibraryDto createLibrary(String userId, LibraryDto dto) {
//        var userDto = this.userManagementService.getUser(userId);
//        var newLibrary = createFunction.apply(dto, userDto);
//        return UUIDUtil.toString(newLibrary.getId());
        return this.findUserFromId
                .andThen(user -> createFunction.apply(dto, user))
                .apply(userId);
    }

    @Override
    @CachePut(key = "'libraries_' + #userId")
    public LibraryDto addBook(String userId, String libraryId, String isbn13) {
        return this.findUserFromId
                .andThen(user -> this.findUserLibrary(user, libraryId))
                .andThen(library -> this.addBookToUserLibrary(library, isbn13))
                .apply(userId);
    }

    @Override
    @CacheEvict(key = "'libraries_' + #userId")
    public LibraryDto removeBook(String userId, String libraryId, String isbn13) {
        return this.findUserFromId
                .andThen(user -> this.findUserLibrary(user, libraryId))
                .andThen(library -> this.removeBookFromUserLibraryIfNeeded(library, isbn13))
                .apply(userId);
    }

    @Override
    @CacheEvict(key = "'library_' + #userId + '_' + #libraryId")
    public void deleteLibrary(String userId, String libraryId) {
        var user = this.userManagementService.getUser(userId);
        this.libraryRepository.deleteByUser_IdAndId(
                user.getId(),
                libraryId
        );
    }

    private Library updateCreateDTOAndMapToModel(LibraryDto dto, UserDto user) {
        if (dto.getId() != null) {
            dto.setId(null);
        }
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUser(user);
        return this.libraryMapper.toModel(dto);
    }

    private LibraryDto createAndThenMapToDTO(Library model) {
        return this.libraryMapper.toDto(
                this.libraryRepository.save(model)
        );
    }

    private Book findBook(String isbn13) {
        return this.bookRepository
                .findByIsbn13(isbn13)
                .orElseThrow(() -> this.createBookNotFoundException(isbn13));
    }

    private Library findUserLibrary(UserDto user, String libraryId) {
        return this.libraryRepository
                .findByUser_IdAndId(
                        user.getId(),
                        libraryId
                )
                .orElseThrow(() -> this.createNotFoundException(user.getId(), libraryId));
    }

    private LibraryDto addBookToUserLibrary(Library library, String bookId) {
        var book = this.findBook(bookId);
        library.addBook(book);
        var updatedLibrary = this.libraryRepository.save(library);
        return this.libraryMapper.toDto(updatedLibrary);
    }

    private LibraryDto removeBookFromUserLibraryIfNeeded(Library library, String isbn13) {
        var book = this.findBook(isbn13);
        if (!library.getBooks().contains(book)) {
            throw this.createBookNotFoundException(isbn13);
        }
        library.removeBook(book);
        var updatedLibrary = this.libraryRepository.save(library);
        return this.libraryMapper.toDto(updatedLibrary);
    }

    private LibraryNotFoundException createNotFoundException(String userId, String libraryId) {
        return new LibraryNotFoundException(
                String.format("library with id '%s' not found for userId '%s'", libraryId, userId)
        );
    }

    private BookNotFoundException createBookNotFoundException(String isbn13) {
        return new BookNotFoundException(
                String.format("book with isbn '%s' not found", isbn13)
        );
    }
}
