package com.centerm.fud_demo.utils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/15 上午11:52
 */
@Slf4j
public class FileUtil {

    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer num = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(num);
    }
    public static String getFormatSize(double size){
        double kiloByte = size / 1024;
        if (kiloByte < 1){
            return size + "Byte(s)";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1){
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if (gigaByte < 1){
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1){
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public static String getFileMd5(File file){
        if (!file.exists() || !file.isFile()){
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try{
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1){
                digest.update(buffer, 0, len);
            }
            in.close();
        }catch (Exception e){
            log.info("getMd5 error: " + e.getMessage());
            return null;
        }
        BigInteger bigInteger = new BigInteger(1, digest.digest());
        return bigInteger.toString(16);
    }
    public static String getFileMd5(String path){
        BigInteger bi = null;
        try{
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1){
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        }catch (NoSuchAlgorithmException e){
            log.error("No Such AlgorithmException: " + e.getMessage());
        }catch (IOException e){
            log.error("IOException: " + e.getMessage());
        }
        return bi.toString();
    }

    public static byte[] hexToByte(String str){
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    public static void deleteLocalFile(String localUrl) {
        try{
            log.info("Start deleting local file: " + localUrl);
            File deleteFile = new File(localUrl);
            deleteFile.delete();
            log.info("Delete successfully...");
        }catch (Exception e){
            log.error("Delete Error...");
            log.error(e.getMessage());
        }
    }

    public static void deleteDirectory(String sPath){
        log.info("Start deleting directory....");
        if (!sPath.endsWith(File.separator)){
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()){
            log.info("Directory didn't exists...");
            return;
        }
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()){
                files[i].delete();
            }
        }
        dirFile.delete();
        log.info("Delete successfully...");
    }

}
