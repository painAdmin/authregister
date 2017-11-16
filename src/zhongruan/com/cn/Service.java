package zhongruan.com.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Service {

	/**
	 * 
	 * @param msg ：管理中心授权码
	 * @param htsWebCode 堡垒机授权码
	 * @return
	 * @throws Exception
	 */
	public static String getEncoder(String msg,String htsWebCode) throws Exception{
		String res=null; //返回业务处理后的信息字符串
		String[] result=msg.split(",");
		String basecode=result[0].split(":")[1];
		String code=new String(Coder.decryptBASE64(basecode),"UTF-8");
		if(!"0".equals(code)){
			String authStr="code:"+code+msg.substring(msg.indexOf(","));
			String md5str=EncryptUtil.encodeByMD5(Coder.encryptBASE64(authStr.getBytes("UTF-8")));//支持jar运行时对中文支持
		    res=authStr+",md5:"+md5str+";htsWebCode:"+htsWebCode;
		}
		return Coder.getEncoder(res);
	}
	public static String getDecoder(String path) throws Exception{
		File file=new File(path);
		if(!file.exists()){
			return "0";
		}
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		String msg="";
		String line=null;
		while((line=br.readLine())!=null){
			msg+=line;
			line=null;
		}
		String res=Coder.getDecoder(msg);
		if(br!=null){
			br.close();
		}
		return res;
	}
	public static void main(String[] args) throws Exception{
		String path="C:\\Users\\pain\\Desktop\\licenses.key";
		System.out.println(getDecoder(path));
	}
}









