package com.hike.digitalgymnastic.service;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDPATH;
	
	private int FILESIZE = 4 * 1024; 
	
	public String getSDPATH(){
		return SDPATH;
	}
	
	public FileUtils(){
		//得到当前外部存储设备的目录( /SDCARD )
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}
	
	/**
	 * 在SD卡上创建文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException{
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * 在SD卡上创建目录
	 * @param dirName
	 * @return
	 */
	public File createSDDir(String dirName){
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}
	
	/**
	 * 判断SD卡上的文件夹是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName){
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	/**
	 * 判断SD卡上的文件夹是否存在,如果存在，删除
	 * @param fileName
	 * @return
	 */
	public void deleteFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.exists())
			file.delete();
	}
	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
                            byte[] buffer = new byte[FILESIZE];

			/*真机测试，这段可能有问题，请采用下面网友提供的
                           	while((input.read(buffer)) != -1){
				output.write(buffer);
			}
                            */

                           /* 网友提供 begin */
                           int length;
                           while((length=(input.read(buffer))) >0){
                                 output.write(buffer,0,length);
                           }
                           /* 网友提供 end */

			output.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	public byte[] file2Array(File file){
		FileInputStream is=null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			is = new FileInputStream(file);
			
			byte[] buffer = new byte[1024];
			int len =-1 ;
			while ((len=is.read(buffer))!=-1) {
				bos.write(buffer, 0, len);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
			if(is!=null){
				is.close();
			}
			if(bos!=null){
				bos.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		return bos.toByteArray();
	}
}

