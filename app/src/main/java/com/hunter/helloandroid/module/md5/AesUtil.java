package com.hunter.helloandroid.module.md5;

import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密
 */
public class AesUtil {
    private final String CIPHER_MODE_PADDING = "AES/CBC/PKCS5Padding";// AES/CBC/PKCS7Padding

    private String mDefKey = "GZg8Oa4AsZA=1234";// key必须为16位
    private String mDefMigration = "1234567890123456";// 密钥默认偏移，可更改
    private IvParameterSpec mIvParameterSpec;
    private static AesUtil mAesUtil;

    public static AesUtil getInstance() {
        if (mAesUtil == null) {
            synchronized (AesUtil.class) {
                if (mAesUtil == null) {
                    mAesUtil = new AesUtil();
                }
            }
        }
        return mAesUtil;
    }

    private AesUtil() {
        mIvParameterSpec = new IvParameterSpec(mDefMigration.getBytes());
    }

    /**
     * 加密
     */
    public String encrypt(String plaintext) {
        return encrypt(plaintext, mDefKey);
    }

    /**
     * 加密
     */
    public String encrypt(String plaintext, String key) {
        String result = null;
        try {
            if (plaintext != null && plaintext.length() > 0) {
                byte[] bytes = plaintext.getBytes("UTF-8");
                SecretKeySpec skforAES = new SecretKeySpec(key.getBytes("ASCII"), "AES");
                byte[] ciphertext = encrypt(CIPHER_MODE_PADDING, skforAES, mIvParameterSpec, bytes);
                result = Base64Encoder.encode(ciphertext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密
     */
    public String decrypt(String ciphertext_base64) {
        return decrypt(ciphertext_base64, mDefKey);
    }

    /**
     * 解密
     */
    public String decrypt(String plaintext, String key) {
        String result = null;
        try {
            if (plaintext != null && plaintext.length() > 0) {
                SecretKeySpec skforAES = new SecretKeySpec(key.getBytes("ASCII"), "AES");
                byte[] s = Base64Decoder.decodeToBytes(plaintext);
                byte[] decrypt = decrypt(CIPHER_MODE_PADDING, skforAES, mIvParameterSpec, s);
                result = decrypt != null ? new String(decrypt) : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密
     */
    private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.ENCRYPT_MODE, sk, IV);
            return c.doFinal(msg);
        } catch (NoSuchAlgorithmException nsae) {
            Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException nspe) {
            Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);
        } catch (InvalidKeyException e) {
            Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException e) {
            Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException e) {
            Log.e("AESdemo", "bad padding exception");
        }
        return null;
    }

    /**
     * 解密
     */
    private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.DECRYPT_MODE, sk, IV);
            return c.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException nsae) {
            Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        } catch (NoSuchPaddingException nspe) {
            Log.e("AESdemo", "no cipher getinstance support for padding " + cmp);
        } catch (InvalidKeyException e) {
            Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException e) {
            Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException e) {
            Log.e("AESdemo", "bad padding exception");
            e.printStackTrace();
        }
        return null;
    }

}