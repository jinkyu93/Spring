package com.jkpark.study.data.repository;

import com.jkpark.study.data.dao.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Long> {
}
