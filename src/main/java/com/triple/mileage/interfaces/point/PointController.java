package com.triple.mileage.interfaces.point;

import com.triple.mileage.common.response.CommonResponse;
import com.triple.mileage.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/v1/users/points")
    public CommonResponse retrievePoint(@RequestParam @NotEmpty String userId) {
        return CommonResponse.success(pointService.getUserPoint(userId));
    }

    @GetMapping("/v1/users/point-events")
    public CommonResponse retrievePoints(@RequestParam @NotEmpty String userId) {
        return CommonResponse.success(pointService.getUserPointWithEvents(userId));
    }
}
