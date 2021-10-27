package org.junyharang.guestbook.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter public class PageResponseDTO<DTO, ENTITY> {

    //DTO List
    private List<DTO> dtoList;

    // 총 Page 번호
    private int totalPage;

    // 현재 Page 번호
    private int page;

    // 목록 크기
    private int size;

    // 시작 Page 번호, 끝 페이지 번호
    private int start, end;

    // 이전, 다음
    private boolean prev, next;

    // Page 번호 목록
    private List<Integer> pageList;

    public PageResponseDTO(Page<ENTITY> result, Function<ENTITY, DTO> fn) {

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());

    } // 생성자 끝

    private void makePageList(Pageable pageable) {

        // 0부터 시작하기 때문에 1 추가
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        //임시 끝 Page
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    } // makePageList() 끝

} // class 끝
