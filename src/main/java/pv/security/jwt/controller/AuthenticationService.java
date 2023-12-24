package pv.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pv.security.jwt.config.JwtService;
import pv.security.jwt.repository.UserRepository;
import pv.security.jwt.user.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService 
{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	public AuthenticationResponse register(RegisterRequest request)
	{
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		
		repository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token()
				.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		var user = repository.findByEmail(request.getEmail())
				.orElseThrow();
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token()
				.build();
	}

}
