package ru.otus.lib.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.lib.LibraryApplication;

@SpringBootTest
public class LibraryApplicationTest {

    @Test
    public void starterTest() throws IOException {
        LibraryApplication.main(new String[] {});
        assertTrue(true);
    }
    
}
