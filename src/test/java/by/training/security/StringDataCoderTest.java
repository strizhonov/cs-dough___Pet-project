package by.training.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.GeneralSecurityException;

@RunWith(JUnit4.class)
public class StringDataCoderTest {

    private StringDataCoder coder;

    @Before
    public void init() throws GeneralSecurityException {
        coder = new StringDataCoder();
    }

    @Test
    public void differentKeys() throws GeneralSecurityException {
        String firstKey = coder.getStringSecretKey();
        String secondKey = coder.getStringSecretKey();
        Assert.assertNotEquals(firstKey, secondKey);
    }

    @Test
    public void codeDecode() throws GeneralSecurityException {
        String pass = "TEST_PASS";
        String key = coder.getStringSecretKey();
        String encrypted = coder.encrypt(pass, key);
        Assert.assertTrue(encrypted != null && encrypted.length() > 0);

        String decrypted = coder.decrypt(encrypted, key);
        Assert.assertEquals(decrypted, pass);
    }

}
