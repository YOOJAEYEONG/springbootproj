package com.practice.springbootproj.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/")
    public ModelAndView mainCtrl(ModelAndView mv){
        System.out.println("mainController>");

        mv.addObject("boardList", 1);
        mv.setViewName("main");
        return mv;
    }
}
