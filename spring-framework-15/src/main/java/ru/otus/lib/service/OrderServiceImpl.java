package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jline.reader.LineReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.otus.lib.domain.Pizza;
import ru.otus.lib.domain.MenuItem;
import ru.otus.lib.integration.PizzeriaGateway;

@Service
public class OrderServiceImpl implements OrderService {

    private final MenuService menuService;

    private final PizzeriaGateway gateway;

    private LineReader reader;

    public OrderServiceImpl(MenuService menuService, PizzeriaGateway gateway, @Lazy LineReader reader) {
        this.menuService = menuService;
        this.gateway = gateway;
        this.reader = reader;
    }

    @Override
    public List<Pizza> processOrder() {
        System.out.println("Choose dishes from our menu: ");
        menuService.getMenu()
                .stream()
                .forEach(item -> System.out.println(new StringBuilder().append("id = ")
                        .append(item.getId())
                        .append(" name = ")
                        .append(item.getName())));
        String orderedDishes = reader
                .readLine("Choose dishes from our menu using their identifiers (by example, 1 2 7): ");
        
        List<MenuItem> items = Arrays.asList(orderedDishes.split(" "))
                .stream()
                .map(s -> menuService.findById(Integer.valueOf(s)))
                .filter(s -> s != null)
                .collect(Collectors.toList());
         
        List<Pizza> dishes = gateway.processOrder(items);
        System.out.println("Your order is ready:");
        dishes.forEach(System.out::println);
        System.out.println("Bon appetit!");
        return dishes;
    }
}
