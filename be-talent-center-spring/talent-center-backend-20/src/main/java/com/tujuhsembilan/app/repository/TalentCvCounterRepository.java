package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentCvCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentCvCounterRepository extends JpaRepository<TalentCvCounter, Integer> {
}
