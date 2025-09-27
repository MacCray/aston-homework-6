package org.intensiv.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.intensiv.notificationservice.dto.SendEmailRequestDto;
import org.intensiv.notificationservice.service.EmailService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificationapi/")
@RequiredArgsConstructor
class EmailController {
    private final EmailService emailService;

    @PostMapping(path = "/send-email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmail(@RequestBody @Valid SendEmailRequestDto request) {
        emailService.sendEmail(request.email(), request.subject(), request.body());
    }
}
