package org.junyharang.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.guestbook.dto.PageRequestDTO;
import org.junyharang.guestbook.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2 @Controller @RequestMapping("/guestbook")
@RequiredArgsConstructor public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/") public String index() {
        return "redirect:/guestbook/list";
    } // index() 끝

    @GetMapping("/list") public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list()가 동작 중 입니다!" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    } // list() 끝

} // class 끝
