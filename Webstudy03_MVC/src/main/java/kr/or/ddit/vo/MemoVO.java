package kr.or.ddit.vo;

import java.io.Serializable;


//어떤 한객체의 직렬화를 하려면? 모든 속성도 직렬화가 가능해야 한다 
//transient 직렬화가 불가능한걸 가능하게 해줌 
public class MemoVO implements Serializable{
	
	private Integer code;//모든쪽지는 코드값이 있다
	private String writer;
	private String content;
	private String date;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "MemoVO [code=" + code + ", writer=" + writer + ", content=" + content + ", date=" + date + "]";
	}
}
