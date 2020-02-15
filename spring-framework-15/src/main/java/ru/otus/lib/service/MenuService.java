package ru.otus.lib.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.lib.domain.MenuItem;

@Service
public interface MenuService {
    
    List<MenuItem> getMenu();
    
    MenuItem findById(Integer id);

}
