package ru.otus.lib.integration;

import java.util.List;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.lib.domain.Pizza;
import ru.otus.lib.domain.MenuItem;

@MessagingGateway
public interface PizzeriaGateway {

    @Gateway(requestChannel = "ordersChannel", replyChannel = "dishesChannel")
    List<Pizza> processOrder(List<MenuItem> items);
}
