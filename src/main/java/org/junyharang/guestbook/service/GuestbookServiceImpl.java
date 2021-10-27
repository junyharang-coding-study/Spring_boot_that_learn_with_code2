package org.junyharang.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.guestbook.dto.GuestbookDTO;
import org.junyharang.guestbook.dto.PageRequestDTO;
import org.junyharang.guestbook.dto.PageResponseDTO;
import org.junyharang.guestbook.entity.Guestbook;
import org.junyharang.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));

        return new PageResponseDTO<>(result, fn);
    }
} // class 끝
