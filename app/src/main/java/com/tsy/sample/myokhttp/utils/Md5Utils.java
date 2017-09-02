package com.tsy.sample.myokhttp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiaomingMac on 17/4/22.
 */
public class Md5Utils {

    public  String toMd5(String original, String separator) {
        try {
            String result;
            byte[] bytes = original.getBytes();
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            result = toHexString(algorithm.digest(), separator);
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", 0xFF & b)).append(separator);
        }
        return hexString.toString();
    }

    /** Calculate MD5 sum of a file */
    static final public String calcMD5(File file){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");

            FileInputStream input = new FileInputStream(file);
            byte[] buf = new byte[1024];

            while (input.available() > 0) {
                int res = input.read(buf, 0, buf.length);

                md.update(buf, 0, res);
            }
            input.close();

            byte[] md5 = md.digest();

            return bytesToHexString(md5);
        }catch(Exception e){
            e.printStackTrace();
        }

        return ""+file.length();
    }

    /**
     * Convert an array of bytes to a string of hexadecimal numbers
     */
    static final private String bytesToHexString(byte[] array) {
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            int val = array[i] + 256;
            String b = "00" + Integer.toHexString(val);
            int len = b.length();
            String sub = b.substring(len - 2);

            res.append(sub);
        }

        return res.toString();
    }


    /**
     * 32位MD5加密方法
     * 16位小写加密只需getMd5Value("xxx").substring(8, 24);即可
     *
     * @param sSecret
     * @return
     */
    public static String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
