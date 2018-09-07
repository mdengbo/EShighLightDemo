package com.marsdl.controller;

import com.marsdl.service.es.FilmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-30
 */
@RestController
@RequestMapping(value = "film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @RequestMapping("/search/{query}")
    public Object search(@PathVariable String name) {
        if(StringUtils.isNotBlank(name)) {
            return filmService.search(name, "");
        }
        return null;
    }
}
