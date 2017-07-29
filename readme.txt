0. BRIEF DESCRIPTION
A Simple Web Crawler Program to get a certain scale of data from Baidu Tieba, the biggest Chinese Community in the world, which can be seen as an important data source of Social Network Service (SNS). The program is written in Java, and use multi-thread to achieve relatively high efficiency, and the result can be directly used in the experiments of Social Network Analysis, Natural Language Process and so on.
The project file uploaded includes the Source Code (src), the Jar of JSOUP (lib), the Example Result of the Crawler Program (data) which can be directly used as the exeperimental data set (the details of the data can be seen as follow in Chinese).
Because of my limited capacity of programming, it's hard to avoid some errors and deficiencies in the source code. If you find some errors or anything that can be improved, you can contact me via [mengqin_az@foxmail.com].
Thank You Very Much! 

0. 简介
简单的百度贴吧(号称“全球最大中文社区”)网络爬虫,该爬虫用Java实现,并采用了简单的多线程爬取,效率较高。爬取结果可直接用于社交网络分析、自然语言处理等方面的实验。
上传的项目文本包括程序源代码(src),JSOUP开源工具的jar包(lib),以及爬取的示例数据集(data),该数据集可直接使用(数据集说明稍候给出)。
由于作者水平有限,难免有疏漏之处,还望大家批评指正!如有关于源代码、数据集的任何问题,可通过 [mengqin_az@foxmail.com] 邮件联系。
谢谢！

1. HOW TO USE
(1)Import the Project Floder Tieba_Crawler, and the JAR in Tibe_Crawler\lib;

(2)Set the parameters (includes tieba_URL, tieba_name, max_page_num, post_thread_num) of the General Control Program com\Main\Main_Crawler.java;

(3)Run the general control program com\Main\Main_Crawler.java, and the result will be saved in the directory named "data".

1. 使用方法 
(1)导入工程文件夹Tieba_Crawler,重新加载Tieba_Crawler\lib内的jar包;

(2)设置爬虫总控程序 com\Main\Main_Crawler.java 内的参数(tieba_URL, tieba_name, max_page_num, post_thread_num);

(3)运行爬虫总控程序 com\Main\Main_Crawler.java, 爬取的结果保存在data文件夹中。


2. 百度贴吧爬取层级结构说明
(1)将百度贴吧的层次结构从大到小,依次定义为“贴吧”(Tieba)->“帖子”(Post)->“楼”(Floor),每个帖子中,内容的发起人为“楼主”(Owner)。

(2)贴吧的名称一般与地点、组织、人物等相关联,而帖子一般与该地点、组织或围绕该人物发生的事情、举办的活动、热点问题等相关联,“楼”实际上是用户发表的评论,如 
    北京的大学吧(http://tieba.baidu.com/f?kw=北京大学)
    ->标题为“【醒目】2017年北大新生吧友征文活动”的帖子(http://tieba.baidu.com/p/5226092940)
    ->1楼中,楼主发布的内容““未名畔掩映芳华，寰宇间承载万象”……”
百度贴吧的网页主要分为“贴吧网页”(Tieba Page)和“帖子网页”(Post Page);
其中,贴吧的主要内容为“帖子列表”,可从贴吧网页中提取出相关帖子的URL列表,提拔网页每页最多显示50个帖子,而在帖子页面中则直接显示相关评论,即楼层的内容。

(3)楼层的计数方法为按照发布时间从小到大计数,新发布的评论会追加到已有评论的后面,帖子页面中的每一页最多可显示30条评论,即每页30楼(中间可能穿插广告,即一页可能不足30楼);某帖子页面第1页的第1条评论可定义为1楼,而第1页的第2条评论定义为2楼,第1也第3条评论定义为3楼,以此类推。

(4)默认在1楼发表评论的用户为“楼主”,为帖子内容的发起者;楼主也可在其他楼层内发表评论,而无论是否在1楼发表评论,楼主的图表都会有表明“楼主”身份的标签;除楼主外的其他用户可在1楼外的其他楼层发表评论;在同一楼层内,任何用户还可对该楼的内容发表评论,相关的评论内容会在同一楼层内显示。


2. 源代码说明
(1)爬虫程序源代码在src文件夹中,各模块定义如下:
com\Entity 表示百度贴吧实体的类;
com\Process 爬虫处理逻辑、提取网页内容的程序;
com\MThread 实现多线程爬取的程序;
com\Store 将爬取的内容保存至本地的程序;
com\Main 爬虫总控程序。

(2)各源代码文件说明如下:
com\Entity\Post.java 表示帖子实体的类;
com\Entity\Floor.java 表示评论(楼层)实体的类;
com\Process\Tieba_Crawler.java 爬取贴吧网页,获取相关帖子URL列表的程序;   
com\Process\Post_Crawler.java 爬取帖子内容,获取评论(楼层)内容的程序;
com\MThread\Post_Crawler_Thread.java 实现并行爬取帖子页面、保存内容的线程类;
com\Store\Store_Info.java 将爬取内容保存至本地的程序;
com\Main\Main_Crawler.java 爬虫总控程序。


3. 爬取结果数据文件说明
(1)规定每个贴吧数据保存在一个文件夹内,如 北京大学吧 保存在 PKU 文件夹内;

(2)每个帖子页面保存在一个txt文本中,根据爬取顺序,以数字命名,如 北京大学吧 第1个爬取的帖子保存在 PKU\1.txt中,而第2个爬取的帖子保存在 PKU\2.txt中,以此类推;

(3)每个帖子数据文件的格式定义如下:
@URL: [帖子页面URL]
@Title: [帖子标题]
@Owner: [楼主昵称]
[空一行]
@@Floor#: [当前楼层索引]
@@Author: [用户昵称]
@@Is_Owner: [{true, false} 是否为楼主,True表示是,False表示否]
@@Content
“评论内容”
@@Content_End
[空一行]
@@Floor#:
@@Author:
@@Is_Owner:
@@Content
…
@@Content_End
[空一行]
…

(4)爬取是示例数据集包括:
1)北京大学吧 (PKU),贴吧页面前20页,共908个帖子
2)清华大学吧 (THU),贴吧页面前20页,共935个帖子
3)天津大学吧 (TJU),贴吧页面前20页,共985个帖子
4)南开大学吧 (NKU),贴吧页面前20页,共937个帖子
5)上海交通大学吧 (SJTU),贴吧页面前20页,共971个帖子
6)复旦大学吧 (FDU),贴吧页面前20页,共931个帖子


4. 注意事项
(1)本爬虫程序没有涉及爬取一个楼层内的回复(Reply)内容!
实际上,“回复”内容隐藏在js代码中,使用Jsoup获取HTML文本,并设定休眠时间10秒以上,最后将HTML页面中的"\u"开头的Unicode码转化为中文,即可在js代码中找到“回复”内容;“回复”内容还涉及到用户id之间的匹配,为保证爬取效率,爬虫程序一开始就不考虑js代码,故不能再进一步地爬取完整的“回复”内容。

(2)一些帖子的内容可能存在大量与“主题”无关的楼层,即“水贴”(一些贴吧可是专门为“水贴”创建的,如 示例数据集 (data) 中 清华大学吧 (THU) 的3.txt, “【吧务组】清华大学吧官方水塔”)；这实际上是符合社交网络数据特点的,即需要承认与“主题”无关的内容信息是大量存在的。
