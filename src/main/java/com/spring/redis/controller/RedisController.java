package com.spring.redis.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.redis.pojo.User;
import com.spring.redis.util.RedisUtil;

@Controller
@RequestMapping("/")
public class RedisController {
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping(value = "add", method = RequestMethod.GET)
	@ResponseBody
	public String add() {
		System.out.println("====================add====================");
		this.redisUtil.set("aaa", "aaa");
		System.out.println("====================add====================");
		return "hello world";
	}

	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public String get() {
		System.out.println("====================get====================");
		return String.valueOf(this.redisUtil.get("aaa"));
	}

	@RequestMapping(value = "addHash", method = RequestMethod.GET)
	@ResponseBody
	public String addHash() {
		System.out.println("====================addHash====================");
		User u = new User();
		u.setAge(11);
		u.setId(1L);
		u.setName("wyait");
		this.redisUtil.setHash("mmm", "bbb:ccc", u);

		HashMap<String, User> map = new HashMap<String, User>();
		map.put("user", u);
		this.redisUtil.setHash("mmm", "nnn", map);
		System.out.println("====================addHash====================");
		return "hello world";
	}

	@RequestMapping(value = "getHash", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, User> getHash() {
		System.out.println("====================getHash====================");
		User user = (User) this.redisUtil.getHash("mmm", "bbb:ccc");
		System.out.println("====================getHash====================" + user);
		return (HashMap<String, User>) this.redisUtil.getHash("mmm", "nnn");
	}

}