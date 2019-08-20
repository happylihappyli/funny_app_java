/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funnyai.tools;

/**
 *
 * @author happyli
 */
import com.funnyai.io.Old.S_File_Text;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import sun.misc.BASE64Decoder;


public class M_RSA {

    private static final String DEFAULT_PUBLIC_KEY
            = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChDzcjw/rWgFwnxunbKp7/4e8w" + "\r"
            + "/UmXx2jk6qEEn69t6N2R1i/LmcyDT1xr/T2AHGOiXNQ5V8W4iCaaeNawi7aJaRht" + "\r"
            + "Vx1uOH/2U378fscEESEG8XDqll0GCfB1/TjKI2aitVSzXOtRs8kYgGU78f7VmDNg" + "\r"
            + "XIlk3gdhnzh+uoEQywIDAQAB" + "\r";

    private static final String DEFAULT_PRIVATE_KEY
            = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKEPNyPD+taAXCfG" + "\r"
            + "6dsqnv/h7zD9SZfHaOTqoQSfr23o3ZHWL8uZzINPXGv9PYAcY6Jc1DlXxbiIJpp4" + "\r"
            + "1rCLtolpGG1XHW44f/ZTfvx+xwQRIQbxcOqWXQYJ8HX9OMojZqK1VLNc61GzyRiA" + "\r"
            + "ZTvx/tWYM2BciWTeB2GfOH66gRDLAgMBAAECgYBp4qTvoJKynuT3SbDJY/XwaEtm" + "\r"
            + "u768SF9P0GlXrtwYuDWjAVue0VhBI9WxMWZTaVafkcP8hxX4QZqPh84td0zjcq3j" + "\r"
            + "DLOegAFJkIorGzq5FyK7ydBoU1TLjFV459c8dTZMTu+LgsOTD11/V/Jr4NJxIudo" + "\r"
            + "MBQ3c4cHmOoYv4uzkQJBANR+7Fc3e6oZgqTOesqPSPqljbsdF9E4x4eDFuOecCkJ" + "\r"
            + "DvVLOOoAzvtHfAiUp+H3fk4hXRpALiNBEHiIdhIuX2UCQQDCCHiPHFd4gC58yyCM" + "\r"
            + "6Leqkmoa+6YpfRb3oxykLBXcWx7DtbX+ayKy5OQmnkEG+MW8XB8wAdiUl0/tb6cQ" + "\r"
            + "FaRvAkBhvP94Hk0DMDinFVHlWYJ3xy4pongSA8vCyMj+aSGtvjzjFnZXK4gIjBjA" + "\r"
            + "2Z9ekDfIOBBawqp2DLdGuX2VXz8BAkByMuIh+KBSv76cnEDwLhfLQJlKgEnvqTvX" + "\r"
            + "TB0TUw8avlaBAXW34/5sI+NUB1hmbgyTK/T/IFcEPXpBWLGO+e3pAkAGWLpnH0Zh" + "\r"
            + "Fae7oAqkMAd3xCNY6ec180tAe57hZ6kS+SYLKwb4gGzYaCxc22vMtYksXHtUeamo" + "\r"
            + "1NMLzI2ZfUoX" + "\r";

    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;
    private PrivateKey privateKey2;

