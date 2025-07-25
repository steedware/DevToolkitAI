package com.steedware.toolkit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "ai.openai.api-key=test-key",
    "ai.openai.api-url=http://localhost:8080/mock"
})
class AiDevToolkitApplicationTest {

    @Test
    void contextLoads() {
    }
}
