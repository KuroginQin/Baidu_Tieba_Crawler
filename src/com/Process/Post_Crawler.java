package com.Process;

import com.Entity.Floor;
import com.Entity.Post;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.Store.Store_Info;


//爬取帖子页面,提取评论(楼层)内容
public class Post_Crawler
{
	private String owner = ""; //楼主昵称
	private int total_page = 0; //帖子页面总页数
	private int floor_count = 0; //楼层计数器
	
	private Post post; //帖子实体
	
	private static int max_sleep_time = 500; //最大进程休眠时间
	
	//构造方法
	//post_URL 当前帖子页面URL; post_title 当前帖子标题;
	public Post_Crawler(String post_URL, String post_title)
	{
		//获取基本信息
		get_base_info(post_URL);
		//声明帖子实体
		post = new Post(post_URL, post_title, owner);
		//根据帖子页面总数,提取所有评论(楼层)
		for(int i=1;i<=total_page;i++)
		{
			String cur_post_url = post_URL + "?pn=" + i;
			System.out.println("-Current Post URL: " + cur_post_url);
			get_floor(cur_post_url);
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
	}
	
	//获取基本信息的方法
	//基本信息包括 楼主昵称 owner; 总页面数 total_page
	private void get_base_info(String post_URL)
	{
		System.out.println("-Get Post Base Info-");
		//HTML文本预处理
		String str = HTML_process(post_URL);
		//使用Jsoup获取特定内容
		Document doc = Jsoup.parse(str);
		//获取楼主昵称
		owner = doc.select("li[class=d_name]").first().text();
		//获取帖子页面总页数
		String total_page_str = doc.select("li[class=l_reply_num]").first().select("span[class=red]").last().text();
		total_page = Integer.parseInt(total_page_str);
		System.out.println("-Owner: " + owner);
		System.out.println("-Total Page: " + total_page);
		System.out.println();
	}
	
	//提取评论(楼层)内容的方法
	private void get_floor(String post_URL)
	{
		System.out.println("-Get Floor Info-");
		String str = HTML_process(post_URL);
		//使用Jsoup提取特定内容
		Document doc = Jsoup.parse(str);
		Elements nodes = doc.select("div[class=l_post j_l_post l_post_bright  ],div[class=l_post j_l_post l_post_bright noborder ]"); 
		for(Element cnode:nodes) 
		{
			//提取用户昵称
			String cur_author = cnode.select("li[class=d_name]").first().text();
			//判断是否为楼主
			boolean is_owner = false;
			if(cur_author.equals(owner))
				is_owner = true;
			//提取评论(楼层)内容
			String cur_content = "";
			Element content_node = cnode.select("div[class=p_content  p_content p_content_icon_row1 p_content_nameplate],div[class=p_content  p_content p_content_nameplate]").first();
			if(content_node!=null)
			{
				//保留内容中的格式
				cur_content = content_node.html();
				cur_content = cur_content.replaceAll("(\\n|\\r\\n).*?<strong>|</strong>|(\\n|\\r\\n).*?<a.*?>|</a>",""); //去除<strong></strong>标签和<a></a>标签
				cur_content = cur_content.replaceAll("<br>", "\r\n"); //保留HTML中的换行操作
				cur_content = cur_content.replaceAll("<.*?>",""); //去除HTML标签
				cur_content = cur_content.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", ""); //去除多余的空行
				cur_content = cur_content.trim(); //去除首位的多余空格
			}
			//声明楼层实体
			Floor cur_floor = new Floor(floor_count, cur_author, is_owner, cur_content);
			//跟新当前帖子的评论(楼层)列表
			post.add_floor(cur_floor);
			
			System.out.println("-Floor#: " + floor_count);
			System.out.println("-Author: " + cur_author);
			System.out.println("-Is Owner: " + is_owner);
			System.out.println("-Content: \r\n" + cur_content) ;
			System.out.println();
			++floor_count;
		}
	}
	
	//HTML文本预处理的方法
	private String HTML_process(String URL)
	{
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
		
		return str;
	}
	
	//定义获取帖子实体的方法
	public Post get_post()
	{
		return post;
	}
	
	//测试
	/*public static void main(String args[])
	{
		//new Post_Crawler("http://tieba.baidu.com/p/5226092940", "关于2017年暑假期间参观校园的公告");
		//new Post_Crawler("http://tieba.baidu.com/p/3055335624", "【贴身导游】北大旅游超详细攻略！精选路线+景点介绍+图片实拍");
		//new Post_Crawler("http://tieba.baidu.com/p/5226092940", "【醒目】2017年北大新生吧友征文活动");
		//new Post_Crawler("http://tieba.baidu.com/p/5241305852?fid=4509", "第一次在这里发帖子，我。。我9");
		
		Post_Crawler post_crawler = new Post_Crawler("http://tieba.baidu.com/p/3055335624", 
				"【贴身导游】北大旅游超详细攻略！精选路线+景点介绍+图片实拍");
		Post cur_post = post_crawler.get_post();
		new Store_Info("PKU", 0, cur_post);
	}*/
}