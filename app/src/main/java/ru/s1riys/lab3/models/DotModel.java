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
public class DotModel implements Serializable {
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
    private boolean hit;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public DotModel(float x, float y, float r, boolean hit, ZonedDateTime createdAt) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.createdAt = createdAt;
    }

    public DotModel(DotModel sourceResult) {
        this.id = sourceResult.id;
        this.x = sourceResult.getX();
        this.y = sourceResult.getY();
        this.r = sourceResult.getR();Добавить инпут в форму - селект из двух вариантов - паук и муравей. 
        SpidetDot и AntDot - наследуется от Dot. Dot теперь становится абстрактным классом. 
        * SpiderDot - legsQuantity (рандомного/либо инпут)
        * AntDot - bodyColor (рандомно или инупт). 
        this.hit = checkHit();
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
        return hit ? "Попадание" : "Промах";
    }

    public String getClassSuccess() {
        return hit ? "hit" : "miss";
    }
}
