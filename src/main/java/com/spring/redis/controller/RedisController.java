package com.spring.redis.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping(value = "add", method = RequestMethod.GET)
	@ResponseBody
	public String add() {
		logger.debug("====================add====================");
		this.redisUtil.set("aaa", "aaa");
		logger.debug("====================add====================");
		return "hello world";
	}

	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public String get() {
		logger.debug("====================get====================");
		return String.valueOf(this.redisUtil.get("aaa"));
	}

	@RequestMapping(value = "addHash", method = RequestMethod.GET)
	@ResponseBody
	public String addHash() {
		logger.debug("====================addHash====================");
		User u = new User();
		u.setAge(11);
		u.setId(1L);
		u.setName("wyait");
		this.redisUtil.setHash("mmm", "bbb:ccc", u);

		HashMap<String, User> map = new HashMap<String, User>();
		map.put("user", u);
		this.redisUtil.setHash("mmm", "nnn", map);
		logger.debug("====================addHash====================");
		return "hello world";
	}

	@RequestMapping(value = "getHash", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, User> getHash() {
		logger.debug("====================getHash====================");
		User user = (User) this.redisUtil.getHash("mmm", "bbb:ccc");
		logger.debug("====================getHash====================" + user);
		return (HashMap<String, User>) this.redisUtil.getHash("mmm", "nnn");
	}

}