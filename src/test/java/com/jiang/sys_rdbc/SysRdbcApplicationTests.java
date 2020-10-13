package com.jiang.sys_rdbc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SysRdbcApplicationTests {

    @Test
    public void test() {
    }

    public static void main(String[] args) {

        //流式编程
        List<Student> studentList = Stream
                .of(new Student("路飞", 22, 175), new Student("红发", 40, 180), new Student("白胡子", 50, 185))
                .collect(Collectors.toList());
        System.out.println(studentList);

        //fifter过滤
        List list = studentList.stream().filter(stu -> stu.getHeight() < 180).collect(Collectors.toList());
        System.out.println(list);

        //map
        List<String> names = studentList.stream().map(stu -> stu.name).collect(Collectors.toList());

        System.out.println(names);

    }

}
