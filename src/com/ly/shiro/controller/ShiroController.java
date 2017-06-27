package com.ly.shiro.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.shiro.services.ShiroService;

@Controller
@RequestMapping("/shiro")
public class ShiroController {
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "value123456");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}

	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		// 获取当前的 Subject. 调用 SecurityUtils.getSubject();
		Subject currentUser = SecurityUtils.getSubject();

		// 测试当前的用户是否已经被认证. 即是否已经登录.
		// 调动 Subject 的 isAuthenticated()
		if (!currentUser.isAuthenticated()) {
			// 把用户名和密码封装为 UsernamePasswordToken 对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// rememberme
			token.setRememberMe(true);
			try {
				// 执行登录.
				currentUser.login(token);
			}
			// your application?
			// 所有认证时异常的父类.
			catch (AuthenticationException ae) {
				// unexpected condition? error?
				System.out.println("登录失败！"+ae.getMessage());
			}
		}
		return "redirect:/list.jsp";
	}

}
