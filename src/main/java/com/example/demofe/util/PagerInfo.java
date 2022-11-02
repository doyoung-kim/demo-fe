package com.example.demofe.util;
/**
 * PagerInfo
 */
public class PagerInfo {
    private int page = 1; //default = 1
	private int pageSize = 10; //default = 10;
	private int totalCount = 0;
	private String idx;
	 

	public PagerInfo(){		
	}
	
	
	
	public PagerInfo(int page, int pageSize, int totalCount) {	 
		this.page = page;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	
	public void init(int pageSize, int totalCount){
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	 
	
	
	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartNum() {
		return this.pageSize * (this.page - 1) + 1;		 

	}
	public int getEndNum() {
		return Math.min(this.pageSize * this.page, this.totalCount);
	}
	
	public int getStartNumByMySQL() {		 
		return this.pageSize * (this.page - 1);

	}
	public int getEndNumByMySQL() {
		return this.pageSize;
	}



	public String getIdx() {
		return idx;
	}



	public void setIdx(String idx) {
		this.idx = idx;
	}

    
}