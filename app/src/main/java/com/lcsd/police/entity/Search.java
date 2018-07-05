package com.lcsd.police.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */
public class Search {
    public static class TRslist{

        private	String	dfiles;	/*http://jingbao.5kah.com/res/enclosure/2018/ff3e590f78c45f2a.pdf*/
        private	String	cate_id;	/*621*/
        private	String	attr;	/**/
        private	String	dateline;	/*1516346167*/
        private	String	cate_identifier;	/*zbglxgwj*/
        private	String	url;	/*http://jingbao.5kah.com/app/index.php?id=2002&project=build*/
        private	String	id;	/*2002*/
        private	String	title;	/*中华人民共和国人民警察使用警械和武器条例*/
        private	String	hits;	/*1*/
        private	String	source;	/**/
        private	Object	zan;	/*Object*/
        private	Object	is_zan;	/*Object*/
        private	String	writer;	/**/
        private	String	thumb;	/**/
        private	String	note;	/**/
        private	String	video;	/**/
        private	String	cate_name;	/*装备管理相关文件*/

        public void setDfiles(String value){
            this.dfiles = value;
        }
        public String getDfiles(){
            return this.dfiles;
        }

        public void setCate_id(String value){
            this.cate_id = value;
        }
        public String getCate_id(){
            return this.cate_id;
        }

        public void setAttr(String value){
            this.attr = value;
        }
        public String getAttr(){
            return this.attr;
        }

        public void setDateline(String value){
            this.dateline = value;
        }
        public String getDateline(){
            return this.dateline;
        }

        public void setCate_identifier(String value){
            this.cate_identifier = value;
        }
        public String getCate_identifier(){
            return this.cate_identifier;
        }

        public void setUrl(String value){
            this.url = value;
        }
        public String getUrl(){
            return this.url;
        }

        public void setId(String value){
            this.id = value;
        }
        public String getId(){
            return this.id;
        }

        public void setTitle(String value){
            this.title = value;
        }
        public String getTitle(){
            return this.title;
        }

        public void setHits(String value){
            this.hits = value;
        }
        public String getHits(){
            return this.hits;
        }

        public void setSource(String value){
            this.source = value;
        }
        public String getSource(){
            return this.source;
        }

        public void setZan(Object value){
            this.zan = value;
        }
        public Object getZan(){
            return this.zan;
        }

        public void setIs_zan(Object value){
            this.is_zan = value;
        }
        public Object getIs_zan(){
            return this.is_zan;
        }

        public void setWriter(String value){
            this.writer = value;
        }
        public String getWriter(){
            return this.writer;
        }

        public void setThumb(String value){
            this.thumb = value;
        }
        public String getThumb(){
            return this.thumb;
        }

        public void setNote(String value){
            this.note = value;
        }
        public String getNote(){
            return this.note;
        }

        public void setVideo(String value){
            this.video = value;
        }
        public String getVideo(){
            return this.video;
        }

        public void setCate_name(String value){
            this.cate_name = value;
        }
        public String getCate_name(){
            return this.cate_name;
        }

    }
    private	List<TRslist>	rslist;	/*List<TRslist>*/
    public void setRslist(List<TRslist> value){
        this.rslist = value;
    }
    public List<TRslist> getRslist(){
        return this.rslist;
    }

    private	String	total;	/*6*/
    private	Integer	total_page;	/*1*/
    private	String	keywords;	/*中华*/
    private	Integer	psize;	/*10*/
    private	Integer	pageid;	/*1*/

    public void setTotal(String value){
        this.total = value;
    }
    public String getTotal(){
        return this.total;
    }

    public void setTotal_page(Integer value){
        this.total_page = value;
    }
    public Integer getTotal_page(){
        return this.total_page;
    }

    public void setKeywords(String value){
        this.keywords = value;
    }
    public String getKeywords(){
        return this.keywords;
    }

    public void setPsize(Integer value){
        this.psize = value;
    }
    public Integer getPsize(){
        return this.psize;
    }

    public void setPageid(Integer value){
        this.pageid = value;
    }
    public Integer getPageid(){
        return this.pageid;
    }
}
