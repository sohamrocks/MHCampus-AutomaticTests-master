package com.mcgraw.test.automation.ui.mhcampus.course.connect;

public class BookForConnect {

	private String bookId;
	private String bookNameLong;
	private String bookName;
	private String category;
	
	
	public BookForConnect(String bookId, String bookNameLong, String bookName, String category) {
		super();
		this.bookId = bookId;
		this.bookNameLong = bookNameLong;
		this.bookName = bookName;
		this.category = category;
	}
	
	public String getBookId() {
		return bookId;
	}
	
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	public String getBookNameLong() {
		return bookNameLong;
	}
	
	public void setBookNameLong(String bookNameLong) {
		this.bookNameLong = bookNameLong;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}	
	
}
