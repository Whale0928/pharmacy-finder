package app.pharmacy;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class AbstractIntegrationContainerBaseTest {

    static final GenericContainer<?> REDIS;

    static {
        REDIS = new GenericContainer<>("redis:6")
                .withExposedPorts(6379);

        REDIS.start();

        System.setProperty("spring.redis.host", REDIS.getHost());
        System.setProperty("spring.redis.port", REDIS.getMappedPort(6379).toString());
    }

}
