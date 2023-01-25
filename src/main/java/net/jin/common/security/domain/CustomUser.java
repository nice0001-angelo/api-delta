package net.jin.common.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import net.jin.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private static final long serialVersionUID = 1L;

	private Member member;

	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUser(Member member) {
		super(member.getUserId(), member.getUserPw(), member.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));

		this.member = member;
	}

	public CustomUser(Member member, Collection<? extends GrantedAuthority> authorities) {
		super(member.getUserId(), member.getUserPw(), authorities);

		this.member = member;
	}

	public long getUserNo() {
		return member.getUserNo();
	}

	public String getUserId() {
		return member.getUserId();
	}

}
