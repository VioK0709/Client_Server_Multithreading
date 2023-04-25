import org.example.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainClientTest {
    Client client;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void beforeEach() {
        client = new Client(8080, "localhost");
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void afterEach() {
        System.setOut(standardOut);
    }

    @Test
    public void testSocket() {
        System.out.println("test socket");
        assertTrue(client.getPort() == 8080 && client.getHost().equals("localhost"));
    }
}