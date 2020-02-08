package ru.otus.lib.service;

import org.springframework.stereotype.Service;

import ru.otus.lib.domain.Pizza;
import ru.otus.lib.domain.MenuItem;

@Service
public class PizzaKitchenService implements KitchenService {

    @Override
    public Pizza process(MenuItem menuItem) {
        return Pizza.builder().name(menuItem.getName()).build();
    }

}
