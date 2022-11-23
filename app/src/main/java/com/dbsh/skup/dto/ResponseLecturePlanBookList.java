package com.dbsh.skup.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturePlanBookList {
	@SerializedName("BOOK_AUTHOR")  // 저자
	@Expose
	private String bookAuthor;

	@SerializedName("BOOK_NAME")    // 교재 명
	@Expose
	private String bookName;

	@SerializedName("BOOK_PUB_CO")  // 출판사
	@Expose
	private String bookPubCo;

	@SerializedName("BOOK_PUB_YEAR")    // 출판년도
	@Expose
	private String bookPubYear;

	@SerializedName("ISBN")         // ISBN
	@Expose
	private String isbn;

	public String getBookAuthor() {
		return bookAuthor;
	}

	public String getBookName() {
		return bookName;
	}

	public String getBookPubCo() {
		return bookPubCo;
	}

	public String getBookPubYear() {
		return bookPubYear;
	}

	public String getIsbn() {
		return isbn;
	}
}
