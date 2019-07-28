package com.example.previewproject.entity;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YwYjtp implements Serializable {


    private int id;
    private int zdid;
    private String pname;
    private String pdescribe;
    private String path;
    private String creater;
    private Date createtime;
    private String bz;
    private String sname;
    private String creatTimeStr;//格式化时间的字符串，方便页面显示


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getCreatTimeStr() {
        return creatTimeStr;
    }

    public void setCreatTimeStr(String creatTimeStr) {
        this.creatTimeStr = creatTimeStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZdid() {
        return zdid;
    }

    public void setZdid(int zdid) {
        this.zdid = zdid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescribe() {
        return pdescribe;
    }

    public void setPdescribe(String pdescribe) {
        this.pdescribe = pdescribe;
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
        String time = dateFormat.format(createtime);
        setCreatTimeStr(time);
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
