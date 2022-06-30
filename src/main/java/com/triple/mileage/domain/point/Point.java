package com.triple.mileage.domain.point;

import com.triple.mileage.domain.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

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

    public Point(String userId) {
        this.userId = userId;
        this.amount = 0;
    }

    public void versionUp() {
        this.version += 1;
    }

    public void pointUp() {
        this.amount += 1;
    }

    public void pointDownAmountOf(final int deductAmount) {
        Assert.isTrue(this.amount >= deductAmount, "차감 포인트가 현재 포인트 양을 초과합니다");
        this.amount -= deductAmount;
    }
}
