package com.triple.mileage.domain.point;

import com.triple.mileage.domain.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id", length = 36)
    private Long id;

    @Column(unique = true, nullable = false, length = 36)
    private String userId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Long version = 0L;

    public void versionUp() {
        this.version += 1;
    }

    public void pointUp() {
        this.amount += 1;
    }

    public Point(String userId) {
        this.userId = userId;
        this.amount = 0;
    }
}
