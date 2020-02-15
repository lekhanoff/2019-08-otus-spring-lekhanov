package ru.otus.lib.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.lib.domain.Pizza;

@Service
public interface OrderService {

    List<Pizza> processOrder();
}
