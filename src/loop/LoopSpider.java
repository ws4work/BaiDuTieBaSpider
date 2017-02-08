package loop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.CharTransUtil;

public class LoopSpider {

	public static final String				CONNECT_URL			= "http://tieba.baidu.com";
	public static final String				CONNECT_PACKAGE		= CharTransUtil.charTransMethod("/f?kw=java");
	public static String					TITLE_SEARCH_KEY	= "";
	public static int						pageNum				= 20;
	public static List<Map<String,String>>	srcList				= new ArrayList<Map<String,String>>();

	public static void main(String[] args) throws IOException {
		mainPageMethod(CONNECT_URL + CONNECT_PACKAGE);
		//		System.out.println(srcList.size());
		for (int i = 0; i < srcList.size(); i++) {
			Map<String,String> map = srcList.get(i);
			String src = map.get("src");
			//childLoop(src);
			System.out.println(map.get("title") + "                 " + map.get("src"));
		}
	}

	/**
	 * 主页面循环
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void mainPageMethod(String url) throws IOException {
		pageNum--;
		Element body = getBody(url);
		Elements pager = body.getElementsByClass("next pagination-item ");
		if (pageNum > 0) {
			mainPageMethod(pager.get(0).attr("href"));
		}
		//System.out.println(pager.toString());
		//根据元素标签查找元素
		//Elements aTag = content.get(0).getElementsByTag("a");
		//根据Class属性查找元素
		Elements content = body.getElementsByClass("content");
		if (content.size() > 0) {
			Elements classTag = body.getElementsByClass("j_th_tit ");
			for (int i = 0; i < classTag.size(); i++) {
				Element srcTag = classTag.get(i);
				String title = srcTag.attr("title");
				String src = CONNECT_URL + srcTag.attr("href");
				//childLoop(src);
				//判断是否有关键字
				if (TITLE_SEARCH_KEY != "") {
					//判断是否包含查找关键字
					if (title.contains(TITLE_SEARCH_KEY)) {
						getTitleData(title, src);
					}
				} else {//无关键字方法
					getTitleData(title, src);
				}
			}
		}
	}

	/**
	 * 帖子页面
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void childLoop(String url) throws IOException {
		System.out.println(url);
		Element body = getBody(url);
		Elements divs = body.getElementsByClass("p_postlist");
		Elements div = divs.get(0).getElementsByAttribute("data-field");
		//System.out.println(div.toString());
		System.out.println(div.size());
		for (int i = 5; i < 6; i++) {
			//每层层主
			Elements users = div.get(i).getElementsByClass("p_author_name j_user_card");
			System.out.println("发送人:" + users.get(0).text().toString());
			//每层层主消息
			Elements texts = div.get(i).getElementsByClass("d_post_content_main");
			//System.out.println(texts);
			if (texts.size() > 0) {
				System.out.println("内容:" + texts.get(0).text().toString());
				//每层评论内容
				Elements discuss = texts.get(0).getElementsByClass("d_post_content j_d_post_content  clearfix");
				if (discuss.size() > 0) {
					System.out.println("评论:" + discuss.text().toString());
				}
			}
		}
	}

	/**
	 * 获取页面body
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Element getBody(String url) throws IOException {
		Connection connect = Jsoup.connect(url);
		connect.userAgent("Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		Document document = connect.get();
		//获取页面的head
		//System.out.println(document.head());
		//获取页面的body
		Element body = document.body();
		return body;
	}

	/**
	 * 记录标题和链接地址
	 * 
	 * @param title
	 * @param src
	 */
	public static void getTitleData(String title, String src) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", title);
		map.put("src", src);
		//System.out.println("title:" + title + "           src:" + src);
		srcList.add(map);
	}
}