    /**
     * 公钥
     */
    private RSAPublicKey publicKey;

    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 获取私钥
     *
     * @return 当前的私钥对象
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 随机生成密钥对
     */
    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    //sb.append('\r');
                }
            }
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            //BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = Base64.getDecoder().decode(publicKeyStr);//base64Decoder.decodeBuffer
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
//        } catch (IOException e) {
//            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param keyFileName 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public void loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    //sb.append('\n');
                }
            }
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    
    
    /**
     * Convert PKCS#1 encoded private key into RSAPrivateCrtKeySpec.
     * 
     * <p/>The ASN.1 syntax for the private key with CRT is
     * 
     * <pre>
     * -- 
     * -- Representation of RSA private key with information for the CRT algorithm.
     * --
   * RSAPrivateKey ::= SEQUENCE {
     *   version           Version, 
     *   modulus           INTEGER,  -- n
     *   publicExponent    INTEGER,  -- e
     *   privateExponent   INTEGER,  -- d
     *   prime1            INTEGER,  -- p
     *   prime2            INTEGER,  -- q
     *   exponent1         INTEGER,  -- d mod (p-1)
     *   exponent2         INTEGER,  -- d mod (q-1) 
     *   coefficient       INTEGER,  -- (inverse of q) mod p
     *   otherPrimeInfos   OtherPrimeInfos OPTIONAL 
     * }
     * </pre>
     * 
     * @param keyBytes PKCS#1 encoded key
     * @return KeySpec
     * @throws IOException
     */

    private RSAPrivateCrtKeySpec getRSAKeySpec(byte[] keyBytes) throws IOException  {

      DerParser parser = new DerParser(keyBytes);

      Asn1Object sequence = parser.read();
        if (sequence.getType() != DerParser.SEQUENCE)
          throw new IOException("Invalid DER: not a sequence"); //$NON-NLS-1$

        // Parse inside the sequence
        parser = sequence.getParser();

        parser.read(); // Skip version
        BigInteger modulus = parser.read().getInteger();
        BigInteger publicExp = parser.read().getInteger();
        BigInteger privateExp = parser.read().getInteger();
        BigInteger prime1 = parser.read().getInteger();
        BigInteger prime2 = parser.read().getInteger();
        BigInteger exp1 = parser.read().getInteger();
        BigInteger exp2 = parser.read().getInteger();
        BigInteger crtCoef = parser.read().getInteger();

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
            modulus, publicExp, privateExp, prime1, prime2,
            exp1, exp2, crtCoef);

        return keySpec;
    } 
    
    
    public void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            //BASE64Decoder base64Decoder = new BASE64Decoder();
            //System.out.println(privateKeyStr);
            byte[] buffer =Base64.getDecoder().decode(privateKeyStr);// base64Decoder.decodeBuffer(privateKeyStr);
            
            //PKCS8 
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            
            //PKCS1 可以用csharp程序转位 PKCS8，下面的有些问题
            //KeySpec keySpec = getRSAKeySpec(buffer);
            
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            
                        //////////////////////////////////////////////////
            //RSAPrivateKeySpec rsaPrivKey = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            this.privateKey2 = fact.generatePrivate(keySpec);//rsaPrivKey);
            
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加密过程
     *
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }

    public String decrypt_by_private(String privateKeyFile,String encodedString){
        M_RSA rsaEncrypt = new M_RSA();
        try {
            //加载私钥
            rsaEncrypt.loadPrivateKey(new FileInputStream(new File(privateKeyFile)));
            
            System.out.println("加载私钥成功");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, rsaEncrypt.privateKey2);

            byte[] cipherData  = Base64.getDecoder().decode(encodedString);
            
            byte[] plainText = cipher.doFinal(cipherData);

            String strLine=new String(plainText,Charset.forName("UTF-16LE"));//, Charset.forName("unicode"));
            //System.out.println(strLine);
            return strLine;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("加载私钥失败");
        }
        return "";
    }
    public static void main(String[] args) {
        M_RSA rsaEncrypt = new M_RSA();
        //rsaEncrypt.genKeyPair();

        //加载公钥
        try {
            //C_RSA.DEFAULT_PUBLIC_KEY
            String strFile="D:/Net/Web/public/id_rsa_happyli.pem.pub";
            rsaEncrypt.loadPublicKey(new FileInputStream(new File(strFile)));//strFile);
            System.out.println("加载公钥成功");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("加载公钥失败");
        }

        //加载私钥
        try {
            String privateKeyFile="D:\\Net\\Web\\id_rsa.pkcs8";
            //rsaEncrypt.loadPrivateKey(M_RSA.DEFAULT_PRIVATE_KEY);
            rsaEncrypt.loadPrivateKey(new FileInputStream(new File(privateKeyFile)));
            
            
            System.out.println("加载私钥成功");
            
            String strFile="D:\\Net\\Web\\password_upload_1.txt";
            //strFile="D:\\Net\\Web\\password_upload_1.java.txt";
            
            ///////////////////////////////////////
            String encodedString=S_File_Text.Read(strFile, "utf-8", 1);
            //byte[] decoded = Base64.getDecoder().decode(encodedString);//.getBytes("UTF-8");
            //byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), decoded);
            //byte[] plainText = rsaEncrypt.decrypt((RSAPrivateKey) privateKey, decoded);
            //////////////////////////////////////////////////
//            RSAPrivateKeySpec rsaPrivKey = new RSAPrivateKeySpec(modulus, exponent);
//            KeyFactory fact = KeyFactory.getInstance("RSA");
//            PrivateKey privKey = fact.generatePrivate(rsaPrivKey);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, rsaEncrypt.privateKey2);

            byte[] cipherData  = Base64.getDecoder().decode(encodedString);
            
            byte[] plainText = cipher.doFinal(cipherData);

            String strLine=new String(plainText,"UTF-8");
            System.out.println(strLine);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("加载私钥失败");
        }

        //测试字符串
        String encryptStr = "Test String chaijunkun";

        try {
            //加密
            byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());
            String strResult=Base64.getEncoder().encodeToString(cipher);
            System.out.println(strResult);
            //解密
            byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), cipher);
            System.out.println("密文长度:" + cipher.length);
            System.out.println(M_RSA.byteArrayToString(cipher));
            System.out.println("明文长度:" + plainText.length);
            System.out.println(M_RSA.byteArrayToString(plainText));
            System.out.println(new String(plainText));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

