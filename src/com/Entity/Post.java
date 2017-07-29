package com.Entity; 

import java.util.List;
import java.util.ArrayList;

//����ʵ����
public class Post
{
	private String URL = ""; //����ҳ��URL
	private String title = ""; //���ӱ���
	private String owner = ""; //¥���ǳ�
	List<Floor> floor_list = new ArrayList<Floor>(); //����(¥��)�б�
	
	//���췽��
	public Post(String URL, String title, String owner)
	{
		this.URL = URL;
		this.title = title;
		this.owner = owner;
	}
	
	//���¥��ķ���
	public void add_floor(Floor floor)
	{
		floor_list.add(floor);
	}
	
	//��ȡ����ҳ��URL�ķ���
	public String get_URL()
	{
		return URL;
	}
	
	//��ȡ���ӱ���ķ���
	public String get_title()
	{
		return title;
	}
	
	//��ȡ¥���ǳƵķ���
	public String get_owner()
	{
		return owner;
	}
	
	//��ȡ¥���б�ķ���
	public List<Floor> get_floor_list()
	{
		return floor_list;
	}
}