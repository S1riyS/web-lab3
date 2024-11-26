package ru.s1riys.lab3.dto;

import lombok.Data;

@Data
public class ResponseDotDTO {
    public Float x;
    public Float y;
    public Float r;
    public Boolean isHit;
    public String createdAt;
}
