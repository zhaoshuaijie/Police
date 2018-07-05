package com.lcsd.police.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
public class LeaveMessage {
    public static class TRslist {

        private String cate_url;	/**/
        private String cate_id;	/*0*/
        private String attr;	/**/
        private String dateline;	/*1514952266*/
        private String url;	/*http://jingbao.5kah.com/app/index.php?id=1895*/
        private String id;	/*1895*/
        private String title;	/*测试*/
        private String hits;	/*3*/
        private Object source;	/*Object*/
        private Object writer;	/*Object*/
        private String thumb;	/**/
        private Object note;	/*Object*/
        private String video;	/**/
        private String cate_name;	/**/

        public void setCate_url(String value) {
            this.cate_url = value;
        }

        public String getCate_url() {
            return this.cate_url;
        }

        public void setCate_id(String value) {
            this.cate_id = value;
        }

        public String getCate_id() {
            return this.cate_id;
        }

        public void setAttr(String value) {
            this.attr = value;
        }

        public String getAttr() {
            return this.attr;
        }

        public void setDateline(String value) {
            this.dateline = value;
        }

        public String getDateline() {
            return this.dateline;
        }

        public void setUrl(String value) {
            this.url = value;
        }

        public String getUrl() {
            return this.url;
        }

        public void setId(String value) {
            this.id = value;
        }

        public String getId() {
            return this.id;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getTitle() {
            return this.title;
        }

        public void setHits(String value) {
            this.hits = value;
        }

        public String getHits() {
            return this.hits;
        }

        public void setSource(Object value) {
            this.source = value;
        }

        public Object getSource() {
            return this.source;
        }

        public void setWriter(Object value) {
            this.writer = value;
        }

        public Object getWriter() {
            return this.writer;
        }

        public void setThumb(String value) {
            this.thumb = value;
        }

        public String getThumb() {
            return this.thumb;
        }

        public void setNote(Object value) {
            this.note = value;
        }

        public Object getNote() {
            return this.note;
        }

        public void setVideo(String value) {
            this.video = value;
        }

        public String getVideo() {
            return this.video;
        }

        public void setCate_name(String value) {
            this.cate_name = value;
        }

        public String getCate_name() {
            return this.cate_name;
        }

    }

    private List<TRslist> rslist;	/*List<TRslist>*/

    public void setRslist(List<TRslist> value) {
        this.rslist = value;
    }

    public List<TRslist> getRslist() {
        return this.rslist;
    }

    private Integer total;	/*1*/
    private Integer psize;	/*10*/
    private Integer pageid;	/*1*/
    private String psizeNum;
    public void setTotal(Integer value) {
        this.total = value;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setPsize(Integer value) {
        this.psize = value;
    }

    public Integer getPsize() {
        return this.psize;
    }

    public void setPageid(Integer value) {
        this.pageid = value;
    }

    public Integer getPageid() {
        return this.pageid;
    }
    public String getPsizeNum() {
        return psizeNum;
    }

    public void setPsizeNum(String psizeNum) {
        this.psizeNum = psizeNum;
    }
}
