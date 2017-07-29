package com.Main;

import com.Process.Tieba_Crawler;
import com.MThread.Post_Crawler_Thread;

import java.util.List;
import java.util.ArrayList;

//�ٶ���������
public class Main_Crawler
{
	//���췽��
	//tieba_URL ����ҳ��URLl; tieba_name ��������; max_tieba_page_num ��ȡ���������ҳ����; ��ȡ����ҳ����߳���
	public Main_Crawler(String tieba_URL, String tieba_name, int max_tieba_page_num, int post_thread_num)
	{
		//��ȡ����ҳ��
		Tieba_Crawler tieba_crawler = new Tieba_Crawler(tieba_URL, max_tieba_page_num);
		//��ȡ����ҳ�������Ϣ
		List<String> post_title_list = tieba_crawler.get_post_title_list(); //���ӱ����б�
		List<String> post_URL_list = tieba_crawler.get_post_URL_list(); //����ҳ��URL�б�
		//���������б�,��ȡ����ҳ��
		int list_len = post_title_list.size(); //�б���
		//�����б��Ⱥ��߳���,������ȡ����ҳ��
		int index=0;
		while(index==0 || (index<list_len && Thread.activeCount()>1)) 
		{
			if(Thread.activeCount()<post_thread_num+1) //��ǰ�߳���û�дﵽ����߳���ʱ,�����߳���ȡ����ҳ��
			{
				System.out.println("-Current Active Thread: " + (Thread.activeCount()-1));
				String cur_post_title = post_title_list.get(index); //��ǰ���ӱ���
				String cur_post_URL= post_URL_list.get(index); //��ǰ����URL
				Post_Crawler_Thread cur_post_thread = new Post_Crawler_Thread(tieba_name, index, cur_post_title, cur_post_URL); //��ǰ�����߳�
				new Thread(cur_post_thread).start();
				++index;
			}
		}
	}
	
	public static void main(String args[])
	{
		//tieba_URL ����ҳ��URL; tiebe_name ��������; max_page_num ��ȡ���������ҳ����; post_thread_num ��������߳���
		//String tieba_URL = "http://tieba.baidu.com/f?kw=������ѧ";
		//String tieba_name = "PKU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=�廪��ѧ";
		//String tieba_name = "THU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=����ѧ";
		//String tieba_name = "TJU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=�Ͽ���ѧ";
		//String tieba_name = "NKU";
		
		//String tieba_URL = "http://tieba.baidu.com/f?kw=�Ϻ���ͨ��ѧ";
		//String tieba_name = "SJTU";
		
		String tieba_URL = "http://tieba.baidu.com/f?kw=������ѧ";
		String tieba_name = "FDU";
		
		int max_page_num = 20;
		int post_thread_num = 20;
		new Main_Crawler(tieba_URL, tieba_name, max_page_num, post_thread_num);
	}
}