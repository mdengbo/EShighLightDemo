package com.marsdl.controller;

import com.marsdl.dao.FileDao;
import com.marsdl.entity.File;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-09-07
 */
@Controller
public class FileController {

    @Autowired
    FileDao fileDao;

    /**
     * 上传接口
     *
     * @return
     */
    @RequestMapping(value="/upload")
    public Object handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("************");
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            fileDao.save(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }


    /**
     * 获取文件片信息
     *
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("files/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {

        //Optional<File> file = fileService.getFileById(id);
        System.out.println("******************"+id+"******************");
        File file = fileDao.find(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.getName().getBytes("utf-8"),"ISO-8859-1"))
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "").header("Connection", "close")
                .body(file.getContent().getData());
    }

    @RequestMapping(value = "RequestMapping")
    @ResponseBody
    public void test() {
        System.out.println("***************");
    }
}
