package com.Entity; 

import java.util.List;
import java.util.ArrayList;

//帖子实体类
public class Post
{
	private String URL = ""; //帖子页面URL
	private String title = ""; //帖子标题
	private String owner = ""; //楼主昵称
	List<Floor> floor_list = new ArrayList<Floor>(); //评论(楼层)列表
	
	//构造方法
	public Post(String URL, String title, String owner)
	{
		this.URL = URL;
		this.title = title;
		this.owner = owner;
	}
	
	//添加楼层的方法
	public void add_floor(Floor floor)
	{
		floor_list.add(floor);
	}
	
	//获取帖子页面URL的方法
	public String get_URL()
	{
		return URL;
	}
	
	//获取帖子标题的方法
	public String get_title()
	{
		return title;
	}
	
	//获取楼主昵称的方法
	public String get_owner()
	{
		return owner;
	}
	
	//获取楼层列表的方法
	public List<Floor> get_floor_list()
	{
		return floor_list;
	}
}