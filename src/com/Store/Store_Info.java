package com.Store;

import com.Entity.Floor;
import com.Entity.Post;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

//����ȡ���ݱ���������
public class Store_Info
{
	//���췽��
	//tiba_name ��������; post_index ��ǰ�������к�; post ����ʵ��
	public Store_Info(String tieba_name, int post_index, Post post)
	{
		//����ȡ���������ݱ���������
		FileWriter fileWriter=null;
		try
		{
			//ȷ�����������ļ���·������
			String dir_path = ".//data//" + tieba_name; //�������������ļ��е����·��
			File file =new File(dir_path); 
			if (!file.exists()&&!file.isDirectory()) //����ļ��в������򴴽�
			    file.mkdir(); 
			fileWriter= new FileWriter(dir_path + "//" + post_index + ".txt", true); 
			//�������ӻ�����Ϣ
			String post_URL = post.get_URL(); //����ҳ��URL 
			String post_title = post.get_title(); //���ӱ��� 
			String owner = post.get_owner(); //¥���ǳ�
			String store_info = 
					"@URL: " + post_URL + "\r\n" 
					+ "@Title: " + post_title + "\r\n" 
					+ "@Owner: " + owner + "\r\n";
			System.out.println("-Store Base Info-");
			System.out.println(store_info + "\r\n");
			fileWriter.write(store_info+"\r\n");
			fileWriter.flush();
			//������������(¥��)����
			List<Floor> floor_list = post.get_floor_list(); //��ǰ���ӵ�����(¥��)�б�
			int floor_size = floor_list.size();
			for(int i=0;i<floor_size;i++)
			{
				Floor cur_floor = floor_list.get(i); //��ǰ¥��ʵ��
				int cur_floor_index = cur_floor.get_floor_index(); //��ǰ¥������
				String cur_author = cur_floor.get_author(); //��ǰ�����û�
				boolean cur_is_owner = cur_floor.get_is_owner(); //��ǰ�û���¥����ʶ
				String cur_content = cur_floor.get_content(); //��ǰ��������
				store_info = 
						"@@Floor#: " + cur_floor_index + "\r\n" 
						+ "@@Author: " + cur_author + "\r\n"
						+ "@@Is_Owner: " + cur_is_owner + "\r\n"
						+ "@@Content" + "\r\n" + cur_content + "\r\n@@Content_End\r\n" ;
				System.out.println("-Store Floor Info-");
				System.out.println(store_info + "\r\n");
				fileWriter.write(store_info+"\r\n");
				fileWriter.flush();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fileWriter.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}