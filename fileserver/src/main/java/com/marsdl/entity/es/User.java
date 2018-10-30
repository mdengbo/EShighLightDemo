package com.marsdl.entity.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 加上了@Document注解之后，默认情况下这个实体中所有的属性都会被建立索引、并且分词
 *
 */
@Document(indexName = "user2_index", type = "user2_type")
public class User {

    @Id
    private Integer userid;

    private String username;

    private String sex;

    private String number;

    private String branch;

    private String idcard;

    private String xingzhengban;

    private String jiguan;

    private String biyezhongxue;

    private String phone1;

    private String phone2;

    private String email;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getXingzhengban() {
        return xingzhengban;
    }

    public void setXingzhengban(String xingzhengban) {
        this.xingzhengban = xingzhengban == null ? null : xingzhengban.trim();
    }

    public String getJiguan() {
        return jiguan;
    }

    public void setJiguan(String jiguan) {
        this.jiguan = jiguan == null ? null : jiguan.trim();
    }

    public String getBiyezhongxue() {
        return biyezhongxue;
    }

    public void setBiyezhongxue(String biyezhongxue) {
        this.biyezhongxue = biyezhongxue == null ? null : biyezhongxue.trim();
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1 == null ? null : phone1.trim();
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2 == null ? null : phone2.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}