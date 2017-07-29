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

//��ȡҳ��,��ȡ��������URL�б�
public class Tieba_Crawler
{
	private List<String> post_URL_list = new ArrayList<String>(); //����ҳ��URL�б�
	private List<String> post_title_list =  new ArrayList<String>(); //���ӱ����б�
	private int post_count = 0; //�������������� 
	
	private static int max_sleep_time = 500; //����������ʱ��
	
	//���췽��
	//Tieba_URL ��ȡ������ҳ��URL; max_page_num ��ȡ�����ҳ����
	public Tieba_Crawler(String Tieba_URL, int max_page_num)
	{
		//����ҳ�����кŹ���URL,����ȡ��ǰҳ��URL�б�
		System.out.println("-Get Post URL List-");
		for(int i=0;i<max_page_num;i++)
		{
			int pn = i*50;
			String cur_URL = Tieba_URL + "&&pn=" + pn;
			//��ȡ��ǰҳ�������URL�б�
			get_cur_post_URL_list(cur_URL);
			System.out.println("-Page#: " + (i+1));
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
		System.out.println("-Total Post: " + post_count);
	}
	
	//��ȡ��ǰҳ�������URL�б�ķ���
	private void get_cur_post_URL_list(String URL)
	{
		//��ȡ��ǰҳ���HTML�ı�
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
		//ʹ��Jsoup��ȡ�ض�����
		Document doc = Jsoup.parse(str);
		Elements nodes = doc.select("div[class=t_con cleafix]");
		for(Element cnode : nodes) 
		{
			//��ȡ���ӱ���
			Element info_node = cnode.select("div[class=threadlist_title pull_left j_th_tit ]").first();
			if(info_node==null)
				continue;
			String cur_post_title = info_node.text();
			
			//ʹ��������ʽ��ȡURL
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
	
	//��ȡ���ӱ����б�ķ���
	public List<String> get_post_title_list()
	{
		return post_title_list;
	}
	
	//��ȡ����ҳ��URL�б�ķ���
	public List<String> get_post_URL_list()
	{
		return post_URL_list;
	}
	
	//����
	/*public static void main(String args[])
	{
		new Tieba_Crawler("http://tieba.baidu.com/f?kw=������ѧ", 10);
	}*/
}