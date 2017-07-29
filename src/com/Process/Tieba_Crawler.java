package com.Process;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//爬取页面,提取帖子帖子URL列表
public class Tieba_Crawler
{
	private List<String> post_URL_list = new ArrayList<String>(); //帖子页面URL列表
	private List<String> post_title_list =  new ArrayList<String>(); //帖子标题列表
	private int post_count = 0; //贴子数量计数器 
	
	private static int max_sleep_time = 500; //最大进程休眠时间
	
	//构造方法
	//Tieba_URL 爬取的贴吧页面URL; max_page_num 爬取的最大页面数
	public Tieba_Crawler(String Tieba_URL, int max_page_num)
	{
		//根据页面序列号构造URL,并获取当前页面URL列表
		System.out.println("-Get Post URL List-");
		for(int i=0;i<max_page_num;i++)
		{
			int pn = i*50;
			String cur_URL = Tieba_URL + "&&pn=" + pn;
			//提取当前页面的帖子URL列表
			get_cur_post_URL_list(cur_URL);
			System.out.println("-Page#: " + (i+1));
			//进程随机休眠
			int sleep_time = (int)(Math.random()*max_sleep_time);
			try
			{
				System.out.println("-Sleep Time: " + sleep_time + "msec-");
				Thread.sleep(sleep_time);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("-Total Post: " + post_count);
	}
	
	//提取当前页面的帖子URL列表的方法
	private void get_cur_post_URL_list(String URL)
	{
		//提取当前页面的HTML文本
		StringBuffer stringBuffer = new StringBuffer();
		String str = "";
		try
		{
			java.net.URL url = new java.net.URL(URL); 
			//选择网页的字符集(utf-8/gbk/unicode)
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
			String line;
			//逐行读取HTML文本
			while((line = in.readLine())!=null) 
			{
				stringBuffer.append(line + "\r\n");
			}
			str = stringBuffer.toString();
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//删除冗余的HTML标签
		str = str.replaceAll("(?is)<!DOCTYPE.*?>", "");
		str = str.replaceAll("(?is)<!--.*?-->", "");
		str = str.replaceAll("(?is)<script.*?>.*?</script>", "");
		str = str.replaceAll("(?is)<style.*?>.*?</style>", "");
		//使用Jsoup提取特定内容
		Document doc = Jsoup.parse(str);
		Elements nodes = doc.select("div[class=t_con cleafix]");
		for(Element cnode : nodes) 
		{
			//提取帖子标题
			Element info_node = cnode.select("div[class=threadlist_title pull_left j_th_tit ]").first();
			if(info_node==null)
				continue;
			String cur_post_title = info_node.text();
			
			//使用正则表达式提取URL
			String info = info_node.toString();
			String regex = "<a href=\".*?\" ";
			Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
			Matcher match = pattern.matcher(info);
			String cur_post_URL = "";
			if(match.find())
				cur_post_URL = match.group();
			cur_post_URL = cur_post_URL.replaceAll("<a href=|\"", "");
			cur_post_URL = "http://tieba.baidu.com" + cur_post_URL;
			cur_post_URL = cur_post_URL.trim();
			
			post_title_list.add(cur_post_title);
			post_URL_list.add(cur_post_URL);
			
			System.out.println("-Post Title: " + cur_post_title);
			System.out.println("-Post URL: " + cur_post_URL);
			System.out.println();
			++post_count;
		}
	}
	
	//获取帖子标题列表的方法
	public List<String> get_post_title_list()
	{
		return post_title_list;
	}
	
	//获取帖子页面URL列表的方法
	public List<String> get_post_URL_list()
	{
		return post_URL_list;
	}
	
	//测试
	/*public static void main(String args[])
	{
		new Tieba_Crawler("http://tieba.baidu.com/f?kw=北京大学", 10);
	}*/
}