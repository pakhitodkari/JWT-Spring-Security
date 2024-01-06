package pv.security.jwt.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pv.security.jwt.repository.UserInfoRepository;
import pv.security.jwt.user.UserInfo;

@Service
public class UserInfoService implements UserDetailsService{

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	Optional<UserInfo> userInfo = userInfoRepository.findByEmail(username);
	
		return userInfo.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found "+username));
	}
	
	public String addUser(UserInfo userInfo)
	{
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userInfoRepository.save(userInfo);
		return "User Added Successfully";
	}
	
	public List<UserInfo> getAllUser()
	{
		return userInfoRepository.findAll();
	}
	
	public UserInfo getUser(Integer id)
	{
		return userInfoRepository.findById(id).get();
	}

}
