package com.lcsd.police.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
public class Sy {
    public static class TPageProject{

        private	String	title;	/*基础设施建设与管理*/
        private	String	items;	/*basis*/
        private	String	linkurl;	/*http://jingbao.5kah.com/app/index.php?c=data&data=czzacxcykj*/
        private	String	thumb;	/*http://jingbao.5kah.com/res/ico/6b9b6c7d141f0002.png*/

        public void setTitle(String value){
            this.title = value;
        }
        public String getTitle(){
            return this.title;
        }

        public void setItems(String value){
            this.items = value;
        }
        public String getItems(){
            return this.items;
        }

        public void setLinkurl(String value){
            this.linkurl = value;
        }
        public String getLinkurl(){
            return this.linkurl;
        }

        public void setThumb(String value){
            this.thumb = value;
        }
        public String getThumb(){
            return this.thumb;
        }

    }
    private	List<TPageProject>	pageProject;	/*List<TPageProject>*/
    public void setPageProject(List<TPageProject> value){
        this.pageProject = value;
    }
    public List<TPageProject> getPageProject(){
        return this.pageProject;
    }

    public static class THeadad{

        private	String	id;	/*1893*/
        private	String	title;	/*ad1*/
        private	String	thumb;	/*http://jingbao.5kah.com/res/img/201801/02/a36bdde5738df3b5.png*/

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

        public void setThumb(String value){
            this.thumb = value;
        }
        public String getThumb(){
            return this.thumb;
        }

    }
    private	List<THeadad>	headad;	/*List<THeadad>*/
    public void setHeadad(List<THeadad> value){
        this.headad = value;
    }
    public List<THeadad> getHeadad(){
        return this.headad;
    }
}
