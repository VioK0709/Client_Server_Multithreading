import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    ServerSocket server = new ServerSocket(8080);

    public ServerTest() throws IOException {
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("before each test");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("after each test");
        server = null;
    }

    @Test
    void testPort() {
        System.out.println("test number port");
        int expected = 8080;
        int actual = server.getLocalPort();
        assertEquals(expected, actual);
    }
}