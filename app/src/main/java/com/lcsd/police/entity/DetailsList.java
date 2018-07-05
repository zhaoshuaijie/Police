package com.lcsd.police.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
public class DetailsList {
    public static class TRslist{

        private	String	dfiles;	/*http://jingbao.5kah.com/res/enclosure/2018/7852c64348d99c1b.pdf*/
        private	String	cate_url;	/*http://jingbao.5kah.com/app/index.php?id=basis&cate=zhengcewenjian*/
        private	String	cate_id;	/*617*/
        private	String	attr;	/**/
        private	String	dateline;	/*1515138371*/
        private	String	url;	/*http://jingbao.5kah.com/app/index.php?id=1929*/
        private	String	id;	/*1929*/
        private	String	title;	/*阿萨斯*/
        private	String	hits;	/*58*/
        private	String	source;	/**/
        private	String	writer;	/**/
        private	String	thumb;	/**/
        private	String	note;	/*萨斯 */
        private	String	video;	/**/
        private	String	cate_name;	/*政策文件*/

        public void setDfiles(String value){
            this.dfiles = value;
        }
        public String getDfiles(){
            return this.dfiles;
        }

        public void setCate_url(String value){
            this.cate_url = value;
        }
        public String getCate_url(){
            return this.cate_url;
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

    public static class TCate{

        private	String	id;	/*617*/
        private	String	title;	/*政策文件*/
        private	String	url;	/*http://jingbao.5kah.com/app/index.php?id=basis&cate=zhengcewenjian&pageid=1&psize=10*/

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

        public void setUrl(String value){
            this.url = value;
        }
        public String getUrl(){
            return this.url;
        }

    }
    private	Integer	total;	/*2*/
    private	TCate	cate;	/*TCate*/
    private	Integer	psize;	/*10*/
    private	String	pageid;	/*1*/

    public void setTotal(Integer value){
        this.total = value;
    }
    public Integer getTotal(){
        return this.total;
    }

    public void setCate(TCate value){
        this.cate = value;
    }
    public TCate getCate(){
        return this.cate;
    }

    public void setPsize(Integer value){
        this.psize = value;
    }
    public Integer getPsize(){
        return this.psize;
    }

    public void setPageid(String value){
        this.pageid = value;
    }
    public String getPageid(){
        return this.pageid;
    }
}
