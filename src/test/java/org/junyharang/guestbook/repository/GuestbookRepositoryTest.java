package org.junyharang.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.junyharang.guestbook.entity.Guestbook;
import org.junyharang.guestbook.entity.QGuestbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTest {

    @Autowired private GuestbookRepository guestbookRepository;

    @Test public void insertDummies() {

        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("제목을 생성합니다!" + i)
                    .content("내용을 생성합니다!" + i)
                    .writer("주니하랑" + i % 10)
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });

    } //insertDummies() 끝

    @Test public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if (result.isPresent()) { // isPresent는 Optional의 객체가 null인지 확인 | reulst가 null이 아니면?
            Guestbook guestbook = result.get();

            guestbook.changeTitle("제목을 변경해 볼게요!");
            guestbook.changeContent("내용을 변경해 볼게요!");

            guestbookRepository.save(guestbook);
        }

    } // updateTest() 끝

    @Test public void testQuery1() {

        PageRequest pageable = PageRequest.of(0, 10, Sort.by("writingNo").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qGuestbook.title.contains(keyword);

        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    } //testQuery1() 끝

    @Test public void testQuery2() {

        PageRequest pageable = PageRequest.of(0, 10, Sort.by("writingNo").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qGuestbook.writingNo.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    } // testQuery2() 끝

} // class 끝