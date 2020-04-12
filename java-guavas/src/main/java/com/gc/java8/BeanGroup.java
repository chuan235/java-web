package com.gc.java8;

import com.gc.baseutil.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * java8使用stream对数据进行分组
 */
public class BeanGroup {

    public static void groupTest() {
        List<Person> list = Arrays.asList(
                new Person("a", "mike", 12),
                new Person("a", "Tom", 344),
                new Person("b", "jery", 23),
                new Person("c", "cat", 101),
                new Person("b", "alice", 67),
                new Person("a", "jack", 45));
        // 根据firstName进行分组
        list.stream().collect(Collectors.groupingBy(Person::getFirstName)).forEach((first, persons) -> {
            System.out.println(first);
            persons.forEach(person -> System.out.println(person.getFirstName() + " " + person.getLastName()));
        });
        // 根据数据范围分组
        list.stream().collect(Collectors.partitioningBy(person -> person.getZipCode() < 80)).forEach((code, persons) -> {
            // false 存储 < 80的数据
            // true 存储 > 80的数据
            System.out.println("code=" + code);
            persons.forEach(person -> System.out.println(person.getFirstName() + " " + person.getLastName()));
        });

    }
}
