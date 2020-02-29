package ru.otus.lib.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import ru.otus.lib.domain.Role;
import ru.otus.lib.domain.User;
import ru.otus.lib.repository.UserRepository;
import ru.otus.lib.service.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserDetailsServiceImplTest")
public class UserDetailsServiceImplTest {

    private static final String SECURITY = "security";

    private static final String ADMINISTRATOR = "administrator";

    private static final String USER_NAME = "admin";

    private static final String PASSWORD = "123";

    @Mock
    private UserRepository userRepository;

    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void init() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().roleId(Long.valueOf(1000)).sysname(ADMINISTRATOR).build());
        roles.add(Role.builder().roleId(Long.valueOf(1001)).sysname(SECURITY).build());

        User admin = User.builder().username(USER_NAME).password(PASSWORD).isEnabled(true).roles(roles).build();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(admin);
    }

    @Test
    @DisplayName("loadUserByUsername")    
    public void loadUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_NAME);

        assertThat(userDetails.getUsername()).isEqualTo(USER_NAME);
        assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
        assertThat(userDetails.getAuthorities()).hasSize(2);
    }
}
