package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.model.TalentWishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalentRequestRepository extends JpaRepository<TalentRequest, Integer>, JpaSpecificationExecutor<TalentRequest> {
   // List<TalentRequest> findByTalentRequestStatusOrderByRequestDate(TalentRequestStatus talentRequestStatus,Sort sort);

    List<TalentRequest> findAll(Sort sort);

    Page<TalentRequest> findAllByOrderByRequestDateDesc(Pageable pageable);

    Page<TalentRequest> findAllByOrderByTalentRequestIdAsc(Pageable pageable);




    Page<TalentRequest> findAllByOrderByRequestDateAsc(Pageable pageable);




  //  Page<TalentRequest> findAllByTalentWishListOrderByRequestDate(TalentWishlist talentWishlist,Sort sort);






}
