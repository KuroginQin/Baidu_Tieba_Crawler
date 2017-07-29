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


//��ȡ����ҳ��,��ȡ����(¥��)����
public class Post_Crawler
{
	private String owner = ""; //¥���ǳ�
	private int total_page = 0; //����ҳ����ҳ��
	private int floor_count = 0; //¥�������
	
	private Post post; //����ʵ��
	
	private static int max_sleep_time = 500; //����������ʱ��
	
	//���췽��
	//post_URL ��ǰ����ҳ��URL; post_title ��ǰ���ӱ���;
	public Post_Crawler(String post_URL, String post_title)
	{
		//��ȡ������Ϣ
		get_base_info(post_URL);
		//��������ʵ��
		post = new Post(post_URL, post_title, owner);
		//��������ҳ������,��ȡ��������(¥��)
		for(int i=1;i<=total_page;i++)
		{
			String cur_post_url = post_URL + "?pn=" + i;
			System.out.println("-Current Post URL: " + cur_post_url);
			get_floor(cur_post_url);
			//�����������
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
	
	//��ȡ������Ϣ�ķ���
	//������Ϣ���� ¥���ǳ� owner; ��ҳ���� total_page
	private void get_base_info(String post_URL)
	{
		System.out.println("-Get Post Base Info-");
		//HTML�ı�Ԥ����
		String str = HTML_process(post_URL);
		//ʹ��Jsoup��ȡ�ض�����
		Document doc = Jsoup.parse(str);
		//��ȡ¥���ǳ�
		owner = doc.select("li[class=d_name]").first().text();
		//��ȡ����ҳ����ҳ��
		String total_page_str = doc.select("li[class=l_reply_num]").first().select("span[class=red]").last().text();
		total_page = Integer.parseInt(total_page_str);
		System.out.println("-Owner: " + owner);
		System.out.println("-Total Page: " + total_page);
		System.out.println();
	}
	
	//��ȡ����(¥��)���ݵķ���
	private void get_floor(String post_URL)
	{
		System.out.println("-Get Floor Info-");
		String str = HTML_process(post_URL);
		//ʹ��Jsoup��ȡ�ض�����
		Document doc = Jsoup.parse(str);
		Elements nodes = doc.select("div[class=l_post j_l_post l_post_bright  ],div[class=l_post j_l_post l_post_bright noborder ]"); 
		for(Element cnode:nodes) 
		{
			//��ȡ�û��ǳ�
			String cur_author = cnode.select("li[class=d_name]").first().text();
			//�ж��Ƿ�Ϊ¥��
			boolean is_owner = false;
			if(cur_author.equals(owner))
				is_owner = true;
			//��ȡ����(¥��)����
			String cur_content = "";
			Element content_node = cnode.select("div[class=p_content  p_content p_content_icon_row1 p_content_nameplate],div[class=p_content  p_content p_content_nameplate]").first();
			if(content_node!=null)
			{
				//���������еĸ�ʽ
				cur_content = content_node.html();
				cur_content = cur_content.replaceAll("(\\n|\\r\\n).*?<strong>|</strong>|(\\n|\\r\\n).*?<a.*?>|</a>",""); //ȥ��<strong></strong>��ǩ��<a></a>��ǩ
				cur_content = cur_content.replaceAll("<br>", "\r\n"); //����HTML�еĻ��в���
				cur_content = cur_content.replaceAll("<.*?>",""); //ȥ��HTML��ǩ
				cur_content = cur_content.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", ""); //ȥ������Ŀ���
				cur_content = cur_content.trim(); //ȥ����λ�Ķ���ո�
			}
			//����¥��ʵ��
			Floor cur_floor = new Floor(floor_count, cur_author, is_owner, cur_content);
			//���µ�ǰ���ӵ�����(¥��)�б�
			post.add_floor(cur_floor);
			
			System.out.println("-Floor#: " + floor_count);
			System.out.println("-Author: " + cur_author);
			System.out.println("-Is Owner: " + is_owner);
			System.out.println("-Content: \r\n" + cur_content) ;
			System.out.println();
			++floor_count;
		}
	}
	
	//HTML�ı�Ԥ����ķ���
	private String HTML_process(String URL)
	{
		StringBuffer stringBuffer = new StringBuffer();
		String str = "";
		try
		{
			java.net.URL url = new java.net.URL(URL); 
			//ѡ����ҳ���ַ���(utf-8/gbk/unicode)
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
			String line;
			//���ж�ȡHTML�ı�
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
		//ɾ�������HTML��ǩ
		str = str.replaceAll("(?is)<!DOCTYPE.*?>", "");
		str = str.replaceAll("(?is)<!--.*?-->", "");
		str = str.replaceAll("(?is)<script.*?>.*?</script>", "");
		str = str.replaceAll("(?is)<style.*?>.*?</style>", "");
		
		return str;
	}
	
	//�����ȡ����ʵ��ķ���
	public Post get_post()
	{
		return post;
	}
	
	//����
	/*public static void main(String args[])
	{
		//new Post_Crawler("http://tieba.baidu.com/p/5226092940", "����2017������ڼ�ι�У԰�Ĺ���");
		//new Post_Crawler("http://tieba.baidu.com/p/3055335624", "�������Ρ��������γ���ϸ���ԣ���ѡ·��+�������+ͼƬʵ��");
		//new Post_Crawler("http://tieba.baidu.com/p/5226092940", "����Ŀ��2017�걱�������������Ļ");
		//new Post_Crawler("http://tieba.baidu.com/p/5241305852?fid=4509", "��һ�������﷢���ӣ��ҡ�����9");
		
		Post_Crawler post_crawler = new Post_Crawler("http://tieba.baidu.com/p/3055335624", 
				"�������Ρ��������γ���ϸ���ԣ���ѡ·��+�������+ͼƬʵ��");
		Post cur_post = post_crawler.get_post();
		new Store_Info("PKU", 0, cur_post);
	}*/
}