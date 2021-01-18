package com.amazingrv.springwaffle.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import waffle.servlet.spi.BasicSecurityFilterProvider;
import waffle.servlet.spi.NegotiateSecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProviderCollection;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

/**
 * Configuration data for Waffle security and auth filters
 *
 * @author rjat3
 */
@Configuration
public class WaffleConfig {
	@Bean
	public BasicSecurityFilterProvider basicSecurityFilterProvider(final WindowsAuthProviderImpl windowsAuthProvider) {
		return new BasicSecurityFilterProvider(windowsAuthProvider);
	}

	@Bean
	public NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint(
			final SecurityFilterProviderCollection providers) {
		final var bean = new NegotiateSecurityFilterEntryPoint();
		bean.setProvider(providers);
		return bean;
	}

	@Bean
	public NegotiateSecurityFilterProvider negotiateSecurityFilterProvider(
			final WindowsAuthProviderImpl windowsAuthProvider) {
		final var bean = new NegotiateSecurityFilterProvider(windowsAuthProvider);
		bean.setProtocols(Arrays.asList("Negotiate", "NTLM"));
		return bean;
	}

	@Bean
	public NegotiateSecurityFilter waffleNegotiateSecurityFilter(final SecurityFilterProviderCollection providers) {
		final var bean = new NegotiateSecurityFilter();
		bean.setProvider(providers);
		bean.setPrincipalFormat("fqn");
		bean.setRoleFormat("both");
		bean.setAllowGuestLogin(false);
		bean.setImpersonate(false);
		return bean;
	}

	@Bean
	public SecurityFilterProviderCollection waffleSecurityFilterProviderCollection(
			final NegotiateSecurityFilterProvider negotiateProvider, final BasicSecurityFilterProvider basicProvider) {
		SecurityFilterProvider[] providers;
		providers = new SecurityFilterProvider[] { negotiateProvider, basicProvider };
		return new SecurityFilterProviderCollection(providers);
	}

	@Bean
	public WindowsAuthProviderImpl waffleWindowsAuthProvider() {
		return new WindowsAuthProviderImpl();
	}
}
