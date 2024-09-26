package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.dto.RentDto;

public interface RentService {

    RentDto findGuRentFee(Long id);
    RentDto findDongRentFee(Long id);

}
