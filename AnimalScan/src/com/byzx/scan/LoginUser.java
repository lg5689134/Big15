/**
 * @filename LoginUser.java
 * @author lg
 * @date 2019年3月25日 下午1:41:10
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.scan;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/*存储绑定的用户*/
public class LoginUser {

	//存储绑定的用户
	/*多线程对Hashtable进行了put，remove等更新操作的话，
	就会抛出ConcurrentModificationException异常*/
	 //public static Map<String,String> users = new HashMap<String,String>();
	 public static Map<String,String> users =new ConcurrentHashMap<String,String>();
	 //存储token唯一标识
	 //public static Set<String> tokes = new HashSet<String>();//不能保证线程的安全
	 public static Set<String> tokens = Collections.synchronizedSet(new HashSet<String>());
		
	public static Map<String, String> getUsers() {
		return users;
	}

	public static Set<String> getTokens() {
		return tokens;
	}
	
	
}
