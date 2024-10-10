
package com.tujuhsembilan.app;

import com.tujuhsembilan.app.dto.request.UserRegisterRequest;
import com.tujuhsembilan.app.dto.request.UserSignInRequest;
import com.tujuhsembilan.app.dto.response.UserRegistrationResponse;
import com.tujuhsembilan.app.dto.response.UserSignInResponse;
import com.tujuhsembilan.app.model.*;
import static org.mockito.ArgumentMatchers.any;
import com.tujuhsembilan.app.repository.*;

import com.tujuhsembilan.app.service.JwtService;
import com.tujuhsembilan.app.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = { ApplicationTests.class })
class ApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserClientRepository userClientRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;
	@Mock
	private UserRoleRepository userRoleRepository;

	@Mock
	private ClientPositionRepository clientPositionRepository;



	@InjectMocks
	private UserService userService;


	@Test
	@DisplayName("Sign in user")
	void LoginUserTest(){

		UserSignInRequest userSignInRequest = new UserSignInRequest("kambingmakanapa@gmail.com", "yarumput11" );
		String encodedPassword = passwordEncoder.encode(userSignInRequest.getPassword());

		User existingUser = userRepository.findByEmail(userSignInRequest.getEmail());

		String jwt = jwtService.generateToken(existingUser);

		UserSignInResponse userSignInExpecetedResponse = new UserSignInResponse();
		userSignInExpecetedResponse.setUserId(1);
		userSignInExpecetedResponse.setEmail(userSignInRequest.getEmail());
		userSignInExpecetedResponse.setAccessToken(jwt);

		//cek existing user
		when(userRepository.findByEmail(userSignInRequest.getEmail())).thenReturn(existingUser);

		//UserSignInResponse userSignInActualResponse = userService.userSignIn(userSignInRequest);

//		assertEquals(userSignInActualResponse, userSignInExpecetedResponse);



	}


	@Test
	@DisplayName("Adding User to database, Register")
	void RegisterUserTest(){
		Date dateOfBirth = new Date();
		long timeSystemInMills = System.currentTimeMillis();
		Date currentTime = new Date(timeSystemInMills);
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest("levy","you","yougoat@gmail.com","1234",'L',dateOfBirth,1,"levy you","disini");
		String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

		ClientPosition clientPosition = new ClientPosition();
		clientPosition.setClientPositionName("HRD");
		clientPosition.setClientPositionId(1);
		Optional<ClientPosition> oClientPosition = Optional.of(clientPosition);

		//assertEquals(null, userRepository.findByEmail(userRegisterRequest.getEmail()));

		User user = new User();
		user.setUserId(1);
		user.setFirstName(userRegisterRequest.getFirstName());
		user.setLastName(userRegisterRequest.getLastName());
		user.setEmail(userRegisterRequest.getEmail());
		user.setPassword(encodedPassword);
		user.setCreatedBy(userRegisterRequest.getFirstName());
		user.setCreatedTime(currentTime);
		user.setIsActive(true);

		User newUser = userRepository.save(user);


		Client client = new Client();
		client.setClientId(0);
		client.setClientName(userRegisterRequest.getFirstName()+" "+userRegisterRequest.getLastName());
		client.setSex(userRegisterRequest.getSex());
		client.setBirthDate(userRegisterRequest.getBirthDate());
		client.setEmail(userRegisterRequest.getEmail());
		client.setClientPosition(clientPosition);
		client.setAgencyAddress(userRegisterRequest.getAgencyAddress());
		client.setAgencyName(userRegisterRequest.getAgencyName());
		client.setCreatedTime(currentTime);
		client.setIsActive(true);
		Client newClient = clientRepository.save(client);

		UserClient userClient = new UserClient();
		userClient.setUserClientId(new UserClientId(user.getUserId(), client.getClientId()));
		userClient.setUserClientId(new UserClientId(user.getUserId(), client.getClientId()));
		userClient.setClient(client);
		userClient.setUser(user);
		userClient.setCreatedBy(client.getClientName());
		userClient.setCreatedTime(currentTime);
		userClientRepository.save(userClient);




		Role role= new Role();
		role.setRoleId(1);
		Optional<Role> newRole =Optional.of(role);



		UserRole userRole = new UserRole();
		userRole.setUserRoleId(new UserRoleId(user.getUserId(),role.getRoleId().intValue()));
		userRole.setUser(user);
		userRole.setRole(role);
		userRole.setCreatedBy(user.getFirstName());
		userRole.setCreatedTime(currentTime);
		userRoleRepository.save(userRole);

		when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
		when(clientPositionRepository.findById(anyInt())).thenReturn(oClientPosition);
		when(clientRepository.save(any(Client.class))).thenReturn(client);
		when(userClientRepository.save(any(UserClient.class))).thenReturn(userClient);
		when(roleRepository.findById(1)).thenReturn(newRole);
		when(userRoleRepository.save(any(UserRole.class))).thenReturn(userRole);
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
			User userArgument = invocation.getArgument(0);
			userArgument.setUserId(1); // Set userId here
			return userArgument;
		});

		UserRegistrationResponse userRegistrationActualResponse = userService.userRegistration(userRegisterRequest);

		Mockito.verify(userRepository).findByEmail(userRegisterRequest.getEmail());

		UserRegistrationResponse expectedResponseResultTest = new UserRegistrationResponse();
		expectedResponseResultTest.setName(user.getFirstName()+" "+user.getLastName());
		expectedResponseResultTest.setEmail(user.getEmail());

		assertEquals(userRegistrationActualResponse, expectedResponseResultTest);


		//UserRegistrationResponse userRegistrationResponse = userService.userRegistration(userRegisterRequest);

		//Mockito.verify(userService.userRegistration().d);
		//Mockito.verify(clientRepository, Mockito.times(1)).save(new Client(9999,userRegisterRequest.getFirstName(),userRegisterRequest.getSex(),dateOfBirth,userRegisterRequest.getEmail(), clientPosition.get(),userRegisterRequest.getAgencyAddress(),userRegisterRequest.getAgencyName(),dateOfBirth,));


		//assertEquals("levy you", userRegistrationResponse.getName());
		//assertEquals("yougoat@gmail.com", userRegistrationResponse.getEmail());


	}




	@Test
	void contextLoads() {
	}

}

