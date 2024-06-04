package com.example.jdbcpractice.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Test {
    @NotNull
    private String description1;

    @NotEmpty
    private String description2;

    @NotBlank
    private String description3;

    @Min(10)
    @Max(50)
    private Integer sampleId;

}
