package com.contributetocommunity.bom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Job {

    private Integer id;

    private String name;

    private String description;

    public Integer getId() {
        return id;
    }
}
