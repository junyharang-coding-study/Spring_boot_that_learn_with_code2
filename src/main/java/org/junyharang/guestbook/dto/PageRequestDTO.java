package org.junyharang.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder @AllArgsConstructor @Getter @ToString
public class PageRequestDTO {
    
    private int page;
    private int size;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    } // 기본 생성자 끝

    public Pageable getPageable(Sort sort) {

        return PageRequest.of(page -1, size, sort);

    }// getPageable() 끝
    
} // class 끝
