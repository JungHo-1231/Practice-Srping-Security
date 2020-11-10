package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexContrller {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "인덱스 페이지입니다.";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody  String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody  String manager() {
		return "manager";
	}
	
	// 스프링 시큐리티가 해당 주소를 낚아 챈다. - SecurityConfig 파일 생성 후 작동 안함.
	@GetMapping("/loginForm")
	public  String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public   String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public  String join(User user) {
		String role = "ROLE_USER";
		user.setRole(role);
		
		// 회원가입 잘됨. 비밀번호:1234 => 시큐리티로 로그인을 할 수 없음.
		// 이유는 패스워드가 암호화가 안되었기 때문이다.
		
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepository.save(user);
		
		
		return "redirect:/loginForm";
	}	
	
	
	@Secured("ROLE_ADMIN") // 하나만 걸고 싶을 때 Secured
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}	
}
