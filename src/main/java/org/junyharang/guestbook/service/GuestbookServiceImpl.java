package org.junyharang.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.guestbook.dto.GuestbookDTO;
import org.junyharang.guestbook.dto.PageRequestDTO;
import org.junyharang.guestbook.dto.PageResponseDTO;
import org.junyharang.guestbook.entity.Guestbook;
import org.junyharang.guestbook.entity.QGuestbook;
import org.junyharang.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Log4j2 @Service @RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO를 활용한 GuestbookService가 동작합니다!");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        guestbookRepository.save(entity);

        return entity.getWritingNo();
    } // register() 끝

    @Override
    public PageResponseDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("writingNo").descending());

        // 검색 조건 처리
        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        // Querydsl 이용
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));

        return new PageResponseDTO<>(result, fn);
    } // getList() 끝

    @Override
    public GuestbookDTO read(Long writingNo) {

        Optional<Guestbook> result = guestbookRepository.findById(writingNo);

        return result.isPresent() ? entityToDTO(result.get()) : null;
    } // read() 끝

    @Override
    public void modify(GuestbookDTO dto) {

        // 제목과 내용 수정 가능
        Optional<Guestbook> result = guestbookRepository.findById(dto.getWritingNo());

        if (result.isPresent()) { //result (수정 객체)가 NULL이 아니라면?
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            guestbookRepository.save(entity);
        } // if문 끝

    } // modify() 끝

    @Override
    public void remove(Long writingNo) {

        guestbookRepository.deleteById(writingNo);

    } // remove() 끝

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) { // Querydsl 처리

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.writingNo.gt(0L);     // 글번호가 0보다 큰 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { // 검색 조건이 없다면?
            return booleanBuilder;
        } // if 끝

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("title")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        } // 제목 검색 if문 끝

        if (type.contains("content")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        } // 내용 검색 if문 끝

        if (type.contains("writer")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        } // 작성자 검색 if문 끝

        // 모든 조건 합치기
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    } // getSearch() 끝
} // class 끝
