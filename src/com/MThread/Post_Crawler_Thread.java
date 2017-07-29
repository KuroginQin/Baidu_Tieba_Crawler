package com.MThread;

import com.Entity.Post;
import com.Process.Post_Crawler;
import com.Store.Store_Info;


//ʵ�ֲ�����ȡ�������������ݵ��߳���
public class Post_Crawler_Thread implements Runnable
{
	private String tieba_name = ""; //��ǰ�������ڵ��������� 
	private int post_index = -1; //��ǰ�������� 
	private String post_title = ""; //��ǰ���ӱ��� 
	private String post_URL = ""; //��ǰ����ҳ��URL
	
	//���췽��
	//tieba_name ��ǰ����������������; post_index ��ǰ��������; post_title ��ǰ���ӱ���;  post_URL ��ǰ����URL
	public Post_Crawler_Thread(String tieba_name, int post_index, String post_title, String post_URL)
	{
		this.tieba_name = tieba_name;
		this.post_index = post_index;
		this.post_title = post_title;
		this.post_URL = post_URL;
	}
	
	//����ʵ��Runnable�ӿڵ�run����
	public void run()
	{
		//��ȡ��ǰ����ҳ��
		Post_Crawler cur_post_crawler = new Post_Crawler(post_URL, post_title);
		//��ȡ��ǰ����ʵ��
		Post cur_post = cur_post_crawler.get_post();
		//����ȡ���������ݱ���������
		new Store_Info(tieba_name, post_index, cur_post);
	}
	
	//����
	/*public static void main(String args[])
	{
		Post_Crawler_Thread thread1 = new Post_Crawler_Thread("PKU", 0, "����Ŀ��2017�걱�������������Ļ", "http://tieba.baidu.com/p/5226092940");
		Post_Crawler_Thread thread2 = new Post_Crawler_Thread("PKU", 1, "����2017������ڼ�ι�У԰�Ĺ���", "http://tieba.baidu.com/p/5216928962");
		Post_Crawler_Thread thread3 = new Post_Crawler_Thread("PKU", 2, "�������Ρ��������γ���ϸ���ԣ���ѡ·��+�������+ͼƬʵ��", "http://tieba.baidu.com/p/3055335624");
		Thread t1 = new Thread(thread1);
		Thread t2 = new Thread(thread2);
		Thread t3 = new Thread(thread3);
		t1.start();
		t2.start();
		t3.start();
		try
		{
			t1.join();
			t2.join();
			t3.join();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
}