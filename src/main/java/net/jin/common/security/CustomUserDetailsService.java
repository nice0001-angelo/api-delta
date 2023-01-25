package net.jin.common.security;

import net.jin.common.security.domain.CustomUser;
import net.jin.domain.Member;
import net.jin.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository repository;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		log.info("userName : " + userName);

		Member member = repository.findByUserId(userName).get(0);

		log.info("member : " + member);

		return member == null ? null : new CustomUser(member);
	}

}
