package pv.security.jwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pv.security.jwt.service.UserInfoService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Lazy
	@Autowired
	private UserInfoService userInfoService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,  HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		
		if(authHeader == null || !authHeader.startsWith("Bearer"))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);  //extract the userEmail from JWT Token
		
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.userInfoService.loadUserByUsername(userEmail);
			
			if(jwtService.IsTokenValid(jwt, userDetails)) //use to update the validated user in SecurityContextHolder for further user
			{
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
