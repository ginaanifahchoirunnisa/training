package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentProfileCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentProfileCounterRepository extends JpaRepository<TalentProfileCounter, Integer> {

}
