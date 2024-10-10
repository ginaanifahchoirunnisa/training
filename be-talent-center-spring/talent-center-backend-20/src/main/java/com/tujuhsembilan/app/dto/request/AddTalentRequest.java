package com.tujuhsembilan.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tujuhsembilan.app.model.TalentSkillset;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class AddTalentRequest {

    private MultipartFile talentPhoto;
    private String talentName;

    @Pattern(regexp = "\\d+", message = "Format kolom NIP harus berupa angka")
    private String nip;

    @Pattern(regexp = "[LP]", message = "Format kolom gender harus berupa karakter L atau P")
    private Character sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message = "Kolom deskripsi talent tidak boleh kosong")
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



