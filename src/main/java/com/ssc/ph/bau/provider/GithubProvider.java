package com.ssc.ph.bau.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ssc.ph.bau.dto.AccessTokenDTO;
import com.ssc.ph.bau.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @author asus
 *
 */
@Component
public class GithubProvider {
	public String getToken(AccessTokenDTO accesstokendto) {
		MediaType mediatype = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediatype, JSON.toJSONString(accesstokendto));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
		try (Response response = client.newCall(request).execute()) {
			String vauleString = response.body().string();
			System.out.println(vauleString);
			String accessToken=vauleString.split("&")[0].split("=")[1];
			return accessToken;
		} catch (IOException e) {
		}
		return null;
	}

	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();
		try {
			Response response = client.newCall(request).execute();
			String valueString = response.body().string();
			GithubUser githubuser = JSON.parseObject(valueString, GithubUser.class);
			return githubuser;
		} catch (IOException e) {
		}
		return null;

	}

}
