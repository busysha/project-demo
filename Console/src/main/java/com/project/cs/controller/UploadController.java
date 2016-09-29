package com.project.cs.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

//import sun.misc.BASE64Decoder;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.sun.jersey.core.util.Base64;
import com.project.common.util.QiNiuUtil;

import net.sf.json.JSONObject;

/**
 * 图片上传
 * @author dahuangfeng
 *
 */
@Controller
@RequestMapping(value="/upload")
public class UploadController {
	private static Logger logger = Logger.getLogger(UploadController.class);
	@RequestMapping(value="/getQiniuUpToken")
	@ResponseBody
	public String getToken(HttpServletRequest request,HttpServletResponse response ){
		String token = QiNiuUtil.getToken();
		JSONObject json = new JSONObject();
		json.put("uptoken", token);
		return json.toString();
	}
	
	@RequestMapping(value="/getImage")
	@ResponseBody
	public Map<String, Object> getImage(HttpServletRequest request,HttpServletResponse response,MultipartRequest requests ){
		
		MultipartFile	d =  requests.getFile("imgFile");
		
		UploadManager uploadManager = new UploadManager();
		String Uname = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			 uploadManager.put(d.getBytes(),Uname,  QiNiuUtil.getToken());
			 Map<String, Object> succMap = new HashMap<String, Object>();  
             succMap.put("error", 0);  
             succMap.put("url", QiNiuUtil.getUrl() + Uname);  
             return succMap;  
		} catch (QiniuException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	@RequestMapping(value="/editImage")
	@ResponseBody
	public Map<String, Object> editImage(HttpServletRequest request,HttpServletResponse response,MultipartRequest requests ){
		
		//图片的base64编码
		String baseImage =  request.getParameter("imgUrl");
		//图片的初始长度
		int imgInitW  =  Math.round(Float.parseFloat(request.getParameter("imgInitW")));
		//图片的初始化高度
		int imgInitH  =  Math.round(Float.parseFloat(request.getParameter("imgInitH")));
		//页面上宽度
		int imgW =  Math.round(Float.parseFloat(request.getParameter("imgW")));
		//页面上高度
		int imgH =   Math.round(Float.parseFloat(request.getParameter("imgH")));
		//图片框的高度
		int cropH =   Math.round(Float.parseFloat(request.getParameter("cropH")));
		//框的长度
		int cropW =   Math.round(Float.parseFloat(request.getParameter("cropW")));
		//距离框的高度
		int imgY1 =   Math.round(Float.parseFloat(request.getParameter("imgY1")));
		//距离框的宽度
		int imgX1 =   Math.round(Float.parseFloat(request.getParameter("imgX1")));
		//缩放比例
		
		byte[]  result = 	cupImge(baseImage, imgInitW, imgInitH, imgW, imgH, imgX1, imgY1, cropH, cropW);
		
		UploadManager uploadManager = new UploadManager();
		String Uname = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			 uploadManager.put(result,Uname, QiNiuUtil.getToken());
			 Map<String, Object> succMap = new HashMap<String, Object>();  
             succMap.put("status", "success");  
             succMap.put("url", QiNiuUtil.getUrl() + Uname);  
             return succMap;  
		} catch (Exception e) {
			logger.error(e);
		} 
		return null;
	}
	
