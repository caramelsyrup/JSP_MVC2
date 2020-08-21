package com.jsoup.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test4 {

	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("https://search.naver.com/search.naver?sm=top_hty&fbm=0&ie=utf8&query=%ED%99%98%EC%9C%A8").get();
			
			Elements table = doc.select("table.rate_table_info");
//			System.out.println(table);
			Elements tr = table.select("tbody tr");
			
			for(int i=0; i<tr.size(); i++) {
				String title = tr.get(i).select("th a").text();
				String price = tr.get(i).select("td").first().text();
				System.out.println(title+" : "+price);
			}
			System.out.println("-----------------------------");
			
			Elements thead = table.select("thead tr th span");
//			System.out.println(thead);
			Elements tbody1 = table.select("tbody tr th span");
			Elements tbody2 = table.select("tbody tr td span");
			String nation = tbody1.first().text();
			String won = tbody2.first().text();
			System.out.println(nation+" : "+won+" ¿ø");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
