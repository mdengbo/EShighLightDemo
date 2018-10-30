package com.marsdl.controller;

import com.marsdl.service.es.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-21
 */
@Controller
public class HotSongController {

    @Resource
    FilmService filmService;


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(String name) {
        return filmService.search(name);
    }

    @RequestMapping(value="/search/{query}")
    @ResponseBody
    public Object searchQuery(@PathVariable String query) {
        return filmService.search(query,"");
    }
}
