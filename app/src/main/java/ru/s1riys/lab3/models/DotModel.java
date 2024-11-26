package ru.s1riys.lab3.models;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class DotModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @Column(name = "x", nullable = false)
    private float x;
    @Column(name = "y", nullable = false)
    private float y;
    @Column(name = "r", nullable = false)
    private float r;
    @Column(name = "is_hit", nullable = false)
    private boolean hit;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public DotModel(Float x, Float y, Float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit();
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    private boolean checkHit() {
        float halfR = r / 2;
        boolean triangle = x >= 0 && y >= 0 && y <= -x + r;
        boolean circle = x <= 0 && y <= 0 && x * x + y * y <= halfR * halfR;
        boolean rectangle = x >= 0 && y <= 0 && x <= r && y >= -halfR;
        return triangle || circle || rectangle;
    }

    public String getStringSuccess() {
        return hit ? "Попадание" : "Промах";
    }

    public String getClassSuccess() {
        return hit ? "hit" : "miss";
    }
}
