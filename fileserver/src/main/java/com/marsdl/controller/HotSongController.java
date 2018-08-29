package com.marsdl.controller;

import com.marsdl.service.es.FilmService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-21
 */
@RestController
public class HotSongController {

    @Resource
    FilmService filmService;

    @RequestMapping(value="/search")
    public Object search(String name) {
        return filmService.search(name);
    }

    @RequestMapping(value="/search/{query}")
    public Object searchQuery(@PathVariable String query) {
        return filmService.search(query,"");
    }
}
