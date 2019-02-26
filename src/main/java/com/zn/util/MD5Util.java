package com.zn.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class MD5Util {
	/*
	 * UUID :32位永不相同的字符串，一般用于主键（varchar String）
	 */
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	/**
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 * MD5:特点：不可以逆，将不等长字符信息处理成等长信息。
	 * 明文-》算法A-》密文-》传输-》算法B-》明文 .对称加密，加密后能解密
	 * 明文-》算法-》密文-》传输
	 */
	public static String md5(String src) throws Exception{
		//将字符串信息采用MD5处理
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] output = md.digest(src.getBytes());
		//将MD5处理结果利用Base64转成字符串
		String s = Base64.encodeBase64String(output);
		return s;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(md5("123456"));
		System.out.println(createId());
	}
}
