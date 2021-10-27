package org.junyharang.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Builder @AllArgsConstructor @NoArgsConstructor @ToString
@Entity public class Guestbook extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writingNo;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void changeTitle(String title) {
        this.title = title;
    } // changeTitle() 끝

    public void changeContent(String content) {
        this.content = content;
    } // changeContent() 끝

} // class 끝
