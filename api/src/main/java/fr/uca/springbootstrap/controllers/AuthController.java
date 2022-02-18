package fr.uca.springbootstrap.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.request.LoginRequest;
import fr.uca.springbootstrap.payload.request.SignupRequest;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
    JwtUtils jwtUtils;

	public Authentication authentication;

	public String generateJwt(String userName, String password) {
		authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userName, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtUtils.generateJwtToken(authentication);
	}

	//TODO ENVOYER UNE REQUETE AU CONTAINER JWT
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost("http://auth:8080/authentication/signin");
		request.addHeader("Content-type", "application/json");
		try {
			request.setEntity(new StringEntity(loginRequest.toString()));
			HttpResponse latestHttpResponse = httpClient.execute(request);
			return ResponseEntity.ok().body(EntityUtils.toString(latestHttpResponse.getEntity()));
		} catch (IOException e) {
			return ResponseEntity.status(500).build();
		}
	}

	User createUser(String userName, String email, String password, Set<String> strRoles) {
		User user = new User(userName, email, password);
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin" -> {
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
					}
					case "teacher" -> {
						Role modRole = roleRepository.findByName(ERole.ROLE_TEACHER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
					}
					default -> {
						Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				}
			});
		}
		user.setRoles(roles);
		return user;
	}

	//TODO ENVOYER UNE REQUETE AU CONTAINER JWT
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost("http://auth:8080/authentication/signup");
		request.addHeader("Content-type", "application/json");
		try {
			request.setEntity(new StringEntity(signUpRequest.toString()));
			HttpResponse latestHttpResponse = httpClient.execute(request);
			return ResponseEntity.ok().body(EntityUtils.toString(latestHttpResponse.getEntity()));
		} catch (IOException e) {
			return ResponseEntity.status(500).build();
		}
	}
}
