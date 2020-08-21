package com.jsoup.test;



import java.io.IOException;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test1 {
	
	
	public static void main(String[] arg) {
		// get은 리턴이 document이다.
		try {
			// 해당 주소의 문서를 다 긁어 온다.
			Document doc = Jsoup.connect("https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q=%EC%98%81%ED%99%94").get();
			
			// 문서의 내용 중에서 선택.
			Elements movie_list = doc.select("ol.movie_list li");
			// 문서의 내용 중에서 특정 테그의 특정 클래스의 a태그의 내용들을 전부 선택
			Elements titleContainer = movie_list.select("div.info_tit a");
			// 텍스트로 쓰여진 제목만 긁어 오려면, 전부 선택된 내용중에서 제목만 긁어올수 있도록.
			for(int i=0;i<titleContainer.size();i++) {
				System.out.println(titleContainer.get(i).text());
			}
			System.out.println("-----------------------------------");
			Elements score = movie_list.select("em.rate");
			ArrayList<Movie> arr = new ArrayList<Movie>();
			
			for(int i=0;i<score.size();i++) {
				Movie movie = new Movie();
				String title = titleContainer.get(i).text();
				String rate = score.get(i).text();
				movie.setTitle(title);
				movie.setRate(rate);
				arr.add(movie);
			}
//			System.out.println(titleContainer);
			for(Movie movie : arr) {
				System.out.println("제목:"+movie.getTitle());
				System.out.println("평점:"+movie.getRate());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
