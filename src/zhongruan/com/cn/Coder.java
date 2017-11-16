package zhongruan.com.cn;



import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public  class Coder {  
	  private static final String privatekey="GmCL+M7jQXf/7dpJRxwQHkccJZjjCSKH+luPkllMqILr4tirK+LMe0OVSqO4GuGhEaweCV24JXRYCxdr+ABCnUPHyYP3j3T/evS4SA5pLo7Nht2XSaiO7Pi2LDH1+9mME4kvLDJ3hdFFwyeng84dPg5aSxGnSKT5oAU4BaXR+xeFiHOS9jZVyslxnDwi2igi0Z3g/gKgIghubYiKZg0KZzR35hNCcby3epUkK15wKN2nJ+l+nwEVBk3Rd+rTY/a1ZVD1sLRmplL64cawHe/xsVTOhGogvb3fBDDCrSeKvWm3BQhrN19vxUOZ4yuVOBxr5dJnrix/3ynMFjocGPun7Q==";
    /** 
     * BASE64解密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new BASE64Decoder()).decodeBuffer(key);  
    }  
  
    /** 
     * BASE64加密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
        return (new BASE64Encoder()).encodeBuffer(key).replace("\r", "").replace("\n", "");  
    }  
    /**
     * 加密
     * @param datasource byte[]
     * @param password String
     * @return byte[]
     */
    public static  byte[] encrypt(byte[] datasource, String password) {            
        try{
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        //现在，获取数据并加密
        //正式执行加密操作
        return cipher.doFinal(datasource);
        }catch(Throwable e){
                e.printStackTrace();
        }
        return null;
     }
    /**
     * 解密
     * @param src byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return cipher.doFinal(src);
     }
    public static String getEncoder(String msg){
    	 //加密
    	try {
			byte[] buf = encrypt(msg.getBytes("UTF-8"),Coder.privatekey);
			String buf1=encryptBASE64(buf);
			return buf1;
		} catch (Exception e) {
			return "0";
		}
    	
    }
    public static String getDecoder(String msg){
    	//解密
    	
		try {
			byte[] result=decryptBASE64(msg);	
			String result2 = new String(decrypt(result,Coder.privatekey),"UTF-8");
			return result2;
	    } catch (Exception e) {
	    	return "0";
		}
    }
    public static void main(String[] args) throws Exception{
    	String msg="mdwNhxEpCeGzRbLRuB8qHASDtiaL5wFy2SbQWot/Li+Qkm70uA2GAg9Wz4kLB0v5yr/U1Bv9w3YX3UyvOtOpGVPZCRv+BbV05s2vCz0X51/i48M2/AteE/huokeWGUNwsgO8A/QznGUESPF2Wav0I4b8LSiMh+EchNneu3E2B/Vh2D3I+rlHsaw5ozwHqJWztAh/ySHQzrLIOvqTASubq6oEoy3YXaw5RdvcBlEVhXP42bV6B8wUJvF6aQ18tkIO+1ILhX0Eb4RbUuJtIrZkK1V4sjQLj3vzRbtdSBBfMfRWO5JWajrx3SBU8+7e0/vQ0SaJj5uOFHLvuR1dhQmSc4466J+iIIG4pQNsHjomJiudHqenOR7byAYftgl7bhoU69XxcWAjf9Zy67apNsv9o/VJ98Szt/3VDlP53tgHeA5xIYSB9JWNjZiEH1weaDcJC5zuhS1oSwvlrz3LMnegloQ5VXh0UQOinBCcQlgQO/NOiWlKTnPwiWiYX29QfdDPtl/kAdq/SEkhe9h1DDHAHxuy2EG3dIDGCymUfHkiz55+L+O6xvHn0V+/hh/LxlBqJdoB+M4WU0i83gjX+cBpZ9rRGhprqStL8R3SH6qkBji1X/QSRo+4gh2qD84JImgaK+xYLt7U4FOQLXl1/lXI93hVzvceRJnc9wzLxM8nG5O0G7enWMXBBDunnNfU5UReaA4DeN4gbT4gcyofSCbu54lup0/9D3mwaAeSa8qmKZ5UuVHR4bW2Hno8z8Mmy2H4uNYjtewnTLFnYs0TrVATMv58vk4ZZcjMDmROCYBEWgFEjXFFvJN5h1FjdTn4/b/PptYQx2XX8zGBt1Gaqhv//LBGqu7lDRzIdpceA7BbwUWXMjvLX+PCJoWSw88YbZBOqpZdUN90bhCLlq0qWJYtkgXncrdCQsSVC5XH6BhH2gxx/PxMc6WtiOcVDd3oG3CzSCVqQbr7zWc+ndyMXMKG1E4o2kyYqyKq";
//    	String key="GmCL+M7jQXf/7dpJRxwQHkccJZjjCSKH+luPkllMqILr4tirK+LMe0OVSqO4GuGhEaweCV24JXRYCxdr+ABCnUPHyYP3j3T/evS4SA5pLo7Nht2XSaiO7Pi2LDH1+9mME4kvLDJ3hdFFwyeng84dPg5aSxGnSKT5oAU4BaXR+xeFiHOS9jZVyslxnDwi2igi0Z3g/gKgIghubYiKZg0KZzR35hNCcby3epUkK15wKN2nJ+l+nwEVBk3Rd+rTY/a1ZVD1sLRmplL64cawHe/xsVTOhGogvb3fBDDCrSeKvWm3BQhrN19vxUOZ4yuVOBxr5dJnrix/3ynMFjocGPun7Q==";
//        //加密
//    	byte[] buf=encrypt(msg.getBytes("UTF-8"),key);
//    	String buf1=encryptBASE64(buf);
//    	System.out.println("加密后:"+buf1);
//    	
//    	byte[] result=decryptBASE64(buf1);	
//    	String result2=new String(decrypt(buf,key),"UTF-8");
//    	System.out.println("解密后："+result2);
    	
//    	String buf=getEncoder(msg);
//    	String result=getDecoder(buf);
//    	System.out.println("加密后:"+buf);
//    	System.out.println("解密后"+result);
//    	
//    	String result=getDecoder(msg);
//    	System.out.println("解密后"+result);
//    	
    	System.out.println(encryptBASE64("当123.com".getBytes("UTF-8")));
    }
}

















