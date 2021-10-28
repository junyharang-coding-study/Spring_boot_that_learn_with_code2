package org.junyharang.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class GuestbookDTO {

    private Long writingNo;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;

} // class 끝
