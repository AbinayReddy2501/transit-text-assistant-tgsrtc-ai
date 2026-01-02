package com.transit.assistant.repository;

import com.transit.assistant.domain.StopTime;
import com.transit.assistant.domain.StopTimeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopTimeRepository extends JpaRepository<StopTime, StopTimeId> {
}
