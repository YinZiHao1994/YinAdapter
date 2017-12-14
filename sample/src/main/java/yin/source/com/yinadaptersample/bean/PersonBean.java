package yin.source.com.yinadaptersample.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yin on 2017/4/23.
 */

public class PersonBean {

    private String name;
    //0 表示女，1 表示男
    private int sex;

    private int age;

    public PersonBean(String name, int sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public static List<PersonBean> init() {
        List<PersonBean> list = new ArrayList<>();
        for (int i=0;i<8;i++) {
            String name = String.valueOf(i);
            PersonBean personBean = new PersonBean(name, (i+1) % 5 == 0 ? 0 : 1, i);
            list.add(personBean);
        }
        return list;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }
}
