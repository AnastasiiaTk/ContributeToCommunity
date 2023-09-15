package com.contributetocommunity.bom;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class Volunteer {

    private Integer id;

    private String firstName;

    private String lastName;

    private List<Job> jobs;
}
