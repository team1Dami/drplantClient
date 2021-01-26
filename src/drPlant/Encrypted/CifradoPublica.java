/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drPlant.Encrypted;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author 2dam
 */
public class CifradoPublica {
    
    /**
     * Cifra un texto con RSA, modo ECB y padding PKCS1Padding (asim�trica) y lo
     * retorna
     *
     * @param mensaje El mensaje a cifrar
     * @return El mensaje cifrado
     */
    public byte[] cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        try {
            // Cargamos la clave pública
            byte fileKey[] = fileReader(".\\src\\drPlant\\Encrypted\\RSA_Public.key");
            System.out.println("Tamaño -> " + fileKey.length + " bytes");

            // Obtenemos una instancia de KeyFactory, algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Creamos un nuevo X509EncodedKeySpec del fileKey
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(fileKey);
            // Generamos la public key con el keyFactory
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // Obtenemos una instancia del Cipher "RSA/ECB/PKCS1Padding"
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Iniciamos el cipher (ENCRYPT_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // El método doFinal nos cifra el mensaje
            encodedMessage = cipher.doFinal(mensaje.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }

    /**
     * Descifra un texto con RSA, modo ECB y padding PKCS1Padding (asim�trica) y
     * lo retorna
     *
     * @param mensaje El mensaje a descifrar
     * @return El mensaje descifrado
     */
    private  byte[] descifrarTexto(byte[] mensaje) {
        byte[] decodedMessage = null;
        try {
            // Cargamos la clave privada
           byte fileKey[] = fileReader("\\drPlant.Encrypted\\Privada.txt");
           System.out.println("Tamaño -> " + fileKey.length + " bytes");

            // Obtenemos una instancia de KeyFactory, algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // Creamos un nuevo PKCS8EncodedKeySpec del fileKey
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            // Generamos la public key con el keyFactory
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            // Obtenemos una instancia del Cipher "RSA/ECB/PKCS1Padding"
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Iniciamos el cipher (DECRYPT_MODE)
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // El método doFinal nos descifra el mensaje
            decodedMessage = cipher.doFinal(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedMessage;
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
