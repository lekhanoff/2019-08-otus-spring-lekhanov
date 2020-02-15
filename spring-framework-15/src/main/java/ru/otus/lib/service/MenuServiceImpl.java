package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.lib.domain.MenuItem;

@Service
public class MenuServiceImpl implements MenuService {

    private static final List<MenuItem> MENU_ITEMS = Arrays.asList(
            MenuItem.builder().id(1).name("Neapolitan Pizza").build(),
            MenuItem.builder().id(2).name("Chicago Pizza").build(),
            MenuItem.builder().id(3).name("New York-Style Pizza").build(),
            MenuItem.builder().id(4).name("Sicilian Pizza").build(),
            MenuItem.builder().id(5).name("Greek Pizza").build(),
            MenuItem.builder().id(6).name("California Pizza").build(),
            MenuItem.builder().id(7).name("Detroit Pizza").build());

    @Override
    public List<MenuItem> getMenu() {
        return MENU_ITEMS;
    }

    @Override
    public MenuItem findById(Integer id) {
        return MENU_ITEMS.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }
}
