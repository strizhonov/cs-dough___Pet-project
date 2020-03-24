package by.training.security;

import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHex;

public class StringDataCoder {

    private static final Logger LOGGER = LogManager.getLogger(StringDataCoder.class);
    private static final String ENCRYPTION_TYPE = "AES";
    private final Cipher cipher;


    public StringDataCoder() throws GeneralSecurityException {
        cipher = Cipher.getInstance(ENCRYPTION_TYPE);
    }


    public String getStringSecretKey() throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_TYPE);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        char[] hex = encodeHex(secretKey.getEncoded());
        return String.valueOf(hex);
    }


    public String encrypt(String plainText, String sSecretKey) throws GeneralSecurityException {
        byte[] decoded;
        try {
            decoded = decodeHex(sSecretKey);
        } catch (DecoderException e) {
            LOGGER.error("Unable to decode string secret key.", e);
            throw new GeneralSecurityException("Unable to decode string secret key.", e);
        }

        SecretKey key = new SecretKeySpec(decoded, ENCRYPTION_TYPE);
        byte[] plainTextByte = plainText.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(encryptedByte);
    }


    public String decrypt(String encryptedText, String sSecretKey) throws GeneralSecurityException {
        byte[] decoded;
        try {
            decoded = decodeHex(sSecretKey);
        } catch (DecoderException e) {
            LOGGER.error("Unable to decode string secret key.", e);
            throw new GeneralSecurityException("Unable to decode string secret key.", e);
        }

        SecretKey key = new SecretKeySpec(decoded, ENCRYPTION_TYPE);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        return new String(decryptedByte, StandardCharsets.UTF_8);
    }

}