package com.application.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class BookDto {

    private String title;

    private Double price;

    private Boolean availability;

    private Short rating;
}
