package ru.otus.lib.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.service.OrderService;

@RequiredArgsConstructor
@ShellComponent
public class OrderShell {

    private final OrderService orderService;
    
    @ShellMethod(value = "Order creation", key = {"order", "o"})
    public void order() {
        orderService.processOrder();
    }
}
