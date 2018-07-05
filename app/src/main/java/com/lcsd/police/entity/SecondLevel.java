package com.lcsd.police.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
public class SecondLevel {
    public static class TSublist{

        private	String	id;	/*617*/
        private	String	title;	/*政策文件*/
        private	String	ico;	/**/
        private	String	identifier;	/*zhengcewenjian*/

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

        public void setIco(String value){
            this.ico = value;
        }
        public String getIco(){
            return this.ico;
        }

        public void setIdentifier(String value){
            this.identifier = value;
        }
        public String getIdentifier(){
            return this.identifier;
        }

    }
    private List<TSublist> sublist;	/*List<TSublist>*/
    public void setSublist(List<TSublist> value){
        this.sublist = value;
    }
    public List<TSublist> getSublist(){
        return this.sublist;
    }

    private	String	id;	/*612*/
    private	String	title;	/*法律法规类*/
    private	String	ico;	/**/
    private	String	identifier;	/*falvfaguilei*/

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

    public void setIco(String value){
        this.ico = value;
    }
    public String getIco(){
        return this.ico;
    }

    public void setIdentifier(String value){
        this.identifier = value;
    }
    public String getIdentifier(){
        return this.identifier;
    }

}
