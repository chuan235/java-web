package com.gc.baseutil;

import com.google.common.base.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * base包下常用的工具类学习
 * 字符串连接工具：Joiner
 * 字符串分割工具：Splitter
 * 格式化工具：CaseFormat
 * 工具类：Strings
 *  https://guava.dev/releases/snapshot/api/docs/com/google/common/base/Strings.html
 */
public class BaseUtil {

    /**
    * Joiner/       对象是immutable的，所以在你为Splitter对象添加配置的时候，你必须用一个新的Splitter对象去接收
    *
    * */
    @Test
    public void testjoiner(){
        // 自动忽略拼接中的null值，不会忽略空值
        Joiner joiner = Joiner.on(",").skipNulls();
        // Mike,like,null,book
        String s1 = joiner.join("Mike", null, "like", "", "book");
        // 1,3,5,7
        String s2 = Joiner.on(",").join(Arrays.asList(1, 3, 5, 7));
        System.out.println(s1);
        System.out.println(s2);
        // 匹配元音小写字母
        CharMatcher matcher = CharMatcher.anyOf("aeiou");
//        LOWER_CAMEL			lowerCamel
//        LOWER_HYPHEN		    lower-hyphen
//        LOWER_UNDERSCORE	    lower_underscore
//        UPPER_CAMEL			UpperCamel
//        UPPER_UNDERSCORE	    sUPPER_UNDERSCORE
        // 格式化工具
        String s3 = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_name");
        // constantName
        System.out.println(s3);
        // null转空字符串
        Strings.nullToEmpty("12231");
        // null 或者 空字符串
        Strings.isNullOrEmpty("");
        int i = 90;
        // 前置判断，根据传入表达式的结果，结果为false就会抛出异常
        Preconditions.checkArgument(i>=0,"Argument was %s but expected nonnegative",i);
//        Preconditions.checkNotNull();
//        Preconditions.checkState();
        // 简化toString的写法        toStringHelper("myObject")
        String toString = MoreObjects.toStringHelper(this).add("x1", 1).toString();
        // BaseUtil{x1=1}
        System.out.println(toString);
    }

    @Test
    public void testSplitter(){
        // 设置分割符：单个字符，字符串，正则，CharMatcher实例，传入一个分割的长度
        Iterable<String> iterable = Splitter.on(",")
                // 去掉子串的空格
                .trimResults()
                // 去掉空的子串
                .omitEmptyStrings()
                .split("foo,bar,, ,  qux");
        // [foo, bar, qux]
        System.out.println(iterable);

        Iterable<String> iterable1 = Splitter.on("/").trimResults()
                // 设置分割的子串数量，数量达到limit时，停止分割  [wrong, wrong / wrong]
                .limit(2)
                .split(" wrong / wrong / wrong ");
        // [wrong, wrong, wrong]
        System.out.println(iterable1);
        // Splitter类可以对结果进行无限次分割
        Map<String, String> map = Splitter.on(";").trimResults().withKeyValueSeparator("=").split("a=2;b=3");
        // {a=2, b=3}
        System.out.println(map);
        // [a , b_ , c] b后面的下划线去不掉
        List<String> list = Splitter.on(',').trimResults(CharMatcher.is('_')).splitToList("_a ,_b_ ,c__");
        System.out.println(list);
    }

    @Test
    public void testCharMatcher(){

    }
}
