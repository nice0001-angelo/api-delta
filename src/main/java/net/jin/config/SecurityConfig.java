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
//인자있는 생성자 자동생성
@RequiredArgsConstructor
@EnableWebSecurity
//시큐리티 애노테이션 활성활르 위한 옵션
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// JWT 토큰을 제공하는 JwtTokenProvider 필드 선언
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("Security Configuring....");

		// 폼로그인기능과 베이직인증 비활성화
//		httpSecurity.formLogin().disable().httpBasic().disable();

		// CORS 사용자 설정
		httpSecurity.cors();

		// csrf() Disable 하지 않으면 CUD를 할수 없다 CSRF 방지 지원 기능 비활성화
		httpSecurity.csrf().disable();

		// JWT 관련 필터 보안 컨텍스트에 추가
		httpSecurity
				.addFilterAt(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// 웹경로 보안 설정
		httpSecurity.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll().antMatchers("/").permitAll().antMatchers("/authenticate/**").access("permitAll")
				.antMatchers("/codes/**").access("permitAll").antMatchers("/users/**").access("permitAll")
				.antMatchers("/codegroups/**").access("hasRole('ADMIN')").antMatchers("/codedetails/**")
				.access("hasRole('ADMIN')")
				// 회원게시판 웹경로 보안 권한 지정
				.antMatchers("/boards/**").access("request.method == 'GET' ? permitAll : hasAnyRole('ADMIN', 'MEMBER')")
				// 공지사항 웹경로 보안 권한 지정
				.antMatchers("/notices/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
				// 상품관리 웹경로 보안 권한 지정
				.antMatchers("/items/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
				// 코인충전 웹 경로 보안 지정
				.antMatchers("/coins/**").access("hasRole('MEMBER')")
				// 구매상품 웹 보안 경로 지정
				.antMatchers("/useritems/**").access("hasAnyRole('ADMIN','MEMBER')")
				// 공개자료 웹 경로 보안 지정
				.antMatchers("/pds/**").access("request.method == 'GET' ? permeitALL : hasRole('ADMIN')")
				// Swagger 경로 보안 지정(permitAll())
				.antMatchers("/v2/api-docs", "/v2/api-docs/**", "/swagger-resources/**", "/swagger-ui.html/**",
						"/swagger*/**")
				.permitAll().anyRequest().authenticated();

	}

	// CustomUserDetailsService 빈을 인증 제공자에게 지정하고 비밀번호 암호처리기를 등록한다
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
	}

	// 비밀번호 암호 처리기 생성
	@Bean
	public PasswordEncoder passwordEncoder() {
		// - BCryptPasswordEncoder는 BCrypt 해싱 함수(BCrypt hashing function)를 사용해서
		// 비밀번호를 인코딩해주는 메서드와 사용자의 의해 제출된 비밀번호와 저장소에 저장되어 있는 비밀번호의 일치 여부를 확인해주는 메서드를
		// 제공합니다.
		return new BCryptPasswordEncoder();
	}

	// 스프링 시큐리티의 UserDetailsService를 구현한 클래스를 빈으로 등록한다
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}

	// Cors 사용자설정
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
