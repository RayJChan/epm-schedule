package com.midea.epm.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加解密工具类
 *
 */
public class CryptographyUtil {


    /**
     * MD5加密
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str, String salt) {
        return new Md5Hash(str, salt).toString();
    }



}
