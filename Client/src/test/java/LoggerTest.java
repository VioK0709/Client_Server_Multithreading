import org.example.Logger;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerTest {
    Logger log;

    @BeforeEach
    public void beforeEach() {
        System.out.println("before each test");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("after each test");
        log = null;
    }

    @Test
    public void testLog() {
        System.out.println("test log");
        String msg = "";
        final String original = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "] " + msg;
        final String argument = "";
        final String result = original.concat(argument);
        assertEquals(original, result);
    }
}