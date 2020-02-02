package ru.otus.lib.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.lib.domain.Role;
import ru.otus.lib.domain.User;
import ru.otus.lib.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        User applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(), applicationUser.getPassword(),
                getAuthorityList(applicationUser.getRoles()));
                                                                                                                                                  
    }

    private Set<GrantedAuthority> getAuthorityList(Set<Role> roles) {
        Set<GrantedAuthority> authoritySet = null;
        
        if(roles != null) {
            authoritySet = roles.stream().map(role -> new SimpleGrantedAuthority(role.getSysname())).collect(Collectors.toSet());
        } else {
            authoritySet = Collections.<GrantedAuthority>emptySet();
        }
        
        return authoritySet;
    }
}
