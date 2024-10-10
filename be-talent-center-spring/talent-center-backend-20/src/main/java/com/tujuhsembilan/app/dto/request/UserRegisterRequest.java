package com.tujuhsembilan.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NotEmpty(message = "'First Name' must be fill in")
    private String firstName;

    @NotEmpty(message = "'Last Name' must be fill in")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 5)
    private String password;


    @Pattern(regexp = "[LP]", message = "Format kolom gender harus berupa karakter L atau P")
    private char sex;

    @NotNull(message = "Date of Birth must be fill in")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private Integer clientPositionId;

    private String agencyName;

    private String agencyAddress;
}
