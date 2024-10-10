
package com.tujuhsembilan.app.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequest {

    private String email;
    private String password;
}

