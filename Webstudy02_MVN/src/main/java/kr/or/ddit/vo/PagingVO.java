package kr.or.ddit.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 페이징과 관련된 모든 데이터를 가진 객체
 * setter는 2개만 나머지는 2개의 setter로 연산으로 처리함 
 */
@Getter
@NoArgsConstructor
@ToString
public class PagingVO<T> {
	
	
	public PagingVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}


	private int totalRecord; //DB 조회
	private int screenSize=10; //임의 설정 
	private int blockSize=5; //임의설정
	
	private int currentPage; //클라이언트 파라미터로 결정 -> 
	
	//스크린사이즈 
	private int totalPage; //totalRecord, screenSize 로 결정
	private int startRow; //screenSize  결정
	private int endRow;
	private int startPage;//currentPage
	private int endPage;
	
	
	//페이징 처리의 각 페이지 마다 다름 
	private List<T> dataList;
		
	
	
	//단순검색 -> 검색조건 1개 
	private SearchVO simpleCondition;
	public void setSimpleCondition(SearchVO simpleCondition) {
		this.simpleCondition = simpleCondition;
	}
	
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	
	//토탈페이지, 연산으로 결정됨 
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		//totalRecord 연산식 109 + 9 /10 
		totalPage = (totalRecord + (screenSize -1))/screenSize;
		
	}
	//setter가 호출되는 시점 ! 한번은 반드시 호출되어야함
	//totalPage빼고 나머지 다 결정 연산되는 시점?  
	//쿼런트페이지가 vo로 들어오려면? setter 호출해줘야햄
	//여기서 연산 
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage * screenSize;
		startRow = endRow - (screenSize-1);
		
		endPage = ((currentPage+(blockSize-1))/blockSize)*blockSize;
		startPage =endPage - (blockSize-1); 
		
	}
	
	private final String APATTERN = "<a class='paging' href='#' data-page='%d'>%s<a>";
	
	
	public String getPagingHTML() {
		
		StringBuffer html = new StringBuffer();
		
		
		//이전 으로 눌렀을때 
		if(startPage > blockSize) {
			html.append(
					String.format(APATTERN, startPage-blockSize, "이전")
					
			);
		}
		//end페이지 보장 출력내용이 4까지만 있을때  end: 5 total 4 일때 반복문은
		endPage = endPage > totalPage ? totalPage : endPage;
		
		for (int page = startPage; page <= endPage; page++) {
			
			if(page == currentPage) {
				html.append(
						"<a href='#'>"+page+"</a>"
			
				);
			}else {
				html.append(
						String.format(APATTERN, page, page+"")
				);
				
			}
		}
		//다음 : 지금보이는 마지막페이지(endPage) totalPage에 의해 결정됨
		if(endPage<totalPage) {
			html.append(
					String.format(APATTERN, endPage+1, "다음")
			);
		}
		
		return html.toString();
	}
}

//처음 vo객체를 만들때 .screenSizeblockSize






