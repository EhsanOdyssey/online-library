package neo.ehsanodyssey.library.service;

import neo.ehsanodyssey.library.dal.model.Book;
import neo.ehsanodyssey.library.dal.model.Library;
import neo.ehsanodyssey.library.dal.model.User;
import neo.ehsanodyssey.library.dal.repository.BookRepository;
import neo.ehsanodyssey.library.dal.repository.LibraryRepository;
import neo.ehsanodyssey.library.dal.repository.UserRepository;
import neo.ehsanodyssey.library.service.dto.BookDto;
import neo.ehsanodyssey.library.service.dto.LibraryDto;
import neo.ehsanodyssey.library.service.dto.UserDto;
import neo.ehsanodyssey.library.service.impl.DefaultLibraryService;
import neo.ehsanodyssey.library.service.mapper.LibraryMapper;
import neo.ehsanodyssey.library.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-20 Feb/Tue
 **/
class LibraryServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private LibraryMapper libraryMapper;
    @InjectMocks
    private DefaultLibraryService defaultLibraryService;

    static User user;
    static UserDto userDto;
    static Book javaBook;
    static BookDto javaBookDto;
    static Book j2eeBook;
    static Book hibernateBook;
    static Library library;
    static LibraryDto libraryDto;
    static Library libraryAfterBookAdded;
    static LibraryDto libraryAfterBookAddedDto;

    @BeforeAll
    public static void init() {
        user = new User();
        user.setId("6f154940-b713-4819-a135-3ce4accabbad");
        user.setFirstName("ehsan");
        user.setLastName("shahmirzaloo");
        user.setUsername("ehsan.odyssey");

        userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());

        javaBook = new Book();
        javaBook.setId("67542bd3-8284-49f4-b3bd-8017f06ed197");
        javaBook.setIsbn13("jc_001");
        javaBook.setTitle("Java Core");
        javaBook.setPages(100);

        j2eeBook = new Book();
        j2eeBook.setId("e36d9510-9d92-4f6c-91fb-f7c1e69c9283");
        j2eeBook.setIsbn13("web-800");
        j2eeBook.setTitle("J2EE");
        j2eeBook.setPages(600);

        hibernateBook = new Book();
        hibernateBook.setId("0719d661-f2fb-4186-90bf-1ed1a4b02628");
        hibernateBook.setIsbn13("hb_jpa-100");
        hibernateBook.setTitle("Hibenate");
        hibernateBook.setPages(500);

        library = new Library();
        library.setId("082a3c02-dc15-4d8c-ab1a-ee33ff6ce389");
        library.setName("My Java");
        library.setUser(user);
        library.setActive(true);
        library.setCreatedAt(LocalDateTime.now());

        libraryDto = new LibraryDto();
        libraryDto.setId(library.getId());
        libraryDto.setName(library.getName());
        libraryDto.setUser(userDto);
        libraryDto.setActive(library.isActive());
        libraryDto.setCreatedAt(library.getCreatedAt());

        libraryAfterBookAdded = new Library();
        libraryAfterBookAdded.setId(library.getId());
        libraryAfterBookAdded.setUser(library.getUser());
        libraryAfterBookAdded.setName(library.getName());
        libraryAfterBookAdded.setCreatedAt(library.getCreatedAt());
        libraryAfterBookAdded.setActive(library.isActive());
        libraryAfterBookAdded.setBooks(List.of(javaBook));

        libraryAfterBookAddedDto = new LibraryDto();
        libraryAfterBookAddedDto.setId(library.getId());
        libraryAfterBookAddedDto.setUser(userDto);
        libraryAfterBookAddedDto.setName(library.getName());
        libraryAfterBookAddedDto.setCreatedAt(library.getCreatedAt());
        libraryAfterBookAddedDto.setActive(library.isActive());

        javaBookDto = new BookDto();
        javaBookDto.setId(javaBook.getId());
        javaBookDto.setIsbn13(javaBook.getIsbn13());
        javaBookDto.setTitle(javaBook.getTitle());
        javaBookDto.setPages(javaBook.getPages());

        libraryAfterBookAddedDto.setBooks(List.of(javaBookDto));
    }

    @BeforeEach
    public void initBeforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLibrary_thenGetCreatedLibraryDTO() {
        lenient().when(this.userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        lenient().when(this.userMapper.toDto(any(User.class)))
                .thenReturn(userDto);
        lenient().when(this.libraryMapper.toModel(any(LibraryDto.class)))
                .thenReturn(library);
        lenient().when(this.libraryRepository.save(any(Library.class)))
                .thenReturn(library);
        lenient().when(this.libraryMapper.toDto(any(Library.class)))
                .thenReturn(libraryDto);

        var createLibraryDto = new LibraryDto();
        createLibraryDto.setName("Test");
        var createdLibrary = this.defaultLibraryService.createLibrary(user.getId(), createLibraryDto);
        assertEquals(user.getUsername(), createdLibrary.getUser().getUsername());
    }

    @Test
    void createLibrary_thenGetUniqueConstraintException_UniqueUserAndLibrary() {
        var constraintEntryValue = user.getId() + "-" + library.getName();
        lenient().when(this.userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        lenient().when(this.userMapper.toDto(any(User.class)))
                .thenReturn(userDto);
        lenient().when(this.libraryMapper.toModel(any(LibraryDto.class)))
                .thenReturn(library);
        lenient().when(this.libraryRepository.save(any(Library.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry '" + constraintEntryValue + "' for key 'library.UniqueUserAndLibrary'"));
        lenient().when(this.libraryMapper.toDto(any(Library.class)))
                .thenReturn(libraryDto);

        var createLibraryDto = new LibraryDto();
        createLibraryDto.setName("Test");
        assertThatThrownBy(() -> this.defaultLibraryService.createLibrary(user.getId(), createLibraryDto))
                .isInstanceOf(
                        DataIntegrityViolationException.class
                ).satisfies(ex -> {
                    DataIntegrityViolationException exception = (DataIntegrityViolationException) ex;
                    assertThat(exception.getMessage())
                            .contains(constraintEntryValue);
                });
    }

    @Test
    void addBook_thenGetSuccessfulWithAddedBookToLibraryDTO() {
        lenient().when(this.userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        lenient().when(this.userMapper.toDto(any(User.class)))
                .thenReturn(userDto);
        lenient().when(this.libraryRepository.findByUser_IdAndId(user.getId(), library.getId()))
                .thenReturn(Optional.of(library));
        lenient().when(this.bookRepository.findByIsbn13(javaBook.getIsbn13()))
                .thenReturn(Optional.of(javaBook));
        lenient().when(this.libraryRepository.save(any(Library.class)))
                .thenReturn(libraryAfterBookAdded);
        lenient().when(this.libraryMapper.toDto(any(Library.class)))
                .thenReturn(libraryAfterBookAddedDto);
        assertEquals(null, library.getBooks());
        var libraryDto = this.defaultLibraryService.addBook(user.getId(), library.getId(), javaBook.getIsbn13());
        assertEquals(1, libraryDto.getBooks().size());
    }
}
