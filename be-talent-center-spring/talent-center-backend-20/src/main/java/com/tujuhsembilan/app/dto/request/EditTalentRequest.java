package com.tujuhsembilan.app.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class EditTalentRequest {
    private Integer talentId;
    private MultipartFile talentPhoto;
    private String talentName;

    @Pattern(regexp = "\\d+", message = "Format kolom NIP harus berupa angka")
    private String nip;
    private Character sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String talentDescription;
    private MultipartFile cv;
    private Short talentExperience;
    private Integer talentLevelId;

    private Integer talentStatusId;
    private Short projectCompleted;
    private List<TalentPositionsRequest> position;
    private List<TalentSkillsetRequest> skillSet;
    private String email;
    private String cellphone;
    private Integer employeeStatusId;
    private Boolean talentAvailability;
    private String videoUrl;
}
