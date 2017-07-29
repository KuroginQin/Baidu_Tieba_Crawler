package com.Store;

import com.Entity.Floor;
import com.Entity.Post;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

//将爬取内容保存至本地
public class Store_Info
{
	//构造方法
	//tiba_name 贴吧名称; post_index 当前帖子序列号; post 帖子实体
	public Store_Info(String tieba_name, int post_index, Post post)
	{
		//将爬取的帖子内容保存至本地
		FileWriter fileWriter=null;
		try
		{
			//确保保存数据文件的路径可用
			String dir_path = ".//data//" + tieba_name; //保存贴吧数据文件夹的相对路径
			File file =new File(dir_path); 
			if (!file.exists()&&!file.isDirectory()) //如果文件夹不存在则创建
			    file.mkdir(); 
			fileWriter= new FileWriter(dir_path + "//" + post_index + ".txt", true); 
			//保存帖子基本信息
			String post_URL = post.get_URL(); //帖子页面URL 
			String post_title = post.get_title(); //帖子标题 
			String owner = post.get_owner(); //楼主昵称
			String store_info = 
					"@URL: " + post_URL + "\r\n" 
					+ "@Title: " + post_title + "\r\n" 
					+ "@Owner: " + owner + "\r\n";
			System.out.println("-Store Base Info-");
			System.out.println(store_info + "\r\n");
			fileWriter.write(store_info+"\r\n");
			fileWriter.flush();
			//保存帖子评论(楼层)内容
			List<Floor> floor_list = post.get_floor_list(); //当前帖子的评论(楼层)列表
			int floor_size = floor_list.size();
			for(int i=0;i<floor_size;i++)
			{
				Floor cur_floor = floor_list.get(i); //当前楼层实体
				int cur_floor_index = cur_floor.get_floor_index(); //当前楼层索引
				String cur_author = cur_floor.get_author(); //当前评论用户
				boolean cur_is_owner = cur_floor.get_is_owner(); //当前用户的楼主标识
				String cur_content = cur_floor.get_content(); //当前评论内容
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