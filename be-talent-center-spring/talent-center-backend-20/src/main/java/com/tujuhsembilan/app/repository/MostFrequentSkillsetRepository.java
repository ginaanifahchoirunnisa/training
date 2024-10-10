package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.MostFrequentSkillset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MostFrequentSkillsetRepository extends JpaRepository<MostFrequentSkillset, Integer> {
}
