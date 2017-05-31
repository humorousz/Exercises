package com.humorousz.commonutils.json.model;

import java.util.ArrayList;

/**
 * Created by zhangzhiquan on 2017/5/1.
 */

public class Team {
    //人员
    private ArrayList<Person> persons;
    //小组名
    private String team;

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Team{" +
                "persons=" + persons +
                ", team='" + team + '\'' +
                '}';
    }
}