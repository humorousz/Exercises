package com.humorousz.commonutils;

import com.humorousz.commonutils.json.model.IJokeModel;
import com.humorousz.commonutils.json.model.JokeModel;
import com.humorousz.commonutils.json.model.Person;
import com.humorousz.commonutils.json.model.Team;
import com.humorousz.commonutils.json.JsonTools;

import org.json.JSONException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangzhiquan on 2017/5/1.
 */

public class JsonParseUnitTest {
    String jsonTeam = "{\"persons\":[{\"name\":\"Bob\",\"age\":14,\"married\":false,\"salary\":null},{\"name\":\"Kate\",\"age\":26,\"married\":true,\"salary\":8888.88}],\"team\":\"Android\"}";
    String jsonPerson = "{\"name\":null,\"age\":14,\"married\":false,\"salary\":null}";
    String jsonString = "{\"code\":200,\"msg\":\"success\",\"newslist\":[{\"id\":2394,\"title\":\"正点火车\",\"content\":\"<br\\/>商人吉米在铁路上做了多年的买卖，这天偶然发现一列火车<br\\/>准时到了站。<br\\/>他连忙跑到列车员跟前说：“请吸烟，我祝贺你！我在这条<br\\/>铁路上跑了15年，这还是第一次见火车正点到站。”<br\\/>“留着你的烟吧，”列车员说，“这是昨天的列车！”<br\\/>\"}]}";

    @Test
    public void parseJson_isNotEmpty(){
        Team team = JsonTools.parse(jsonTeam,Team.class);
        Person person =  JsonTools.parse(jsonPerson,Person.class);
        IJokeModel model = JsonTools.parse(jsonString,JokeModel.class);
        assertNotNull(team);
        assertNotNull(person);
        assertNotNull(model);
    }

    @Test
    public void parseTeam_isCorrect(){
        Team team = JsonTools.parse(jsonTeam,Team.class);
        assertNotNull(team);
        List<Person> persons = team.getPersons();
        assertEquals(persons.size(),2);
        Person person1 = persons.get(0);
        Person person2 = persons.get(1);
        assertPerson(person1,"Bob",14,false,0.0);
        assertPerson(person2,"Kate",26,true,8888.88);
        assertEquals(team.getTeam(),"Android");

    }

    @Test
    public void parsePerson_isCorrect(){
        Person person =  JsonTools.parse(jsonPerson,Person.class);
        assertNotNull(person);
        assertPerson(person,null,14,false,0.0);
    }

    private void assertPerson(Person person,String name,int age,boolean married,double salary){
        assertEquals(person.getName(),name);
        assertEquals(person.getAge(),age);
        assertEquals(person.isMarried(),married);
        assertEquals(person.getSalary(),salary,0.5);
    }


    @Test
    public void parseJson_isCorrect() throws JSONException {
        IJokeModel model = JsonTools.parse(jsonString, JokeModel.class);
        assertNotNull(model);
        List<JokeModel.JokeItem> list = model.getJokeItems();
        assertEquals(list.size(),1);
        JokeModel.JokeItem item = list.get(0);
        assertEquals(item.id,2394);
        assertEquals(item.title,"正点火车");
        assertTrue(item.content.contains("商人吉米在铁路上做了多年的买卖"));
    }



}
