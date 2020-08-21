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
		// get�� ������ document�̴�.
		try {
			// �ش� �ּ��� ������ �� �ܾ� �´�.
			Document doc = Jsoup.connect("https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q=%EC%98%81%ED%99%94").get();
			
			// ������ ���� �߿��� ����.
			Elements movie_list = doc.select("ol.movie_list li");
			// ������ ���� �߿��� Ư�� �ױ��� Ư�� Ŭ������ a�±��� ������� ���� ����
			Elements titleContainer = movie_list.select("div.info_tit a");
			// �ؽ�Ʈ�� ������ ���� �ܾ� ������, ���� ���õ� �����߿��� ���� �ܾ�ü� �ֵ���.
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
				System.out.println("����:"+movie.getTitle());
				System.out.println("����:"+movie.getRate());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
