package neo.ehsanodyssey.library.rest.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.ehsanodyssey.library.rest.api.v1.mapper.UserRequestResponseMapper;
import neo.ehsanodyssey.library.rest.api.v1.request.CreateUserRequest;
import neo.ehsanodyssey.library.rest.api.v1.request.UpdateUserRequest;
import neo.ehsanodyssey.library.rest.api.v1.response.UserResponse;
import neo.ehsanodyssey.library.service.UserManagementService;
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
@RequestMapping("/v1/usermgmt")
@RequiredArgsConstructor
@Slf4j
public class UserManagementResource {

    private final UserRequestResponseMapper mapper;
    private final UserManagementService service;

    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create an user",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        var dto = this.mapper.toDto(request);
        return this.mapper.toResponse(
                this.service.createUser(dto)
        );
    }

    @Operation(summary = "Find User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get an user",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable("id") String id) {
        return this.mapper.toResponse(
                this.service.getUser(id)
        );
    }

    @Operation(summary = "Get All Users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<UserResponse> getUsers() {
        return this.service.getUsers()
                .stream()
                .map(this.mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Update User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update an user info",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("{id}")
    public UserResponse updateUser(@PathVariable("id") String id,
                                   @RequestBody UpdateUserRequest request) {
        var dto = this.mapper.toDto(id, request);
        return this.mapper.toResponse(
                this.service.updateUser(dto)
        );
    }

    @Operation(summary = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete an user",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema
                            )
                    })
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") String id) {
        this.service.deleteUser(id);
    }
}
