package com.d108.project.domain.map.respository;

import com.d108.project.domain.map.entity.Gu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuRepository extends JpaRepository<Gu, Long> {

    List<Gu> findAllByOrderByGuName();
}
