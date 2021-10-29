package org.junyharang.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junyharang.guestbook.dto.GuestbookDTO;
import org.junyharang.guestbook.dto.PageRequestDTO;
import org.junyharang.guestbook.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/register") public void register() {

        log.info("regiset()의 get이 작동 중 입니다!");

    } // register() 끝

    @PostMapping("/register") public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {

        log.info("register Post가 작동 중입니다! \n dto : " + dto );


        // 새로 추가된 Entity Number
        Long writingNo = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", writingNo);

        return "redirect:/guestbook/list";

    } // registerPost() 끝

    @GetMapping({"/read", "/modify"}) public void read(long writingNo, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("글 번호 : " + writingNo);

        GuestbookDTO dto = service.read(writingNo);

        model.addAttribute("dto", dto);
    } // read() 끝

    @PostMapping("/modify") public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {

        log.info("Controller modify가 동작 중 입니다!");
        log.info("dto" + dto);

        service.modify(dto);

        redirectAttributes.addFlashAttribute("page", requestDTO.getPage());
        redirectAttributes.addFlashAttribute("type", requestDTO.getType());
        redirectAttributes.addFlashAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addFlashAttribute("writingNo", dto.getWritingNo());

        return "redirect:/guestbook/read";

    } // modify() 끝
    
    @PostMapping("/remove") public String remove(long writingNo, RedirectAttributes redirectAttributes) {

        log.info("글번호 : " + writingNo);
        
        service.remove(writingNo);
        
        redirectAttributes.addFlashAttribute("msg", writingNo);
        
        return "redirect:/guestbook/list";

    } // remove() 끝

} // class 끝
