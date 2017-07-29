package com.Entity;

//¥��ʵ����
public class Floor
{
	private int floor_index = -1; //¥������
	private String author = ""; //�û��ǳ�
	private String content = ""; //¥����������
	private boolean is_owner = false; //¥���жϱ�־
	
	//���췽��
	public Floor(int floor_index, String author, boolean is_owner, String content)
	{
		this.floor_index = floor_index;
		this.author = author;
		this.content = content;
		this.is_owner = is_owner;
	}
	
	//��ȡ¥�������ķ���
	public int get_floor_index()
	{
		return floor_index;
	}
	
	//��ȡ�û��ǳƵķ���
	public String get_author()
	{
		return author;
	}
	
	//��ȡ¥���������ݵķ���
	public String get_content()
	{
		return content;
	}
	
	//��ȡ¥���жϱ�־�ķ���
	public boolean get_is_owner()
	{
		return is_owner;
	}
}