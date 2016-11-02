/**
 * StringUtil.java 1.0 2014-12-23
 *
 * JYD(Bei Jing) Network Polytron Technologies Inc. All rights reserved.
 * This software is the confidential and proprietary 
 * information of JYD(Bei Jing) Network Polytron Technologies Inc. 
 * ("Confidential Information"). You shall not disclose 
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement 
 * you entered into with JYD.
 */
package cn.nick;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author Ulysses.Liu
 * @version 1.0 bate, 2014-12-23
 * @since JDK 1.5
 * 
 */
public class StringUtil {
	/**
	 * 判断是否是空字符串 null和"" 都返回 true
	 * @author Robin Chang
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
			return false;
		}
		return true;
	}

}
