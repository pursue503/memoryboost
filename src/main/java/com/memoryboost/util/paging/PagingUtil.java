package com.memoryboost.util.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingUtil {

    private int totalResult; // 전체 게시물 수
    private int amount = 10; // 한페이지 게시물수
    private int totalPage; // 전체 페이지수
    
    private int page; // 현재페이지
    private int startPage;
    private int endPage;

    private boolean prev,next; // 이전 다음,

//    public void setTotalPage(){ // 전체페이지를 설정함
//        // 나머지는 버림이됨.
//        this.totalPage = this.totalResult / this.amount;
//
//        if(totalResult <= amount) { // 총 게시물 수가 한페이지 게시물 수보다 작거나 같으면
//            this.totalPage++;  // 전체 페이지수는 0 이되므로 1페이지를 보여주기위해 ++
//        }else if(totalResult % amount > 0) { // ex ) 23 % 10 = 나머지가 3 즉 3페이지까지한후 3개를 보여줘야함. 전체페이지 ++
//            this.totalPage++;
//        }
//    } // end setTotalPage;

    public void pageSetting() {
        //Math.ceil = 올림, ex) page가 5면 5/ 10.0 = 0.5 올림해서 1이고 *10 하며 10 // ex2) 15 /10.0 = 1.5 올림 2 -> *10 20
        //여기는 먼저 반올림을 하고 *
        endPage = (int) ((Math.ceil(page / 10.0)) * 10);

        //ex) 20 - 9 = 11
        startPage = endPage - 9;

        // 전체 게시물 85 / 10  = 8.5 올림 = 9  ; 총페이지수
        //여기는 안에서 계산을 하고 반올림 괄호를 잘보자.. 실수..
        totalPage = (int) (Math.ceil((totalResult * 1.0) / amount));
        if(totalPage < endPage) { // end 페이지보다 총페이지수가 작으면 , 기본 1페이지 검색에서 endpage 는 10
            endPage = totalPage;
        }

        prev = startPage >1; // 11페이지부터 이전이생성됨  // true

        next = endPage < totalPage; // 전체페이지가 end 페이지보다 클경우만 true

    }

}
