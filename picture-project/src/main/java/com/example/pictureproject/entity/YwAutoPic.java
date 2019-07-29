package com.example.pictureproject.entity;


import java.io.Serializable;
import java.util.Date;

public class YwAutoPic implements Serializable {

    private int id;
    private String zdid;
    private String ptitle;
    private String path;
    private String creater;
    private Date createtime;
    private String bz;

    private String sname;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZdid() {
        return zdid;
    }

    public void setZdid(String zdid) {
        this.zdid = zdid;
    }


    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

}
