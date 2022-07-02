package com.triple.mileage.domain.point.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PointInfo {
    private final long amount;
    private final List<PointEventInfo> pointEventInfoList;
}
