package com.sistema.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sistema.models.ERole;
import com.sistema.models.ErrorAuth;
import com.sistema.models.Role;
import com.sistema.models.User;
import com.sistema.repository.RoleRepository;
import com.sistema.repository.UserRepository;
import com.sistema.request.LoginRequest;
import com.sistema.request.SignupRequest;
import com.sistema.response.JwtResponse;
import com.sistema.security.UserDetailsImpl;
import com.sistema.security.services.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
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

	@GetMapping("/login")
	public String signin(Model model) {
		
		LoginRequest loginRequest = new LoginRequest();
		
		System.out.println("/login: " + loginRequest.getUsername());
		System.out.println("/login: " + loginRequest.getPassword());

		ErrorAuth errorAuth = new ErrorAuth();
		model.addAttribute("errorAuth", errorAuth);

		User login = new User();
		model.addAttribute("login", login);

		User usuario = new User();
		model.addAttribute("usuario", usuario);

		return "login";
	}

	@PostMapping("/api/auth/signin")
	public String authenticateUser(@ModelAttribute("login") LoginRequest loginRequest) {
		
		String loginUsername = loginRequest.getUsername();
		String loginPassword = loginRequest.getPassword();

		String stringUsername = loginUsername;
		String[] splittedUsername = stringUsername.split(",");
		String username = splittedUsername[0];
		
		String stringPassword = loginPassword;
		String[] splittedpassword = stringPassword.split(",");
		String password = splittedpassword[0];
		
		System.out.println("/signin: " + username);
		System.out.println("/signin: " + password);

		ErrorAuth.setErro(true);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		ErrorAuth.setErro(false);

		System.out.println(roles.toString());

		JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles);

		return "login_success";
	}

	@GetMapping("/signup")
	public String signup(Model model) {

		ErrorAuth.setErro(false);

		User usuario = new User();
		model.addAttribute("usuario", usuario);

		return "register";
	}

	@PostMapping("/api/auth/signup")
	public String registerUser(@ModelAttribute("usuario") SignupRequest signUpRequest) {

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return "login";
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.isPolicyTerms());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Erro: o Role não foi encontrado."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Erro: o Role não foi encontrado."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Erro: o Role não foi encontrado."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Erro: o Role  não foi encontrado."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return "register_success";
	}
	
	
	@GetMapping("/erroLogin")
	public String errorLogin(Model model) {
		

		return "login";
	}
	
}
