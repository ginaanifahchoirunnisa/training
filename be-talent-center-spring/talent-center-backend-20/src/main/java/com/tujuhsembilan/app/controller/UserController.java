package com.tujuhsembilan.app.controller;

import com.tujuhsembilan.app.dto.request.UserRegisterRequest;
import com.tujuhsembilan.app.dto.request.UserSignInRequest;
import com.tujuhsembilan.app.dto.response.ClientPositionResponse;
import com.tujuhsembilan.app.dto.response.UserRegistrationResponse;
import com.tujuhsembilan.app.dto.response.UserSignInResponse;
import com.tujuhsembilan.app.model.ClientPosition;
//import com.tujuhsembilan.app.service.TalentManagementService;
import com.tujuhsembilan.app.service.UserService;
import com.tujuhsembilan.app.util.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tujuhsembilan.app.util.ResponseMessages.*;

@RestController
@RequestMapping("/user-management")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/client-position-option-lists")
    public ResponseEntity<?> getListClientPosition(){
       try{
           return ResponseHandler.generateResponse("Get List Position Success", HttpStatus.OK, userService.getAllClientPosition());
       }catch (Exception e){
           return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping ("/users/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        try{
            UserRegistrationResponse userRegistrationResponse = userService.userRegistration(userRegisterRequest);
            return new ResponseEntity<>(SIGN_UP_SUCESS , HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseHandler.generateResponse(SIGN_UP_FAILED,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/users/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInRequest userSignInRequest){
        try{
            UserSignInResponse userSignInResponse = userService.userSignIn(userSignInRequest);
            return new ResponseEntity<>(SIGN_IN_FAILED, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseHandler.generateResponse(SIGN_IN_SUCESS,HttpStatus.BAD_REQUEST);
        }

    }

}
