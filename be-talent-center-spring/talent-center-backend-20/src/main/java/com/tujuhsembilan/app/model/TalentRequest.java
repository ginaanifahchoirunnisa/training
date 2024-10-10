package com.tujuhsembilan.app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "talent_request")
public class TalentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talent_request_id")
    private Integer talentRequestId;

    @ManyToOne
    @JoinColumn(name = "talent_request_status_id")
    private TalentRequestStatus talentRequestStatus;

    @ManyToOne
    @JoinColumn(name = "talent_wishlist_id")
    private TalentWishlist talentWishList;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "request_reject_reason")
    private String requestRejectReason;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;


}
