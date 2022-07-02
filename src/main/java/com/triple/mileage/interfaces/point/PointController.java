package com.triple.mileage.interfaces.point;

import com.triple.mileage.common.response.CommonResponse;
import com.triple.mileage.domain.point.PointService;
import com.triple.mileage.domain.point.dto.PointInfo;
import com.triple.mileage.domain.point.dto.TotalPointEventInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/v1/point-events")
    public CommonResponse<List<TotalPointEventInfo>> retrieveAllUsersPoint() {
        return CommonResponse.success(pointService.getAllUserPointsWithEvents());
    }

    @GetMapping("/v1/users/points")
    public CommonResponse<Integer> retrievePoint(@RequestParam @NotEmpty String userId) {
        return CommonResponse.success(pointService.getUserPoint(userId));
    }

    @GetMapping("/v1/users/point-events")
    public CommonResponse<PointInfo> retrievePoints(@RequestParam @NotEmpty String userId) {
        return CommonResponse.success(pointService.getUserPointWithEvents(userId));
    }
}
