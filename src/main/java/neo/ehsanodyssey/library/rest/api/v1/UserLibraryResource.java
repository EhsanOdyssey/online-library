package neo.ehsanodyssey.library.rest.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.ehsanodyssey.library.rest.api.v1.mapper.LibraryRequestResponseMapper;
import neo.ehsanodyssey.library.rest.api.v1.request.AddRemoveBookToFromUserLibraryRequest;
import neo.ehsanodyssey.library.rest.api.v1.request.CreateUserLibraryRequest;
import neo.ehsanodyssey.library.rest.api.v1.response.LibraryResponse;
import neo.ehsanodyssey.library.service.LibraryService;
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
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserLibraryResource {

    private final LibraryRequestResponseMapper mapper;
    private final LibraryService service;

    @Operation(summary = "Create User's Library")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create a user's library",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LibraryResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("{userId}/libraries")
    public LibraryResponse createUserLibrary(@PathVariable("userId") String userId,
                                             @RequestBody CreateUserLibraryRequest request) {
        var dto = this.mapper.toDto(request);
        return this.mapper.toResponse(
                this.service.createLibrary(userId, dto)
        );
    }

    @Operation(summary = "Find User Library")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get a book",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LibraryResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{userId}/libraries/{libraryId}")
    public LibraryResponse getUserLibrary(@PathVariable("userId") String userId,
                                          @PathVariable("libraryId") String libraryId) {
        return this.mapper.toResponse(
                this.service.getUserLibrary(userId, libraryId)
        );
    }

    @Operation(summary = "Get All User's Libraries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all user's libraries",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LibraryResponse.class))
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{userId}/libraries")
    public List<LibraryResponse> getUserLibraries(@PathVariable("userId") String userId) {
        return this.service.getUserLibraries(userId)
                .stream()
                .map(this.mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Add a Book To User Library")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Add a book to the user's library",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LibraryResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("{userId}/libraries/{libraryId}/add")
    public LibraryResponse addBookToUserLibrary(@PathVariable("userId") String userId,
                                                @PathVariable("libraryId") String libraryId,
                                                @RequestBody AddRemoveBookToFromUserLibraryRequest request) {
        return this.mapper.toResponse(
                this.service.addBook(userId, libraryId, request.getIsbn13())
        );
    }

    @Operation(summary = "Remove a Book From User Library")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Remove a book from user's library",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LibraryResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("{userId}/libraries/{libraryId}/remove")
    public LibraryResponse removeBookFromUserLibrary(@PathVariable("userId") String userId,
                                                     @PathVariable("libraryId") String libraryId,
                                                     @RequestBody AddRemoveBookToFromUserLibraryRequest request) {
        return this.mapper.toResponse(
                this.service.removeBook(userId, libraryId, request.getIsbn13())
        );
    }

    @Operation(summary = "Delete User's Library")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete a user's library",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("{userId}/libraries/{libraryId}")
    public void deleteUserLibrary(@PathVariable("userId") String userId,
                                  @PathVariable("libraryId") String libraryId) {
        this.service.deleteLibrary(userId, libraryId);
    }
}
