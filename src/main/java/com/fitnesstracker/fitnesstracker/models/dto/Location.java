package com.fitnesstracker.fitnesstracker.models.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {
    private String name;
    private String region;
    private String country;
    private String lat;
    private String lon;
    private String tz_id;
    private String localtime_epoch;
    private String localtime;
}

