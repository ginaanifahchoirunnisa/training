package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "talent", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Talent {
    @Id
    @Column(name = "talent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer talentId;

    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TalentCvCounter> talentCvCounters;

    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TalentProfileCounter> talentProfileCounters;

    @ManyToOne
    @JoinColumn(name = "talent_level_id")
    private TalentLevel talentLevel;

    @ManyToOne
    @JoinColumn(name = "talent_status_id")
    private TalentStatus talentStatus;

    @ManyToOne
    @JoinColumn(name = "employee_status_id")
    private EmployeeStatus employeeStatus;

    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TalentPosition> talentPositions;

    @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TalentWishlist> talentWishlists;

    @OneToMany(mappedBy = "talent", fetch = FetchType.LAZY)
    private List<TalentSkillset> talentSkillsets;

    @Column(name = "talent_name")
    private  String talentName;

    @Column(name = "talent_photo_url")
    private String talentPhotoUrl;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "sex")
    private Character sex;

    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "talent_description")
    private String talentDescription;

    @Column(name = "talent_cv_url")
    private String talentCvUrl;

    @Column(name = "experience")
    private Short experience;


  //  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "email yang dimasukkan tidak dalam format yang valid")
  @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "biography_video_url")
    private  String biographyVideoUrl;

    @Column(name = "total_project_completed")
    private Short totalProjectCompleted;

//    @Column(name = "talent_availibility")
//    private Boolean talentAvailibility;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

}
