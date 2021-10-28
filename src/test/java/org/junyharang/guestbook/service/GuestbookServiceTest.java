package org.junyharang.guestbook.service;

import org.junit.jupiter.api.Test;
import org.junyharang.guestbook.dto.GuestbookDTO;
import org.junyharang.guestbook.dto.PageRequestDTO;
import org.junyharang.guestbook.dto.PageResponseDTO;
import org.junyharang.guestbook.entity.Guestbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest class GuestbookServiceTest {

    @Autowired private GuestbookService service;

    @Test public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("제목을 작성 해 봅니다!")
                .content("내용을 작성 해 봅니다!")
                .writer("주니하랑")
                .build();

        System.out.println(service.register(guestbookDTO));

    } // testRegister() 끝

    @Test public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                                                        .page(1)
                                                        .size(10)
                                                        .build();

        PageResponseDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("이전 : " + resultDTO.isPrev());
        System.out.println("다음 : " + resultDTO.isNext());
        System.out.println("Total : " + resultDTO.getTotalPage());

        System.out.println("----------------------------------------------------------------------");

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        } // for문 끝

        System.out.println("=======================================================================");
        resultDTO.getPageList().forEach(i ->
                System.out.println(i));

    } // testList() 끝

    @Test public void testSearch() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("titlecontent")
                .keyword("한글")          // 검색 Keyword
                .build();

        PageResponseDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("이전 : " + resultDTO.isPrev());
        System.out.println("다음 : " + resultDTO.isNext());
        System.out.println("통합 : " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------------------------------------");

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        } // for 문 끝

        System.out.println("====================================================================");
        resultDTO.getPageList().forEach(integer -> System.out.println(integer));

    } //testSearch() 끝

} // class 끝