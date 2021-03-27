package com.practice.springbootproj.common.util;








import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * <pre>
 *   AES256 Encode, Decode
 * </pre>
 */
public class AES256Cipher {
  private static final Charset UTF8 = Charset.forName("UTF-8");
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

  /**
   *
   * @param str
   * @param key
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws InvalidAlgorithmParameterException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public static String AES_Encode(String str, String key)
    throws UnsupportedEncodingException
      , NoSuchAlgorithmException
      , NoSuchPaddingException
      , InvalidKeyException
      , InvalidAlgorithmParameterException
      , IllegalBlockSizeException
      , BadPaddingException  {
    return AES_Encode(str, key, key.substring(0,16).getBytes());
  }

  /**
   *
   * @param str
   * @param key
   * @param ivBytes
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws InvalidAlgorithmParameterException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public static String AES_Encode(String str, String key, byte[] ivBytes)
      throws UnsupportedEncodingException
      , NoSuchAlgorithmException
      , NoSuchPaddingException
      , InvalidKeyException
      , InvalidAlgorithmParameterException
      , IllegalBlockSizeException
      , BadPaddingException  {
    byte[] textBytes = str.getBytes("UTF-8");

    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    SecretKeySpec newKey = new SecretKeySpec(key.getBytes(UTF8),"AES");
    Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE,newKey,ivSpec);
    return Base64.encodeBase64String(cipher.doFinal(textBytes));
  }

  public static String AES_Decode(String str, String key)
    throws UnsupportedEncodingException
      , NoSuchAlgorithmException
      , NoSuchPaddingException
      , InvalidKeyException
      , InvalidAlgorithmParameterException
      , IllegalBlockSizeException
      , BadPaddingException{
    return AES_Decode(str, key,key.substring(0,16).getBytes());
  }

  /**
   *
   * @param str
   * @param key
   * @param ivBytes
   * @return
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws InvalidAlgorithmParameterException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   */
  public static String AES_Decode(String str, String key, byte[] ivBytes)
      throws UnsupportedEncodingException
      , NoSuchAlgorithmException
      , NoSuchPaddingException
      , InvalidKeyException
      , InvalidAlgorithmParameterException
      , IllegalBlockSizeException
      , BadPaddingException{
    byte[] textBytes = Base64.decodeBase64(str);
    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    SecretKeySpec newKey = new SecretKeySpec(key.getBytes(UTF8),"AES");
    Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE,newKey,ivSpec);
    return new String(cipher.doFinal(textBytes),"UTF-8");
  }
}
