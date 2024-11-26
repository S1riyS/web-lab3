package ru.s1riys.lab3.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sprider_dots")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SpiderDotModel extends DotModel {
    @Column(name = "legs_quantity", nullable = false)
    private Integer legsQuantity;

    public SpiderDotModel(Float x, Float y, Float r) {
        super(x, y, r);
        this.legsQuantity = getRandomLegQuantity(2, 8);
    }

    private Integer getRandomLegQuantity(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}
