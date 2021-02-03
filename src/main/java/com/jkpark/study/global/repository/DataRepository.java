package com.jkpark.study.global.repository;

import com.jkpark.study.global.domain.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Long> {
}
