package org.junyharang.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder @NoArgsConstructor @AllArgsConstructor @Getter @ToString
public class GuestbookDTO {

    private Long writingNo;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modeDate;

} // class 끝
