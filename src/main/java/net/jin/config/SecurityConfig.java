//package net.jin.config;
//
//import java.util.Arrays;
//
//import net.jin.common.security.*;
//import net.jin.common.security.jwt.filter.*;
//import net.jin.common.security.jwt.provider.*;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.*;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.*;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//public class SecurityConfig {
//
//	private final JwtTokenProvider jwtTokenProvider;
//
//	private final AuthenticationManager authenticationManager;
//
//	protected void configure(HttpSecurity http) throws Exception {
//		log.info("security config ...");
//
//		http.formLogin().disable().httpBasic().disable();
//
//		http.cors();
//
//		http.csrf().disable();
//
//		http.addFilterAt(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider),
//				UsernamePasswordAuthenticationFilter.class)
//				.addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//				.antMatchers("/").permitAll().antMatchers("/codes/**").access("permitAll").antMatchers("/users/**")
//				.access("permitAll").antMatchers("/codegroups/**").access("hasRole('ADMIN')")
//				.antMatchers("/codedetails/**").access("hasRole('ADMIN')").antMatchers("/boards/**")
//				.access("request.method == 'GET' ? permitAll : hasAnyRole('MEMBER', 'ADMIN')")
//				.antMatchers("/notices/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
//				.antMatchers("/items/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
//				.antMatchers("/coins/**").access("hasRole('MEMBER')").antMatchers("/useritems/**")
//				.access("hasAnyRole('MEMBER', 'ADMIN')").antMatchers("/pds/**")
//				.access("request.method == 'GET' ? permitAll : hasRole('ADMIN')").anyRequest().authenticated();
//
//		http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
//				.accessDeniedHandler(accessDeniedHandler());
//	}
//
////	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public UserDetailsService customUserDetailsService() {
//		return new CustomUserDetailsService();
//	}
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("OPTIONS");
//		config.addAllowedMethod("HEAD");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("DELETE");
//		config.addAllowedMethod("PATCH");
//		config.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));
//
//		source.registerCorsConfiguration("/**", config);
//
//		return source;
//	}
//
//	@Bean
//	public AccessDeniedHandler accessDeniedHandler() {
//		return new CustomAccessDeniedHandler();
//	}
//
//}

/**
 * 
 */
package net.jin.config;

import java.util.*;

import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.*;

import lombok.*;
import lombok.extern.slf4j.*;
import net.jin.common.security.*;
import net.jin.common.security.jwt.filter.*;
import net.jin.common.security.jwt.provider.*;

/**
 * @author njh
 *
 */
@Slf4j
//???????????? ????????? ????????????
@RequiredArgsConstructor
@EnableWebSecurity
//???????????? ??????????????? ???????????? ?????? ??????
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// JWT ????????? ???????????? JwtTokenProvider ?????? ??????
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("Security Configuring....");

		// ????????????????????? ??????????????? ????????????
//		httpSecurity.formLogin().disable().httpBasic().disable();

		// CORS ????????? ??????
		httpSecurity.cors();

		// csrf() Disable ?????? ????????? CUD??? ?????? ?????? CSRF ?????? ?????? ?????? ????????????
		httpSecurity.csrf().disable();

		// JWT ?????? ?????? ?????? ??????????????? ??????
		httpSecurity
				.addFilterAt(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// ????????? ?????? ??????
		httpSecurity.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll().antMatchers("/").permitAll().antMatchers("/index").permitAll().antMatchers("/about")
				.permitAll().antMatchers("/computer").permitAll().antMatchers("/laptop").permitAll()
				.antMatchers("/product").permitAll().antMatchers("/contact").permitAll().antMatchers("/authenticate/**")
				.access("permitAll").antMatchers("/codes/**").access("permitAll").antMatchers("/users/**")
				.access("permitAll").antMatchers("/codegroups/**").access("hasRole('ADMIN')")
				.antMatchers("/codedetails/**").access("hasRole('ADMIN')")
				// ??????????????? ????????? ?????? ?????? ??????
				.antMatchers("/boards/**").access("request.method == 'GET' ? permitAll : hasAnyRole('ADMIN', 'MEMBER')")
				// ???????????? ????????? ?????? ?????? ??????
				.antMatchers("/notices/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
				// ???????????? ????????? ?????? ?????? ??????
				.antMatchers("/items/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
				// ???????????? ??? ?????? ?????? ??????
				.antMatchers("/coins/**").access("hasRole('MEMBER')")
				// ???????????? ??? ?????? ?????? ??????
				.antMatchers("/useritems/**").access("hasAnyRole('ADMIN','MEMBER')")
				// ???????????? ??? ?????? ?????? ??????
				.antMatchers("/pds/**").access("request.method == 'GET' ? permeitALL : hasRole('ADMIN')")
				// Swagger ?????? ?????? ??????(permitAll())
				.antMatchers("/v2/api-docs", "/v2/api-docs/**", "/swagger-resources/**", "/swagger-ui.html/**",
						"/swagger*/**")
				.permitAll().anyRequest().authenticated();

	}

	// CustomUserDetailsService ?????? ?????? ??????????????? ???????????? ???????????? ?????????????????? ????????????
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
	}

	// ???????????? ?????? ????????? ??????
	@Bean
	public PasswordEncoder passwordEncoder() {
		// - BCryptPasswordEncoder??? BCrypt ?????? ??????(BCrypt hashing function)??? ????????????
		// ??????????????? ?????????????????? ???????????? ???????????? ?????? ????????? ??????????????? ???????????? ???????????? ?????? ??????????????? ?????? ????????? ??????????????? ????????????
		// ???????????????.
		return new BCryptPasswordEncoder();
	}

	// ????????? ??????????????? UserDetailsService??? ????????? ???????????? ????????? ????????????
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}

	// Cors ???????????????
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		config.setExposedHeaders(Arrays.asList("Authorization", "Content-Disposition"));

		source.registerCorsConfiguration("/**", config);
		System.out.println();

		return source;
	}
}
