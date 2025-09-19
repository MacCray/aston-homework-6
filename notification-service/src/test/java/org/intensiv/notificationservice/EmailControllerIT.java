package org.intensiv.notificationservice;

import org.intensiv.notificationservice.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class})
@Testcontainers
class EmailControllerIT {
    @Container
    static final GenericContainer<?> mailpitContainer = new GenericContainer<>("axllent/mailpit:v1.27")
            .withExposedPorts(1025, 8025)
            .waitingFor(Wait.forLogMessage(".*accessible via.*", 1));
    private static RestClient mailpitClient;
    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void configureMail(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", mailpitContainer::getHost);
        registry.add("spring.mail.port", mailpitContainer::getFirstMappedPort);
        registry.add("mailpit.web.port", () -> mailpitContainer.getMappedPort(8025));
    }

    @BeforeAll
    static void setUpMailpitClient() {
        mailpitClient = RestClient.builder()
                .baseUrl("http://" + mailpitContainer.getHost() + ":"
                        + mailpitContainer.getMappedPort(8025) + "/api/v1")
                .build();
    }

    @Test
    @DisplayName("Отправка email с валидными данным, возвращает 200 и отправляет письмо")
    void sendEmail_withValidData_ReturnsOK() {
        SendEmailRequestDto request = new SendEmailRequestDto(
                "test@mail.com",
                "Test Subject",
                "Test Body"
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/notificationapi/send-email",
                request,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Письмо успешно отправлено на email: test@mail.com"));

        String messages = mailpitClient.get()
                .uri("/messages")
                .retrieve()
                .body(String.class);

        assertNotNull(messages);
        assertTrue(messages.contains("test@mail.com"));
        assertTrue(messages.contains("Test Subject"));
        assertTrue(messages.contains("Test Body"));
    }
}
