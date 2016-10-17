package com.hike.digitalgymnastic.db;

import android.content.Context;
import android.text.TextUtils;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
	public static  String  db_name="hiko";
	public static final int version=1;
	public static  DbUtils  db_utils;
	
	private static DBManager dbManger;
	Context context;
	private DBManager(Context context){
		this.context=context;
	}
	//根据不同的用户创建不同的数据库
	public static DBManager getIntance(Context context, String name){
		if(TextUtils.isEmpty(name))
		{
			dbManger=null;
		}else{
			db_name="hiko"+"_"+name;
			if(dbManger==null){
				dbManger=new DBManager(context);
				dbManger.createDataBase(context);
			}
		}
		return dbManger;
	}
	
	public boolean expireUpdate()
	{
		return false;
	}
	
	public boolean saveUserInfo()
	{
		return false;
	}
	
	public boolean updateUserInfo()
	{
		return false;
	}
	
	public boolean saveCounter()
	{
		return false;
	}
	
	public boolean saveSleep()
	{
		return false;
	}
	
	public boolean saveTask()
	{
		return false;
	}
	
	private  synchronized  void createDataBase(Context context){
		db_utils=DbUtils.create(context, db_name,version, dbUpgradeListener);
		
	}
	public  synchronized  void createTable(List<Class> cList){
		try {
			for(Class c:cList){
				db_utils.createTableIfNotExist(c);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //创建一个表
	}
	
	public synchronized  void save(Object entity){
		try {
			db_utils.save(entity);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public synchronized  void saveAll(List list){
		try {
			db_utils.saveAll(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized  void saveOrupdateAll(List list){
		try {
			db_utils.saveOrUpdateAll(list);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized List getObject(Class entity){
		List b=null;
		try {
			b = db_utils.findAll(entity);
			if(b==null) 
				return new ArrayList();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return b;
	}
	
	public synchronized List getAllObject(Class entity){
		List b=null;
		try {
			b = db_utils.findAll(entity);
			if(b==null) 
				return new ArrayList();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return b;
	}
	public synchronized List getAllObject(Class entity,Selector selection){
		List b=null;
		try {
			b = db_utils.findAll(selection);
			if(b==null) 
				return new ArrayList();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return b;
	}
	public synchronized Object getObject(Class entity,Selector selector){
		try {
			return db_utils.findFirst(selector);
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
	
	public synchronized void  deleteObject(Object entity){
		try {
			 db_utils.delete(entity);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//清空表
	public synchronized void clearTable(Class entity){
		try {
			db_utils.deleteAll(entity);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private  static DbUpgradeListener dbUpgradeListener=new DbUpgradeListener() {
		
		@Override
		public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
			
		}
	};
	
}
