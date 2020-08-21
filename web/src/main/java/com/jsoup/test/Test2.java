package com.jsoup.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test2 {

	public static void main(String[] args) {
	 try {
		Document doc =	Jsoup.connect("https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q=%EC%86%90%ED%9D%A5%EB%AF%BC").get();
		
		Elements container = doc.select("div.type_thumb_s160 dl");
		Elements title = doc.select("div.type_thumb_s160 dl dt");
		Elements content = doc.select("div.type_thumb_s160 dl dd");
		for(int i=0; i<container.size();i++) {
			String titletxt = title.get(i).text();
			String contenttxt = content.get(i).text();
			System.out.println(titletxt+" : "+contenttxt);
		}
		
		// 10개의 타이틀 정보
		Elements infolist = doc.select("dl.dl_comm");
//		System.out.println(infolist);
		System.out.println("------------------");
		
		// 정보 타이틀
		for(int i=0; i<8;i++) {
		String infolist1 = infolist.get(i).text();
		System.out.println(infolist1);
		}
	 
	 } catch (IOException e) {
		e.printStackTrace();
	}

	}

}