	public byte[]  cupImge(String baseImage,int imgInitW ,int imgInitH,int imgW,int imgH,int imgX1,int imgY1,int cropH ,int cropW){
		
 	/*	int srcWidth = imgInitW; // 源图宽度
        int srcHeight = imgInitH; // 源图高度
        //测试
        System.out.println("srcWidth= " + srcWidth + "\tsrcHeight= "+ srcHeight);*/
        BufferedImage bi;
        Image img;
        ImageFilter cropFilter;
		try {
		  //通过字字符串保存图片
			/*System.err.println(baseImage);*/
			String[] datawss= baseImage.split(",");
			byte[] debytes =  Base64.decode(datawss[1]);
			//获取图片
			ByteArrayInputStream in = new ByteArrayInputStream(debytes);
			bi = ImageIO.read(in);  
			
			
		/*	BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
			debytes = decoder.decodeBuffer(baseImage);*/
		     /*  for(int i=0;i<debytes.length;++i) {
	                if(debytes[i]<0) {//调整异常数据
	                	debytes[i]+=256;
	                }
	            }*/
		
	   /*  BufferedInputStream ins = new BufferedInputStream(in);*/
			/*System.err.println(debytes);*/
			
			/* BufferedImage bi1 =ImageIO.read(in);  
			 File w2 = new File("e:\\test.png");//可以是jpg,png,gif格式    
             ImageIO.write(bi1, "png", w2);//不管输出什么格式图片，此处不需改动    
*/ 
		/*	String imgFilePath = "e:\\test.png";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(debytes);
			out.flush();
			out.close();*/
		/*	ByteArrayInputStream datass =	new ByteArrayInputStream(debytes) ;
			
			bi = ImageIO.read(datass);
			bi = ImageIO.read(new File("e:\\123.png"));*/
			
	      //获取缩放后的图片大小
			Image image = bi.getScaledInstance(imgW, imgH,Image.SCALE_DEFAULT);
			/*  bi = ImageIO.read(new File("e:\\123.png"));*/
			/*   image = Toolkit.getDefaultToolkit().createImage(debytes);*/
	     //图片切割类
	      cropFilter  = new CropImageFilter(imgX1, imgY1, cropW, cropH);
	      img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
	      BufferedImage tag = new BufferedImage(cropW, cropH,BufferedImage.TYPE_INT_RGB);
	      Graphics g = tag.getGraphics();
          g.drawImage(img, 0, 0, null); // 绘制截取后的图
          g.dispose();
         /* DataBuffer df =   tag.getData().getDataBuffer();*/
          ByteArrayOutputStream datsa = new ByteArrayOutputStream();
          ImageIO.write(tag,"png",datsa);
          /*   byte[] data  =     datsa.toByteArray();*/
          /*  DataBuffer data =  tag.getData().getDataBuffer()*/
          /*.toString().getBytes()*/;
	      /*       byte[] data =    ((DataBufferByte)df).getData();*/
	      /* BufferedImage imgs = ImageIO.read(img); */
	      /*   byte[] dats =  ((DataBufferByte)tag.getRaster().getDataBuffer()).getData();*/
	      /*    ImageIO.write(tag, "png", new File("e:\\test.png"));*/
          
          return datsa.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
        /*BufferedImage bi =baseImage.getBytes();*/
        /*   bi = ImageIO.read(baseImage.getBytes());*/
     
        
    /*    ImageFilter cropFilter = new CropImageFilter(imgX1, imgX1, destWidth, destHeight);*/
/*        Image image = bi.getScaledInstance(finalWidth, finalHeight,Image.SCALE_DEFAULT);//获取缩放后的图片大小
*/		
		return null;
	}
	
	
	
	public  void abscut(final String srcImageFile,String savedImagePath, final int x, final int y, final int destWidth,
            final int destHeight,final int finalWidth,final int finalHeight) {
   
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi;
            try {
                bi = ImageIO.read(new File(srcImageFile));
                	int srcWidth = bi.getWidth(); // 源图宽度
                    int srcHeight = bi.getHeight(); // 源图高度
                  
                    System.out.println("srcWidth= " + srcWidth + "\tsrcHeight= " + srcHeight);
                   
                        Image image = bi.getScaledInstance(finalWidth, finalHeight,Image.SCALE_DEFAULT);//获取缩放后的图片大小
                        cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight,
                                BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制截取后的图
                        g.dispose();
                       
                        ImageIO.write(tag, getExtention(srcImageFile), new File(savedImagePath));
                             // 输出为文件
                            //再次进行缩放
                   
                   
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
          
      
    
        }

/**
     * 功能：提取文件名的后缀
     *
     * @param fileName
     * @return
     */
    private static  String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }

}
