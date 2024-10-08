package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
}
