import org.example.MultiThreadServer;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MultiThreadServerTest {
    MultiThreadServer mt;
    File file = new File("foo.out");

    @BeforeEach
    public void beforeEach() {
        System.out.println("before each test");
        mt = new MultiThreadServer(new Socket());
    }

    @AfterEach
    public void afterEach() {
        System.out.println("after each test");
        mt = null;
    }

    @Test
    public void testFileName() {
        System.out.println("test file name");
        String expected = "foo.out";
        String actual = file.getName();
        assertEquals(expected, actual);
    }
}