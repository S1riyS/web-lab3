package ru.s1riys.lab3.models;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "results")
public class ResultModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "x", nullable = false)
    private float x;
    @Column(name = "y", nullable = false)
    private float y;
    @Column(name = "r", nullable = false)
    private float r;
    @Column(name = "is_hit", nullable = false)
    private boolean result;
    @Column(name = "execution_time", nullable = false)
    private long executionTime;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public ResultModel(float x, float y, float r, boolean result, long executionTime, ZonedDateTime createdAt) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.executionTime = executionTime;
        this.createdAt = createdAt;
    }

    public ResultModel(ResultModel sourceResult) {
        this.id = sourceResult.id;
        this.x = sourceResult.getX();
        this.y = sourceResult.getY();
        this.r = sourceResult.getR();
        this.result = checkHit();
        this.createdAt = sourceResult.createdAt;
    }

    private boolean checkHit() {
        float halfR = r / 2;
        boolean triangle = x >= 0 && y >= 0 && y <= -x + r;
        boolean circle = x <= 0 && y <= 0 && x * x + y * y <= halfR * halfR;
        boolean rectangle = x >= 0 && y <= 0 && x <= r && y >= -halfR;
        return triangle || circle || rectangle;
    }

    public String getStringSuccess() {
        return result ? "Попадание" : "Промах";
    }

    public String getClassSuccess() {
        return result ? "hit" : "miss";
    }
}
