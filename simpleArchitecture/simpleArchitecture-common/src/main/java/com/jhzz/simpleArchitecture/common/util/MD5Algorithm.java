package com.jhzz.simpleArchitecture.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Algorithm {

    private static Logger       logger        = LoggerFactory.getLogger(MD5Algorithm.class);

    private static final String MD5_ALGORITHM = "MD5";
    MessageDigest               algorithm;
    String                      skey;                                                       // parter对应的密钥 ,十六进制

    public MD5Algorithm(){

    }

    public MD5Algorithm(String keyValue){
        this.skey = keyValue;
        try {
            algorithm = MessageDigest.getInstance(MD5_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5Algorithm初始化失败！");
        }
    }

    public String digest(String plainData) throws Exception {
        algorithm.reset();
        byte[] plainBytes = convertText2Bytes(plainData, false);
        byte[] skeyHexBytes = Hex.decodeHex(skey.toCharArray());
        algorithm.update(plainBytes);
        algorithm.update(skeyHexBytes);

        return convertBytes2Text(algorithm.digest(), true);
    }

    public static byte[] convertText2Bytes(String text, boolean isSecretData) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF-8");

        if (isSecretData) {
            bytes = Base64.decodeBase64(bytes);
        }

        return bytes;
    }

    public static String convertBytes2Text(byte[] bytes, boolean isSecretData) throws UnsupportedEncodingException {
        if (isSecretData) {
            bytes = Base64.encodeBase64(bytes);
        }

        return new String(bytes, "UTF-8");
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    /**
     * 获取signature
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public static String signatureGet(HashMap<String, Object> params, String aodun) throws Exception {

        TreeMap<String, String> paramsTmp = new TreeMap<String, String>();
        // 请求的唯一标识
        paramsTmp.put("requestId", params.get("requestId").toString());
        // 业务线标识
        paramsTmp.put("bizLine", params.get("bizLine").toString());
        // zoneName
        paramsTmp.put("no", params.get("no").toString());
        // 查询的树结构深度
        paramsTmp.put("depth", params.get("depth").toString());
        // 相关业务方标识
        paramsTmp.put("parter", params.get("parter").toString());
        // 调用方设置的请求过期时间
        paramsTmp.put("gmtExpire", params.get("gmtExpire").toString());

        // 将排序好的key：value 顺序放进buffer中
        StringBuilder buf = new StringBuilder(64);
        for (Map.Entry<String, String> entry : paramsTmp.entrySet()) {
            buf.append(entry.getKey()).append(entry.getValue());
        }

        // 使用parter对应的密钥 ， 用md5加密参数对
        MD5Algorithm algorithm = new MD5Algorithm(aodun);// 密钥
        String data = buf.toString();
        String digest = algorithm.digest(data);

        return digest;
    }
}
