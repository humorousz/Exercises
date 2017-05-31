package com.humorousz.commonutils.json.model;

/**
 * Created by zhangzhiquan on 2017/5/1.
 */

public class Person {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 婚否
     */
    private boolean married;
    /**
     * 工资
     */
    private double salary;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", married=" + married +
                ", salary=" + salary +
                '}';
    }

    /**
     * 自我介绍
     *
     * @param ending 结束语
     */
    private void introduce(String ending) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hi,I'm " + getName() + "!");
        sb.append("I'm " + getAge() + " years old!");
        sb.append("I'm " + (isMarried() ? "" : "not ") + "married!");
        sb.append(ending == null ? "" : ending);
        System.out.println(sb.toString());
    }
}