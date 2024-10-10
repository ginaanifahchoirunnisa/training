
package com.tujuhsembilan.app.dto.response;
import lombok.Data;

@Data
public class UserSignInResponse {

    private String email;
    private Integer userId;
    private String accessToken;
}
