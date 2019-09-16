package com.ssc.ph.bau.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssc.ph.bau.dto.AccessTokenDTO;
import com.ssc.ph.bau.dto.GithubUser;
import com.ssc.ph.bau.provider.GithubProvider;

@Controller

public class BauAuthorizeController {
	
	@Autowired
	private GithubProvider githubprovider;
	@Value("${github.client.id}")
	private String ClientId;
	@Value("${github.client.secret}")
	private String ClientSecret;
	@Value("${github.redirect.uri}")
	private String RedirectUri;

	
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,HttpServletRequest request) {
		AccessTokenDTO accesstokendto =new AccessTokenDTO();
		accesstokendto.setCode(code);
		accesstokendto.setState(state);
		accesstokendto.setClient_id(ClientId);
		accesstokendto.setRedirect_uri(RedirectUri);
		accesstokendto.setClient_secret(ClientSecret);
		String token=githubprovider.getToken(accesstokendto);
		String user=githubprovider.getUser(token).getName();
		if (user !=null) {
			System.out.println(user);
			//login success
			request.getSession().setAttribute("user", user);
			return "redirect:/";
		} else {
			//login failed
			return "redirect:/";
		}
	}

}
