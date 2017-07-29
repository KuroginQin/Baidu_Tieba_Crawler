package com.Entity;

//楼层实体类
public class Floor
{
	private int floor_index = -1; //楼层索引
	private String author = ""; //用户昵称
	private String content = ""; //楼层评论内容
	private boolean is_owner = false; //楼主判断标志
	
	//构造方法
	public Floor(int floor_index, String author, boolean is_owner, String content)
	{
		this.floor_index = floor_index;
		this.author = author;
		this.content = content;
		this.is_owner = is_owner;
	}
	
	//获取楼层索引的方法
	public int get_floor_index()
	{
		return floor_index;
	}
	
	//获取用户昵称的方法
	public String get_author()
	{
		return author;
	}
	
	//获取楼层评论内容的方法
	public String get_content()
	{
		return content;
	}
	
	//获取楼主判断标志的方法
	public boolean get_is_owner()
	{
		return is_owner;
	}
}