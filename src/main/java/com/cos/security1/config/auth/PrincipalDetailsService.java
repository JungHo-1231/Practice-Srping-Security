package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingUrl("login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// 로그인 view page에서 name을 username으로 해야한다.
	// 만약 name을 username2로 한다면 loadUserByUsername 함수에서 username을 찾을 수 없기 때문에
	// 값이 매칭이 안된다.
	// 정 바꾸고 싶다면 security config에서 usernameParameter에서 바꿔줘야 한다.(근데 바꾸지 말고 그냥 쓰자)
	
	
	
	// 시큐리티 session(내부 Authentication(내부UserDetails)))들어감  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
