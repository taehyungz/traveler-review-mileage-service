package com.triple.mileage.domain.point;

import com.triple.mileage.domain.BasicEntity;

import javax.persistence.*;

@Entity
@Table(name = "point")
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
}
