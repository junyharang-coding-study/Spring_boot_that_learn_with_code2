package org.junyharang.guestbook.service;

import org.junyharang.guestbook.dto.GuestbookDTO;
import org.junyharang.guestbook.dto.PageRequestDTO;
import org.junyharang.guestbook.dto.PageResponseDTO;
import org.junyharang.guestbook.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO dto);

    // PageRequestDTO를 매개 변수로, PageResponseDTO를 반환값으로 사용하는 Method
    PageResponseDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .writingNo(dto.getWritingNo())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    } // dtoToEntity() 끝

    default GuestbookDTO entityToDTO(Guestbook entity) {

        GuestbookDTO dto = GuestbookDTO.builder()
                .writingNo(entity.getWritingNo())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;

    } // entityToDTO() 끝

    GuestbookDTO read(Long writingNo);

    void modify(GuestbookDTO dto);

    void remove(Long writingNo);
} // interface 끝
