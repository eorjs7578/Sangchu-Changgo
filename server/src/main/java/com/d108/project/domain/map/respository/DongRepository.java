package com.d108.project.domain.map.respository;


import com.d108.project.domain.map.entity.Dong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DongRepository extends JpaRepository<Dong, Long> {

    List<Dong> findByCode(Long code);
}