/**
 * A bare-minimum ASN.1 DER decoder, just having enough functions to 
 * decode PKCS#1 private keys. Especially, it doesn't handle explicitly
 * tagged types with an outer tag.
 * 
 * <p/>This parser can only handle one layer. To parse nested constructs,
 * get a new parser for each layer using <code>Asn1Object.getParser()</code>.
 * 
 * <p/>There are many DER decoders in JRE but using them will tie this
 * program to a specific JCE/JVM.
 * 
 * @author zhang
 *
 */
class DerParser {

  // Classes
  public final static int UNIVERSAL = 0x00;
  public final static int APPLICATION = 0x40;
  public final static int CONTEXT = 0x80;
  public final static int PRIVATE = 0xC0;

  // Constructed Flag
  public final static int CONSTRUCTED = 0x20;

  // Tag and data types
  public final static int ANY = 0x00;
  public final static int BOOLEAN = 0x01;
  public final static int INTEGER = 0x02;
  public final static int BIT_STRING = 0x03;
  public final static int OCTET_STRING = 0x04;
  public final static int NULL = 0x05;
  public final static int OBJECT_IDENTIFIER = 0x06;
  public final static int REAL = 0x09;
  public final static int ENUMERATED = 0x0a;
  public final static int RELATIVE_OID = 0x0d;

  public final static int SEQUENCE = 0x10;
  public final static int SET = 0x11;

  public final static int NUMERIC_STRING = 0x12;
  public final static int PRINTABLE_STRING = 0x13;
  public final static int T61_STRING = 0x14;
  public final static int VIDEOTEX_STRING = 0x15;
  public final static int IA5_STRING = 0x16;
  public final static int GRAPHIC_STRING = 0x19;
  public final static int ISO646_STRING = 0x1A;
  public final static int GENERAL_STRING = 0x1B;

  public final static int UTF8_STRING = 0x0C;
  public final static int UNIVERSAL_STRING = 0x1C;
  public final static int BMP_STRING = 0x1E;

  public final static int UTC_TIME = 0x17;
  public final static int GENERALIZED_TIME = 0x18;

  protected InputStream in;

  /**
   * Create a new DER decoder from an input stream.
   * 
   * @param in
   *            The DER encoded stream
   */
  public DerParser(InputStream in) throws IOException {
    this.in = in;
  }

  /**
   * Create a new DER decoder from a byte array.
   * 
   * @param The
   *            encoded bytes
   * @throws IOException 
   */
  public DerParser(byte[] bytes) throws IOException {
    this(new ByteArrayInputStream(bytes));
  }

  /**
   * Read next object. If it's constructed, the value holds
   * encoded content and it should be parsed by a new
   * parser from <code>Asn1Object.getParser</code>.
   * 
   * @return A object
   * @throws IOException
   */
  public Asn1Object read() throws IOException {
    int tag = in.read();

    if (tag == -1)
      throw new IOException("Invalid DER: stream too short, missing tag"); //$NON-NLS-1$

    int length = getLength();

    byte[] value = new byte[length];
    int n = in.read(value);
    if (n < length)
      throw new IOException("Invalid DER: stream too short, missing value"); //$NON-NLS-1$

    Asn1Object o = new Asn1Object(tag, length, value);

    return o;
  }

