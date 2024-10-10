package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TalentPositionRepository extends JpaRepository<TalentPosition, Integer> {
    Optional<TalentPosition> findByTalentAndPosition(Talent talent, Position position);
}
