package zhongruan.com.cn;

import javax.swing.*;

import java.awt.*;   //导入必要的包
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class AuthConfig extends JFrame{
    public static JTextField jTextField ;//定义文本框组件
    public static JTextField jTextField1 ;//定义文本框组件
    public static JTextField jTextField2 ;//定义文本框组件
    public static JTextField jTextField3 ;//定义文本框组件
    public static JTextField jTextField4 ;//定义文本框组件
    public static JTextField jTextField5 ;//定义文本框组件
    public static JTextField jTextField6 ;//定义文本框组件
   // public static JPasswordField jPasswordField;//定义密码框组件
    public static JLabel jLabel1,jLabel2,jLabel3,jLabel4,jLabel5,jLabel6,jLabel7;
    public static JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8;
    public static JButton jb1,jb2; //创建按钮
    public  AuthConfig(){
        jTextField = new JTextField(20);
        jTextField1=new JTextField(20);
        jTextField2=new JTextField(20);
        jTextField3=new JTextField(20);
        jTextField4=new JTextField(20);
        jTextField5=new JTextField(20);
        jTextField6=new JTextField(20);
       // jPasswordField = new JPasswordField(13);
        jLabel1 = new JLabel("机器码                ");
        jLabel2 = new JLabel("文件路径            ");
        jLabel3 = new JLabel("是否试用(是/否)");
        jLabel4 = new JLabel("试用时间(天)     ");
        jLabel5 = new JLabel("允许终端总数    ");
        jLabel6 = new JLabel("客户信息            ");
        jLabel7 = new JLabel("堡垒机授权码   ");
           jb1 = new JButton("生成授权文件    ");
        //jb2 = new JButton("取消");
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 =  new JPanel();
        jp5 =  new JPanel();
        jp6 =  new JPanel();
        jp7 =  new JPanel();
        jp8 =  new JPanel();
        
        //设置布局
        this.setLayout(new GridLayout(8,1));
        
        
        jp1.add(jLabel1); 
        jp1.add(jTextField);//第一块面板添加用户名和文本框 
        
        jp2.add(jLabel2);
        jp2.add(jTextField1);//第二块面板添加密码和密码输入框
        
        jp4.add(jLabel3);
        jp4.add(jTextField2);
        
        jp5.add(jLabel4);
        jp5.add(jTextField3);
        
        jp6.add(jLabel5);
        jp6.add(jTextField4);
        
        jp7.add(jLabel6);
        jp7.add(jTextField5);
        
        jp8.add(jLabel7);
        jp8.add(jTextField6);
        
        jp3.add(jb1);
        //jp3.add(jb2); //第三块面板添加确认和取消
        
        
        //        jp3.setLayout(new FlowLayout());  　　//因为JPanel默认布局方式为FlowLayout，所以可以注销这段代码.
        this.add(jp1);
        this.add(jp2);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);
        this.add(jp7);
        this.add(jp8);
        this.add(jp3);  //将三块面板添加到登陆框上面
        //设置显示
        this.setSize(400, 300);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("二代管理中心授权工具");
         
    }
    public static void main(String[] args){
        new AuthConfig();
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String authcode=jTextField.getText().trim();//机器码
              String authPath = jTextField1.getText().trim();//文件路径
              String isTest = jTextField2.getText().trim();//是否试用
              String testTime = jTextField3.getText().trim();//试用时间(tian)
              testTime=testTime==null||testTime.equals("")?"0":testTime;
              String clientNum = jTextField4.getText().trim();//允许终端总数
              String clientInfo = jTextField5.getText().trim();//client信息
              String htsWebCode = jTextField6.getText().trim();//堡垒机授权码信息
              creatLicense(authcode,authPath,isTest,testTime,clientNum,clientInfo,htsWebCode);
            }
        });
    }
    public static void creatLicense(String code,String path,String isTest,String testTime,String clientNum,String clientInfo,String htsWebCode){
    	File license=new File(path+File.separator+"licensesfile.key");
        if(!license.exists()){
        	 BufferedWriter bw=null;
        	try {
				license.createNewFile();
			    StringBuilder msg=new StringBuilder();
			    msg.append("code:"+code+",");
			    msg.append("isTest:"+isTest+",");
			    msg.append("testTime:"+testTime+",");
			    msg.append("clientNum:"+clientNum+",");
			    msg.append("clientInfo:"+clientInfo+",");
			    msg.append("codeMd5:"+EncryptUtil.encodeByMD5(code));
		      bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(license),"UTF-8"));
		      String desBaseMsg=Service.getEncoder(msg.toString(),htsWebCode);
			  bw.write(desBaseMsg.toString());
			  bw.flush();
			  
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(bw!=null){
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
        }
    	
    }
}

