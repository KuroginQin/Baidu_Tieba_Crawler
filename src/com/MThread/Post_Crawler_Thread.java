package com.MThread;

import com.Entity.Post;
import com.Process.Post_Crawler;
import com.Store.Store_Info;


//实现并行爬取并保存帖子内容的线程类
public class Post_Crawler_Thread implements Runnable
{
	private String tieba_name = ""; //当前帖子所在的贴吧名称 
	private int post_index = -1; //当前帖子索引 
	private String post_title = ""; //当前帖子标题 
	private String post_URL = ""; //当前帖子页面URL
	
	//构造方法
	//tieba_name 当前帖子所在贴吧名称; post_index 当前帖子索引; post_title 当前帖子标题;  post_URL 当前帖子URL
	public Post_Crawler_Thread(String tieba_name, int post_index, String post_title, String post_URL)
	{
		this.tieba_name = tieba_name;
		this.post_index = post_index;
		this.post_title = post_title;
		this.post_URL = post_URL;
	}
	
	//重新实现Runnable接口的run方法
	public void run()
	{
		//爬取当前帖子页面
		Post_Crawler cur_post_crawler = new Post_Crawler(post_URL, post_title);
		//获取当前帖子实体
		Post cur_post = cur_post_crawler.get_post();
		//将爬取的帖子内容保存至本地
		new Store_Info(tieba_name, post_index, cur_post);
	}
	
	//测试
	/*public static void main(String args[])
	{
		Post_Crawler_Thread thread1 = new Post_Crawler_Thread("PKU", 0, "【醒目】2017年北大新生吧友征文活动", "http://tieba.baidu.com/p/5226092940");
		Post_Crawler_Thread thread2 = new Post_Crawler_Thread("PKU", 1, "关于2017年暑假期间参观校园的公告", "http://tieba.baidu.com/p/5216928962");
		Post_Crawler_Thread thread3 = new Post_Crawler_Thread("PKU", 2, "【贴身导游】北大旅游超详细攻略！精选路线+景点介绍+图片实拍", "http://tieba.baidu.com/p/3055335624");
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