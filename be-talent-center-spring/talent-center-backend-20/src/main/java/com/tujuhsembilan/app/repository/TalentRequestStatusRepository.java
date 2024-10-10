package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentRequestStatus;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TalentRequestStatusRepository extends JpaRepository<TalentRequestStatus, Integer> {
    TalentRequestStatus findByTalentRequestStatusNameIgnoreCase(String requestStatusName);
}
