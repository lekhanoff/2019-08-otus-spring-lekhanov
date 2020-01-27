package ru.otus.lib.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.otus.lib.domain.Role;
import ru.otus.lib.domain.User;
import ru.otus.lib.repository.UserRepository;

@SpringBootTest
@DisplayName("Testing UserDetailsService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserDetailsServiceImplTest {

    private static final String SECURITY = "security";

    private static final String ADMINISTRATOR = "administrator";

    private static final String USER_NAME = "admin";
    
    private static final String PASSWORD = "123";
    
    private User admin;
    private Set<Role> roles; 
    
    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @BeforeEach
    public void init() {
        roles = new HashSet<>();
        roles.add(Role.builder().roleId(Long.valueOf(1000)).sysname(ADMINISTRATOR).build());
        roles.add(Role.builder().roleId(Long.valueOf(1001)).sysname(SECURITY).build());
        
        admin = User.builder().username(USER_NAME).password(PASSWORD).isEnabled(true).roles(roles).build();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(admin);
    }
    
    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_NAME);
        assertThat(userDetails).isEqualToComparingOnlyGivenFields(admin, "username", "password");
        assertThat(userDetails.getAuthorities()).isEqualTo(
                roles.stream().map(role -> new SimpleGrantedAuthority(role.getSysname())).collect(Collectors.toSet()));
    }
}
