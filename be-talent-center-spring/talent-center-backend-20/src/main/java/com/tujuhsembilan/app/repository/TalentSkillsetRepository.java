package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentSkillset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TalentSkillsetRepository extends JpaRepository<TalentSkillset, Integer> {
    List<TalentSkillset> findAllByTalent(Talent talent);

    Optional<TalentSkillset> findByTalentAndSkillset(Talent talent, Skillset skillset);
}
