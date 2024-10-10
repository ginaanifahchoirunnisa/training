package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.SkillsetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsetTypeRepository extends JpaRepository<SkillsetType, Integer> {
}
