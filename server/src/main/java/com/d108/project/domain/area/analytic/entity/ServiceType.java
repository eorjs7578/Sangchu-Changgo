package com.d108.project.domain.area.analytic.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import jakarta.persistence.*;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "service")
public class ServiceType {

    @Id
    @Column(name = "service_code")
    private String serviceCode;

    @Column(name = "service_name", nullable = false)
    private String serviceName;
}
