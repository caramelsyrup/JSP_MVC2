package com.jsoup.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test3 {

	public static void main(String[] arg) {
		
		try {
			Document doc = Jsoup.connect("https://m.dhlottery.co.kr/common.do?method=main").get();
			
			Element container = doc.select("div.prizeresult").first();
//			System.out.println(container);
			Elements result = container.select("span");
//			System.out.println(result);
			
			for(int i=0; i<result.size(); i++) {
			 String re = result.get(i).text();
			 System.out.println(re);
			}
			System.out.println("------------------------");
			
			Elements num = container.select("div.num span");
			for(int i=0; i<num.size(); i++) {
				String su = num.get(i).text();
				System.out.println(su);
			}
			System.out.println("------------------------");
			// 로또 당첨 영역
			Elements lotto = doc.select("div.lotto");
			// 로또 당첨 영역 중 회차 만
			Elements lottoRound = lotto.select("h2 strong.round");
			// 로또 당첨 영역 중 번호 만
			Elements lottoNum = lotto.select("div.prizeresult");
			
			for(int i=0; i<lottoRound.size();i++){
				String loround = lottoRound.get(i).text();
				System.out.println(loround);
				String lonum = lottoNum.get(i).select("span").text();
				System.out.println(lonum);
			}
			

			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
