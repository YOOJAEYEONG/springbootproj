package com.practice.springbootproj.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class MainController {

    @RequestMapping(value = "/")
    public ModelAndView mainCtrl(ModelAndView mv){
        log.info("[mainController]");
        mv.addObject("boardList", 1);
        mv.setViewName("views/main");
        return mv;
    }
}
