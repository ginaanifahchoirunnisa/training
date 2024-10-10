//package com.tujuhsembilan.app.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serial;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "talent_metadata")
//public class TalentMetadata {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "talent_id", columnDefinition = "BINARY(16)")
//    private UUID talentId;
//
//    @ManyToOne
//    @JoinColumn(name = "talent_id")
//    private Talent talent;
//
//    @Column(name = "cv_counter")
//    private Integer cvCounter;
//
//    @Column(name = "profile_counter")
//    private Integer profileCounter;
//
//    @Column(name = "total_project_completed")
//    private Short totalProjectCompleted;
//
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "created_time")
//    private LocalDate createdTime;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
//
//    @Column(name = "last_modified_time")
//    private String lastModifiedTime;
//
//
//    public UUID getTalentId() {
//        return talentId;
//    }
//
//    public void setTalentId(UUID talentId) {
//        this.talentId = talentId;
//    }
//}
