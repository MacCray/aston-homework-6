package org.intensiv.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendEmailRequestDto(@NotNull
                                  @Email
                                  String email,
                                  @NotBlank
                                  String subject,
                                  @NotBlank
                                  String body
) {}
