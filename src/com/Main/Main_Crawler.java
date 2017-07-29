package com.Main;

import com.Process.Tieba_Crawler;
import com.MThread.Post_Crawler_Thread;

import java.util.List;
import java.util.ArrayList;

//百度贴吧爬虫
public class Main_Crawler
{
	//构造方法
	//tieba_URL 贴吧页面URLl; tieba_name 贴吧名称; max_tieba_page_num 爬取的最大贴吧页面数; 爬取帖子页面的线程数
	public Main_Crawler(String tieba_URL, String tieba_name, int max_tieba_page_num, int post_thread_num)
	{
		//爬取贴吧页面
		Tieba_Crawler tieba_crawler = new Tieba_Crawler(tieba_URL, max_tieba_page_num);
		//获取贴吧页面基本信息
		List<String> post_title_list = tieba_crawler.get_post_title_list(); //帖子标题列表
		List<String> post_URL_list = tieba_crawler.get_post_URL_list(); //帖子页面URL列表
		//根据帖子列表,爬取帖子页面
		int list_len = post_title_list.size(); //列表长度
		//根据列表长度和线程数,并行爬取帖子页面
		int index=0;
		while(index==0 || (index<list_len && Thread.activeCount()>1)) 
		{
			if(Thread.activeCount()<post_thread_num+1) //当前线程数没有达到最大线程数时,创建线程爬取帖子页面
			{
				System.out.println("-Current Active Thread: " + (Thread.activeCount()-1));
				String cur_post_title = post_title_list.get(index); //当前帖子标题
				String cur_post_URL= post_URL_list.get(index); //当前帖子URL
				Post_Crawler_Thread cur_post_thread = new Post_Crawler_Thread(tieba_name, index, cur_post_title, cur_post_URL); //当前帖子线程
				new Thread(cur_post_thread).start();
				++index;
			}
		}
	}
	
	public static void main(String args[])
	{
		//tieba_URL 贴吧页面URL; tiebe_name 贴吧名称; max_page_num 爬取的最大贴吧页面数; post_thread_num 爬虫最大线程数
		//String tieba_URL = "http://tieba.baidu.com/f?kw=北京大学";
		//String tieba_name = "PKU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=清华大学";
		//String tieba_name = "THU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=天津大学";
		//String tieba_name = "TJU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=南开大学";
		//String tieba_name = "NKU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=上海交通大学";
		//String tieba_name = "SJTU";
		
		String tieba_URL = "http://tieba.baidu.com/f?kw=复旦大学";
		String tieba_name = "FDU";
		
		int max_page_num = 20;
		int post_thread_num = 20;
		new Main_Crawler(tieba_URL, tieba_name, max_page_num, post_thread_num);
	}
}