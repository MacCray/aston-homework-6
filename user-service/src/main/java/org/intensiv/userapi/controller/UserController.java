package org.intensiv.userapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.intensiv.userapi.dto.request.CreateUserRequestDto;
import org.intensiv.userapi.dto.request.UpdateUserRequestDto;
import org.intensiv.userapi.dto.response.UserResponseDto;
import org.intensiv.userapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/userapi/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    @Operation(summary = "Создание User", description = "Метод для создания User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User создан",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "User с таким email уже существует",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные нового User",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateUserRequestDto.class)
                    )
            )
            @RequestBody @Valid CreateUserRequestDto dto) {
        return userService.createUser(dto);
    }

    @Operation(summary = "Получение User", description = "Метод для поиска User по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User с таким id не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public UserResponseDto getUser(
            @Parameter(required = true)
            @PathVariable @NotNull @Min(1) Long id) {
        return userService.getUser(id);
    }


    @Operation(summary = "Получение всех User", description = "Метод для получения всех User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех User",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    )
            )
    })
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Обновление User", description = "Метод для обновления данных User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User обновлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User с таким id не существует",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "User с таким email уже существует",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            )
    })
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto updateUser(@Parameter(required = true) @PathVariable @NotNull @Min(1) Long id,
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                              description = "Данные обновленного User",
                                              required = true,
                                              content = @Content(
                                                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                      schema = @Schema(implementation = UpdateUserRequestDto.class)
                                              )
                                      )
                                      @RequestBody @Valid UpdateUserRequestDto dto) {
        return userService.updateUser(id, dto);
    }

    @Operation(summary = "Удаление User", description = "Метод для удаления User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User удален",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User с таким id не существует",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(required = true) @PathVariable @NotNull @Min(1) Long id) {
        userService.deleteUser(id);
    }
}
