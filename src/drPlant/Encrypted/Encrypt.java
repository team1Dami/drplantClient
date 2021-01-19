/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.Encrypted;

import drPlant.main.CifrarMensaje;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author saray
 */
public class Encrypt {
    public byte[] getPublicFileKey() throws IOException {

        InputStream keyfis = CifrarMensaje.class.getClassLoader()
                .getResourceAsStream("drPlant/main/key/RSA_Public.key");
        

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = keyfis.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        keyfis.close();
        return os.toByteArray();
    }
    
    
    public byte[] cifrarPass(String passwd) {
        byte[] encodedMessage = null;
        try {
            // Cargamos la clave pública

            byte fileKey[] = getPublicFileKey();

            // Obtenemos una instancia de KeyFactory, algoritmo RSA
            KeyFactory myKeyFactory = KeyFactory.getInstance("RSA");
            // Creamos un nuevo X509EncodedKeySpec del fileKey
            X509EncodedKeySpec encodedKey = new X509EncodedKeySpec(fileKey);
            // Generamos la public key con el keyFactory
            PublicKey publicKey = myKeyFactory.generatePublic(encodedKey);
            // Obtenemos una instancia del Cipher "RSA/ECB/PKCS1Padding"
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Iniciamos el cipher (ENCRYPT_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // El método doFinal nos cifra el mensaje
            encodedMessage = cipher.doFinal(passwd.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }
}
