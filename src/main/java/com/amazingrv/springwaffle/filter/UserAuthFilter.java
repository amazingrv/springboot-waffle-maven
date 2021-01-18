package com.amazingrv.springwaffle.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;
import waffle.servlet.WindowsPrincipal;
import waffle.spring.WindowsAuthenticationToken;

/**
 * Filter to determine the identity and role for user once per request
 *
 * @author rjat3
 */
@Slf4j
@Component
public class UserAuthFilter extends OncePerRequestFilter {

	/**
	 * Helper method to determine the role of the user
	 *
	 * @param authentication
	 * @param isAdmin
	 */
	private void addRoleToAuthentication(WindowsAuthenticationToken authentication, boolean isAdmin) {
		authentication.getAuthorities().clear();
		authentication.getAuthorities().add(new SimpleGrantedAuthority("ROLE_USER"));
		if (isAdmin) {
			authentication.getAuthorities().add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		final SecurityContext sec = SecurityContextHolder.getContext();
		final Authentication authentication = sec.getAuthentication();

		if (authentication != null && authentication.getClass() == WindowsAuthenticationToken.class) {
			final WindowsAuthenticationToken token = (WindowsAuthenticationToken) authentication;
			final WindowsPrincipal principal = (WindowsPrincipal) token.getPrincipal();
			UserAuthFilter.log.debug("Context injection started.");
			final String[] parts = principal.getName().split(Pattern.quote("\\"));
			// update admin value based on DB authentication
			final boolean isAdmin = true;
			addRoleToAuthentication(token, isAdmin);
			sec.setAuthentication(token);
			UserAuthFilter.log.debug("User context injection completed for user: {}", parts[1]);
		}

		filterChain.doFilter(request, response);
	}
}
