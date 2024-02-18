package neo.ehsanodyssey.library.rest.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.ehsanodyssey.library.rest.api.v1.mapper.BookRequestResponseMapper;
import neo.ehsanodyssey.library.rest.api.v1.request.CreateBookRequest;
import neo.ehsanodyssey.library.rest.api.v1.request.UpdateBookRequest;
import neo.ehsanodyssey.library.rest.api.v1.response.BookResponse;
import neo.ehsanodyssey.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-17 Feb/Sat
 **/
@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookResource {

    private final BookRequestResponseMapper mapper;
    private final BookService service;

    @Operation(summary = "Create Book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create a book",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public BookResponse createBook(@RequestBody CreateBookRequest request) {
        var dto = this.mapper.toDto(request);
        return this.mapper.toResponse(
                this.service.createBook(dto)
        );
    }

    @Operation(summary = "Find Book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get a book",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public BookResponse getBook(@PathVariable("id") String id) {
        return this.mapper.toResponse(
                this.service.getBook(id)
        );
    }

    @Operation(summary = "Get All Books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all books",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<BookResponse> getBooks() {
        return this.service.getBooks()
                .stream()
                .map(this.mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Update a Book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update a book info",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("{id}")
    public BookResponse updateBook(@PathVariable("id") String id,
                                   @RequestBody UpdateBookRequest request) {
        var dto = this.mapper.toDto(id, request);
        return this.mapper.toResponse(
                this.service.updateBook(dto)
        );
    }

    @Operation(summary = "Delete Book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete a book",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable("id") String id) {
        this.service.deleteBook(id);
    }
}
