package ru.s1riys.lab3.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ant_dots")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AntDotModel extends DotModel {
    @Column(name = "body_color", nullable = false)
    private String bodyColor;

    public AntDotModel(Float x, Float y, Float r) {
        super(x, y, r);
        this.bodyColor = getRandomBodyColor();
    }

    private String getRandomBodyColor() {
        String[] colors = { "orange", "yellow", "purple", "pink" };
        return colors[(int) (Math.random() * colors.length)];
    }
}
