package com.practice.springbootproj.common.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AES256Helper {


  /**
   * AES256 Encode
   * @param plainStr
   * @param key
   * @return
   */
  public String encode(String plainStr, String key){
    String result = null;
    try {
      result = AES256Cipher.AES_Encode(plainStr,key);
    }catch (Exception e){
      log.error(e.getMessage(),e);
    }
    return result;
  }

  /**
   * AES256 Decode
   * @param encodeStr
   * @param key
   * @param tClass
   * @param <T>
   * @return
   */
  public <T> T decode(String encodeStr, String key, Class<T> tClass){
    ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
    T result = null;
    try {
      result = objectMapper.readValue(AES256Cipher.AES_Decode(encodeStr,key),tClass);
    }catch (Exception e){
      log.error(e.getMessage(),e);
    }
    return result;
  }
}
