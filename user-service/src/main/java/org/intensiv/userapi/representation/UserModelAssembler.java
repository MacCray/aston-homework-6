package org.intensiv.userapi.representation;

import org.intensiv.userapi.controller.UserController;
import org.intensiv.userapi.dto.response.UserResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {
    @Override
    public EntityModel<UserResponseDto> toModel(UserResponseDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class).getUser(dto.id())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"),
                linkTo(methodOn(UserController.class).updateUser(dto.id(), null)).withRel("update"),
                linkTo(methodOn(UserController.class).delete(dto.id())).withRel("delete")
        );
    }
}