  /**
   * Decode the length of the field. Can only support length
   * encoding up to 4 octets.
   * 
   * <p/>In BER/DER encoding, length can be encoded in 2 forms,
   * <ul>
   * <li>Short form. One octet. Bit 8 has value "0" and bits 7-1
   * give the length.
     * <li>Long form. Two to 127 octets (only 4 is supported here). 
     * Bit 8 of first octet has value "1" and bits 7-1 give the 
     * number of additional length octets. Second and following 
     * octets give the length, base 256, most significant digit first.
   * </ul>
   * @return The length as integer
   * @throws IOException
   */
  private int getLength() throws IOException {

    int i = in.read();
    if (i == -1)
      throw new IOException("Invalid DER: length missing"); //$NON-NLS-1$

    // A single byte short length
    if ((i & ~0x7F) == 0)
      return i;

    int num = i & 0x7F;

    // We can't handle length longer than 4 bytes
    if ( i >= 0xFF || num > 4) 
      throw new IOException("Invalid DER: length field too big (" //$NON-NLS-1$
          + i + ")"); //$NON-NLS-1$

    byte[] bytes = new byte[num];     
    int n = in.read(bytes);
    if (n < num)
      throw new IOException("Invalid DER: length too short"); //$NON-NLS-1$

    return new BigInteger(1, bytes).intValue();
  }

}


/**
 * An ASN.1 TLV. The object is not parsed. It can
 * only handle integers and strings.
 * 
 * @author zhang
 *
 */
class Asn1Object {

  protected final int type;
  protected final int length;
  protected final byte[] value;
  protected final int tag;

  /**
   * Construct a ASN.1 TLV. The TLV could be either a
   * constructed or primitive entity.
   * 
   * <p/>The first byte in DER encoding is made of following fields,
   * <pre>
   *-------------------------------------------------
     *|Bit 8|Bit 7|Bit 6|Bit 5|Bit 4|Bit 3|Bit 2|Bit 1|
     *-------------------------------------------------
     *|  Class    | CF  |     +      Type             |
     *-------------------------------------------------
   * </pre>
   * <ul>
   * <li>Class: Universal, Application, Context or Private
   * <li>CF: Constructed flag. If 1, the field is constructed.
   * <li>Type: This is actually called tag in ASN.1. It
   * indicates data type (Integer, String) or a construct
   * (sequence, choice, set).
   * </ul>
   * 
   * @param tag Tag or Identifier
   * @param length Length of the field
   * @param value Encoded octet string for the field.
   */
  public Asn1Object(int tag, int length, byte[] value) {
    this.tag = tag;
    this.type = tag & 0x1F;
    this.length = length;
    this.value = value;
  }

  public int getType() {
    return type;
  }

  public int getLength() {
    return length;
  }

  public byte[] getValue() {
    return value;
  }

  public boolean isConstructed() {
    return  (tag & DerParser.CONSTRUCTED) == DerParser.CONSTRUCTED;
  }

  /**
   * For constructed field, return a parser for its content.
   * 
   * @return A parser for the construct.
   * @throws IOException
   */
  public DerParser getParser() throws IOException {
    if (!isConstructed()) 
      throw new IOException("Invalid DER: can't parse primitive entity"); //$NON-NLS-1$

    return new DerParser(value);
  }

  /**
   * Get the value as integer
   * 
   * @return BigInteger
   * @throws IOException
   */
  public BigInteger getInteger() throws IOException {
      if (type != DerParser.INTEGER)
        throw new IOException("Invalid DER: object is not integer"); //$NON-NLS-1$

      return new BigInteger(value);
  }

  /**
   * Get value as string. Most strings are treated
   * as Latin-1.
   * 
   * @return Java string
   * @throws IOException
   */
  public String getString() throws IOException {

    String encoding;

    switch (type) {

    // Not all are Latin-1 but it's the closest thing
    case DerParser.NUMERIC_STRING:
    case DerParser.PRINTABLE_STRING:
    case DerParser.VIDEOTEX_STRING:
    case DerParser.IA5_STRING:
    case DerParser.GRAPHIC_STRING:
    case DerParser.ISO646_STRING:
    case DerParser.GENERAL_STRING:
      encoding = "ISO-8859-1"; //$NON-NLS-1$
      break;

    case DerParser.BMP_STRING:
      encoding = "UTF-16BE"; //$NON-NLS-1$
      break;

    case DerParser.UTF8_STRING:
      encoding = "UTF-8"; //$NON-NLS-1$
      break;

    case DerParser.UNIVERSAL_STRING:
      throw new IOException("Invalid DER: can't handle UCS-4 string"); //$NON-NLS-1$

    default:
      throw new IOException("Invalid DER: object is not a string"); //$NON-NLS-1$
    }

    return new String(value, encoding);
  }
}
