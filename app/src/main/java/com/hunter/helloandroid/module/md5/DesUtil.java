package com.hunter.helloandroid.module.md5;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加解密
 */
public class DesUtil {
    private final String CIPHER_MODE_PADDING = "DES/CBC/PKCS5Padding";// DES/CBC/PKCS5Padding
    private String mDefKey = "12345678";// key必须为8位
    private byte[] mIvBuffer = {1, 2, 3, 4, 5, 6, 7, 8};
    private static DesUtil mDesUtil;

    public static DesUtil getInstance() {
        if (mDesUtil == null) {
            synchronized (DesUtil.class) {
                if (mDesUtil == null) {
                    mDesUtil = new DesUtil();
                }
            }
        }
        return mDesUtil;
    }

    private DesUtil() {
    }

    /**
     * 加密
     */
    public String encrypt(String encryptString) throws Exception {
        return encrypt(encryptString, mDefKey);
    }

    /**
     * 加密
     */
    public String encrypt(String encryptString, String encryptKey) {
        String result = null;
        try {
            if (encryptString != null && encryptString.length() > 0) {
                IvParameterSpec zeroIv = new IvParameterSpec(mIvBuffer);
                SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("ASCII"), "DES");
                Cipher cipher = Cipher.getInstance(CIPHER_MODE_PADDING);
                cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
                byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
                result = encryptedData != null ? Base64.encode(encryptedData) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密
     */
    public String decrypt(String decryptString) {
        return decrypt(decryptString, mDefKey);
    }

    /**
     * 解密
     */
    public String decrypt(String decryptString, String decryptKey) {
        String result = null;
        try {
            if (decryptString != null && decryptString.length() > 0) {
                byte[] byteMi = Base64.decode(decryptString);
                IvParameterSpec zeroIv = new IvParameterSpec(mIvBuffer);
                SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("ASCII"), "DES");
                Cipher cipher = Cipher.getInstance(CIPHER_MODE_PADDING);
                cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
                byte[] decryptedData = cipher.doFinal(byteMi);
                result = decryptedData != null ? new String(decryptedData) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
