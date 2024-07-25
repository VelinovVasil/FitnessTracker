package com.fitnesstracker.fitnesstracker.models.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Condition {
    private String text;
    private String icon;
    private String code;
}
