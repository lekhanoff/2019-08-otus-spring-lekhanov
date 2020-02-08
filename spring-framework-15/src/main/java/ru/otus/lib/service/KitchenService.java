package ru.otus.lib.service;

import ru.otus.lib.domain.Pizza;
import ru.otus.lib.domain.MenuItem;

public interface KitchenService {

    Pizza process(MenuItem menuItem);
}
