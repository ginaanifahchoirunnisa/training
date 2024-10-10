package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.request.UserRegisterRequest;
import com.tujuhsembilan.app.dto.request.UserSignInRequest;
import com.tujuhsembilan.app.dto.response.ClientPositionResponse;
import com.tujuhsembilan.app.dto.response.UserRegistrationResponse;
import com.tujuhsembilan.app.dto.response.UserSignInResponse;


import com.tujuhsembilan.app.exception.EmailExistException;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPositionRepository clientPositionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserClientRepository userClientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<ClientPositionResponse> getAllClientPosition(){
        List<ClientPosition> clientPositions = clientPositionRepository.findAll();
        System.out.println(clientPositions);
        List<ClientPositionResponse> responses = new ArrayList<>();
        clientPositions.forEach(c -> {
            ClientPositionResponse temp = new ClientPositionResponse();
            temp.setId(c.getClientPositionId());
            temp.setPositionName(c.getClientPositionName());
            responses.add(temp);
        });
        return responses;
    }

    public UserRegistrationResponse userRegistration(UserRegisterRequest userRegisterRequest){
        try{
            if(userRepository.findByEmail(userRegisterRequest.getEmail())!=null){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email have registered");
            }

            String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
            long timeSystemInMills = System.currentTimeMillis();
            Date currentTime = new Date(timeSystemInMills);
            Optional<ClientPosition> clientPosition = clientPositionRepository.findById(userRegisterRequest.getClientPositionId());
            User user =new User();
            user.setFirstName(userRegisterRequest.getFirstName());
            user.setLastName(userRegisterRequest.getLastName());
            user.setEmail(userRegisterRequest.getEmail());
            user.setPassword(encodedPassword);
            user.setCreatedBy(user.getUsername());
            user.setCreatedTime(currentTime);
            user.setIsActive(true);
            User newUser =userRepository.save(user);

            Optional<Role> role = roleRepository.findById(1);
            UserRole userRole = new UserRole();
            userRole.setUserRoleId(new UserRoleId(newUser.getUserId(),role.get().getRoleId().intValue()));
            userRole.setUser(newUser);
            userRole.setRole(role.get());
            userRole.setCreatedBy(newUser.getFirstName());
            userRole.setCreatedTime(currentTime);
            userRoleRepository.save(userRole);

            Client client = new Client();
            client.setClientId(newUser.getUserId());
            client.setClientName(newUser.getFirstName()+" "+newUser.getLastName());
            client.setSex(userRegisterRequest.getSex());
            client.setBirthDate(userRegisterRequest.getBirthDate());
            client.setEmail(userRegisterRequest.getEmail());
            client.setClientPosition(clientPosition.get());
            client.setAgencyAddress(userRegisterRequest.getAgencyAddress());
            client.setAgencyName(userRegisterRequest.getAgencyName());
            client.setCreatedTime(currentTime);
            client.setIsActive(true);
            Client newClient = clientRepository.save(client);

            UserClient userClient = new UserClient();
            userClient.setUserClientId(new UserClientId(newUser.getUserId(), newClient.getClientId()));
            userClient.setClient(newClient);
            userClient.setUser(newUser);
            userClient.setCreatedBy(client.getClientName());
            userClient.setCreatedTime(currentTime);
            userClientRepository.save(userClient);

            UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
            userRegistrationResponse.setName(client.getClientName());
            userRegistrationResponse.setEmail(newUser.getEmail());

            return userRegistrationResponse;

        }catch(Exception e){
            throw  new RuntimeException(e.getMessage(), e);

        }
    }


    public UserSignInResponse userSignIn(UserSignInRequest userSignInRequest) {
        try {
            User existingUser = userRepository.findByEmail(userSignInRequest.getEmail());

//            System.out.println(existingUser);

            if (existingUser == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email is not registered");
            }

            // Verify the provided password against the stored hashed password
            if (!passwordEncoder.matches(userSignInRequest.getPassword(), existingUser.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
            }
            System.out.println("SINI MASUK");


            // Generate JWT token
            String jwt = jwtService.generateToken(existingUser);
            System.out.println("ini hasil jwt" + jwt);
            UserSignInResponse userSignInResponse = new UserSignInResponse();
            userSignInResponse.setUserId(existingUser.getUserId());
            userSignInResponse.setEmail(existingUser.getEmail());
            userSignInResponse.setAccessToken(jwt);

            return userSignInResponse;
        } catch (ResponseStatusException e) {
            throw e; // Re-throw ResponseStatusException
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while signing in.", e);
        }
    }













}
