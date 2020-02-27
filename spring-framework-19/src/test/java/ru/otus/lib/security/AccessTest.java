package ru.otus.lib.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import ru.otus.lib.service.BookServiceImpl;

@SpringBootTest
@DisplayName("Testing access to object using SpringSecurity")
public class AccessTest {
    
    @Autowired
    private BookServiceImpl bookService; 
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void findAllUsersUsingAdminTest(){
        assertThat(bookService.getAllBooks()).isNotEmpty();
    }
    
}
