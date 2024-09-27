package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.entity.Rent;
import com.d108.project.domain.area.analytic.repository.RentRepository;
import com.d108.project.domain.area.dto.RentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService{

    private final RentRepository rentRepository;

    @Override
    public RentDto findGuRentFee(Long id) {
        Rent rent = rentRepository.findByGuIdAndDongId(id,id);
        return RentDto.to(rent);
    }

    @Override
    public RentDto findDongRentFee(Long id) {
        Rent rent = rentRepository.findByDongId(id);
        return RentDto.to(rent);
    }
}
