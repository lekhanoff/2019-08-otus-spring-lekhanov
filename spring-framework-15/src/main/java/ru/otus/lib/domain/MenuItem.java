package ru.otus.lib.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MenuItem {

    private Integer id;
    
    private String name;
}
