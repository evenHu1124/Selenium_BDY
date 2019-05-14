package com.falcon.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * proerties文件工具类
 * @author 
 *
 */
public class PropertiesUtil {

	/**
	 * 根据key获取value
	 * @param key 属性值
	 * @return
	 */
	public static String getValue(String key){
		Properties prop=new Properties();
		InputStream in=new PropertiesUtil().getClass().getResourceAsStream("/connection.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String)prop.get(key);
	}
}
