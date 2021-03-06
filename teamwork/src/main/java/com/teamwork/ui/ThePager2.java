package com.teamwork.ui;

import lombok.Data;

@Data
public class ThePager2 {
	
	private int pageSize;//한 페이지당 데이터 개수
	private int pagerSize;//번호로 보여주는 페이지 Link 개수
	private int dataCount;//총 데이터 수
	
	private int pageNo;//현재 페이지 번호
	private int pageCount;//총 페이지 수
	
	private String linkUrl;//페이저가 포함되는 페이지의 주소
	
	private String queryString;
	
	public ThePager2(int dataCount, int pageNo, 
		int pageSize, int pagerSize, String linkUrl, String queryString) {
		
		this.linkUrl = linkUrl;
		
		this.queryString = "";
		if (queryString != null && queryString.length() > 0) {
			String[] queryList = queryString.split("&");
			for (String query : queryList) {
				if (!query.contains("pageNo=")) {
					if (this.queryString.length() > 0) {
						this.queryString += "&" + query;
					} else { 
						this.queryString += query;
					}
				}
			}
		}
		
		this.dataCount = dataCount;
		this.pageSize = pageSize;
		this.pagerSize = pagerSize;
		this.pageNo = pageNo;		
		pageCount = 
			(dataCount / pageSize) + ((dataCount % pageSize) > 0 ? 1 : 0); 
	}
	
	public String toString(){
		StringBuffer linkString = new StringBuffer(2048);
		
		
		//1. 처음, 이전 항목 만들기
		if (pageNo > 1) {
			linkString.append(
				String.format("<a href='%s?pageNo=1&%s'>첫</a>",linkUrl, queryString));
			linkString.append(String.format(
				"<a href='%s?pageNo=%d&%s'> <i class='ti-angle-left'>←</i> </a>", linkUrl, pageNo - 1, queryString));
		}
		
		//2. 페이지 번호 Link 만들기
		int pagerBlock = (pageNo - 1) / pagerSize;
		int start = (pagerBlock * pagerSize) + 1;
		int end = start + pagerSize;
		for (int i = start; i < end; i++) {
			if (i > pageCount) break;
			if(i == pageNo) {
				linkString.append(String.format("<a> %d </a>", i));
			} else { 
				linkString.append(String.format(
					"<a href='%s?pageNo=%d&%s'>%d</a>", linkUrl, i, queryString, i));
			}
		}		
		
		//3. 다음, 마지막 항목 만들기
		if (pageNo < pageCount) {
			linkString.append(String.format(
				"<a href='%s?pageNo=%d&%s'> <i class='ti-angle-right'>→</i> </a>",linkUrl, pageNo + 1, queryString));
			linkString.append(String.format(
				"<a href='%s?pageNo=%d&%s'>끝</a>", linkUrl, pageCount, queryString));
		}		
		

		return linkString.toString();
	}

}













