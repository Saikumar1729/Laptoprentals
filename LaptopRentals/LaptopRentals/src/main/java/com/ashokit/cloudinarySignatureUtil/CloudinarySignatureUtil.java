package com.ashokit.cloudinarySignatureUtil;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

public class CloudinarySignatureUtil {

    public static String generateSignature(Map<String, Object> paramsToSign, String apiSecret) {
        // Sort parameters alphabetically
        TreeMap<String, Object> sorted = new TreeMap<>(paramsToSign);

        StringBuilder toSign = new StringBuilder();
        for (Map.Entry<String, Object> entry : sorted.entrySet()) {
            if (entry.getValue() != null) {
                toSign.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        if (toSign.length() > 0) {
            toSign.setLength(toSign.length() - 1); // remove last '&'
        }

        return DigestUtils.sha1Hex(toSign.toString() + apiSecret);
    }

}