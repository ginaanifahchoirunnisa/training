package com.tujuhsembilan.app.dto.request;

//import com.tujuhsembilan.app.validation.FileSize;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentSaveRequest {

//    @FileSize(value = 1048576, message = "File size exceeds the allowed limit (1MB)")
    private MultipartFile talentPhoto;

    @NotBlank(message = "Kolom Nama Talent tidak boleh kosong")
    @Size(max = 255, message = "Panjang kolom \"Nama Talent\" tidak boleh lebih dari 255")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Kolom Nama Talent hanya boleh mengandung huruf dan spasi")
    private String talentName;

    @NotNull(message = "Kolom Status Talent tidak boleh kosong")
    private Integer talentStatusId;

    @NotBlank(message = "Kolom Nomor Induk Pegawai tidak boleh kosong")
    @Pattern(regexp = "\\d+", message = "Format kolom NIP harus berupa angka")
    private String nip;

//    @NotNull(message = "Kolom gender tidak boleh kosong")
//    @Pattern(regexp = "[LP]", message = "Kolom gender harus 'L' atau 'P'")
    private Character sex;

    @NotNull(message = "Kolom Tangal Lahir tidak boleh kosong")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dob;

    @NotBlank(message = "Kolom Deskripsi Talent tidak boleh kosong")
    @Size(max = 4000, message = "Panjang kolom \"Deskripsi Talent\" tidak boleh lebih dari 4000")
    private String talentDescription;

    private MultipartFile cvUrl;

    @NotNull(message = "Kolom Pengalaman tidak boleh kosong")
    private Short talentExperience;

    @NotNull(message = "Kolom Level tidak boleh kosong")
    private Integer talentLevelId;

    @NotNull(message = "Kolom Deskripsi Project tidak boleh kosong")
    private Short projectCompleted;

    private List<PositionIdRequest> positions;

    private List<SkillSetIdRequest> skillSets;

    @Email(message = "Format kolom email tidak sesuai")
    @NotBlank(message = "Kolom Email tidak boleh kosong")
    @Size(max = 100, message = "Panjang kolom \"email\" tidak boleh lebih dari 100")
    private String email;

    @NotBlank(message = "Kolom No Handphone tidak boleh kosong")
    @Pattern(regexp = "\\d+", message = "Format kolom NIP harus berupa angka")
    @Size(max = 12, message = "Panjang kolom No Handphone tidak boleh lebih dari 12 digit")
    private String cellphone;

    @NotNull(message = "Kolom Status Pegawai tidak boleh kosong")
    private Integer employeeStatusId;

    @Size(max = 255, message = "Panjang kolom Biography Video Url tidak boleh lebih dari 255")
    @NotBlank(message = "Kolom Biography Video Url tidak boleh kosong")
    private String videoUrl;
}
