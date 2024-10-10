package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.dto.response.ClientPositionResponse;
import com.tujuhsembilan.app.model.ClientPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientPositionRepository extends JpaRepository<ClientPosition, Integer> {

}
