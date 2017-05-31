package com.humorousz.commonutils.json.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/4/22.
 */

public class JokeModel implements Serializable ,IJokeModel{
    private static final String TAG = "JokeModel";
    public List<JokeItem> newslist;

    @Override
    public JokeModel getModel() {
        return this;
    }

    @Override
    public List<JokeItem> getJokeItems() {
        return newslist;
    }

    public JokeModel(){

    }
    public static class JokeItem implements Serializable{
        public int id;
        public String title;
        public String content;

        @Override
        public boolean equals(Object obj) {
            if(this == obj)
                return true;
            if(obj == null)
                return false;
            if(obj.getClass() != getClass())
                return false;
            JokeItem other = (JokeItem) obj;
            return  id == other.id && title.equals(other.title)&& content.equals(other.content);
        }

        @Override
        public int hashCode() {
            return id+title.hashCode()*10+content.hashCode()*13;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.newslist.size();i++){
            JokeItem item = newslist.get(i);
            sb.append("id"+item.id+" title:"+item.title+"\n");
        }
        return sb.toString();
    }
}
