package pv.security.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pv.security.jwt.config.JwtService;
import pv.security.jwt.service.UserInfoService;
import pv.security.jwt.user.AuthenticationRequest;
import pv.security.jwt.user.UserInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController 
{
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return "Welcome to JWT Spring Security";
	}
	
	@PostMapping("/addUser")
	public String addUser(@RequestBody UserInfo userInfo)
	{
		return userInfoService.addUser(userInfo);
	}
	
	@PostMapping("/login")
	public String authenticate(@RequestBody AuthenticationRequest request)
	{
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		if(authenticate.isAuthenticated())
		{
			return jwtService.generateToken(request.getEmail());
		}
		else
		{
			throw new UsernameNotFoundException("Invalid user request");
		}
	}
	
	@GetMapping("/getUsers")
	public List<UserInfo> getAllUsers()
	{
		return userInfoService.getAllUser();
	}
	
	@GetMapping("/getUsers/{id}")
	public UserInfo getUser(@PathVariable("id") Integer id)
	{
		return userInfoService.getUser(id);
	}
}
