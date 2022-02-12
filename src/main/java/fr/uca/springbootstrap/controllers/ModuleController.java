package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.stream.Stream;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/module")
public class ModuleController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/register/{userId}/{moduleId}")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<MessageResponse> addUserToModule(@PathVariable Long moduleId, @PathVariable Long userId) {
		Module module = moduleRepository.findById(moduleId).orElse(null);
		User user = userRepository.findById(userId).orElse(null);
		if(user==null || module==null){
			return ResponseEntity.notFound().build();
		}
		if(module.getParticipants().stream().anyMatch(u -> u.getRoles().stream().map(Role::getName).filter(r -> r == ERole.ROLE_TEACHER).count() == 1)){
			return ResponseEntity.badRequest().body(new MessageResponse("There is already a teacher registered to the course"));
		}
		module.getParticipants().add(user);
		user.getModules().add(module);

		userRepository.save(user);
		moduleRepository.save(module);

		return ResponseEntity.ok(new MessageResponse("User successfully registered to module!"));
	}

	@PostMapping("/remove/{userId}/{moduleId}")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<MessageResponse> removeUserFromModule(@PathVariable Long moduleId, @PathVariable Long userId, HttpServletResponse response) {
		Module module = moduleRepository.findById(moduleId).orElseThrow(() ->new RuntimeException("Error: Module is not found."));
		User user = userRepository.findById(userId).orElseThrow(() ->new RuntimeException("Error: User is not found."));
		module.getParticipants().remove(user);
		user.getModules().remove(module);

		userRepository.save(user);
		moduleRepository.save(module);

		return ResponseEntity.ok(new MessageResponse("User successfully removed from module!"));
	}

}
