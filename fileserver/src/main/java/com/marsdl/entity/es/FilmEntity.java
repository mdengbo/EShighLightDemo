package com.marsdl.entity.es;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * <p>description: </p>
 *
 * @author chenrui
 * @since 2018-08-13
 */
@Document(indexName="chenrui", type="film")
public class FilmEntity {

    @Id
    private Long id;
    private String name;
    private String nameOri;
    private String publishDate;
    private String type;
    private String language;
    private String fileDuration;
    private String director;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOri() {
        return nameOri;
    }

    public void setNameOri(String nameOri) {
        this.nameOri = nameOri;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(String fileDuration) {
        this.fileDuration = fileDuration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FilmEntity [id=" + id + ", name=" + name + ", director=" + director + "]";
    }
}
