package com.d108.project.interfaces.api.analytics;

import com.d108.project.domain.area.dto.RentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/rent")
public interface RentApi {

    @GetMapping("/gu/{id}")
    ResponseEntity<RentDto> getGuRent(@PathVariable Long id);

    @GetMapping("/dong/{id}")
    ResponseEntity<RentDto> getDongRent(@PathVariable Long id);
}
