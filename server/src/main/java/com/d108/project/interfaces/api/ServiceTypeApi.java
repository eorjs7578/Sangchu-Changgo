package com.d108.project.interfaces.api;

import com.d108.project.domain.area.analytic.entity.ServiceType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/service")
public interface ServiceTypeApi {

    @GetMapping("/gu/{guCode}")
    ResponseEntity<List<ServiceType>> getGuServiceType(@PathVariable Long guCode);

    @GetMapping("/dong/{dongCode}")
    ResponseEntity<List<ServiceType>> getDongServiceType(@PathVariable Long dongCode);

    @GetMapping("/area/{areaCode}")
    ResponseEntity<List<ServiceType>> getAreaServiceType(@PathVariable Long areaCode);
}
