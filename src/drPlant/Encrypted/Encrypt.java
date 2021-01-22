/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.Encrypted;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import javax.crypto.Cipher;

/**
 *
 * @author saray
 */
public class Encrypt {

    private static ResourceBundle resource;

    public byte[] getPublicFileKey() throws IOException {
        resource = ResourceBundle.getBundle("drPlant/main/key/KEY_PROPERTIES_FILE");
        String path = resource.getString("PUBLIC_KEY_PATH");
        InputStream keyfis = Encrypt.class.getClassLoader()
                .getResourceAsStream(path);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;

        while ((len = keyfis.read(buffer)) != -1) {

            os.write(buffer, 0, len);
        }
        keyfis.close();
        return os.toByteArray();
    }

    public byte[] cifrarPass(String passwd) {
        byte[] encodedMessage = null;
        try {

            byte fileKey[] = getPublicFileKey();

            KeyFactory myKeyFactory = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec encodedKey = new X509EncodedKeySpec(fileKey);

            PublicKey publicKey = myKeyFactory.generatePublic(encodedKey);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            encodedMessage = cipher.doFinal(passwd.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }
}
