package com.imooc.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author youyusong
 * @date 2018/12/27
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        String pass = MD5Util.formPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9", "1a2b3c4d");
        System.out.println(pass);
    }

}
