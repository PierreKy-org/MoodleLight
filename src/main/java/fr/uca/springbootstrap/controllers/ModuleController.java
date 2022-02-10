package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import fr.uca.springbootstrap.security.jwt.JwtUtils;
import io.cucumber.core.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	@PostMapping("/{id}/participants/{userid}")
	@PreAuthorize("hasRole('TEACHER')")
	public ResponseEntity<?> addUser(Principal principal, @PathVariable long id, @PathVariable long userid) {
		Optional<Module> omodule = moduleRepository.findById(id);
		Optional<User> ouser = userRepository.findById(userid);
		if (!omodule.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such module!"));
		}
		if (!ouser.isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: No such user!"));
		}

		Module module = omodule.get();
		User user = ouser.get();
		User actor = userRepository.findByUsername(principal.getName()).get();

		Set<User> participants = module.getParticipants();
		if ((participants.isEmpty() && actor.equals(user))
				|| participants.contains(actor)) {
			participants.add(user);
		} else {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Not allowed to add user!"));
		}
		moduleRepository.save(module);
		module.getParticipants().add(user);
		return ResponseEntity.ok(new MessageResponse("User successfully added to module!"));
	}

	@GetMapping("/{moduleName}")
	public Long getId(@PathVariable String moduleName) {
		Module module = moduleRepository.findById(moduleName).orElseThrow(() -> new RuntimeException("Error: Module is not found."));
		return module.getId();
	}
	@GetMapping("{moduleName}/allUsers")
	public Set<User> getUsersList(@PathVariable String moduleName) {
		Module module = moduleRepository.findById(moduleName).orElseThrow(() ->new RuntimeException("Error: Module is not found."));
		return module.getParticipants();
	}

	@PostMapping("/{moduleName}/participants/{userid}")
	@PreAuthorize("hasRole('TEACHER')")
	public void removeUserFromTheList(@PathVariable Long moduleName, @PathVariable Long userId) {
		Module module = moduleRepository.findById(moduleName).orElseThrow(() ->new RuntimeException("Error: Module is not found."));
		User user = userRepository.findById(userId).orElseThrow(() ->new RuntimeException("Error: User is not found."));
		module.getParticipants().remove(user);
	}



}
