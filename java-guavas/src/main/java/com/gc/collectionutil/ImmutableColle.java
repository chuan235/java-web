package com.gc.collectionutil;

import com.gc.baseutil.Person;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import org.junit.Test;

import java.util.*;

/**
 * Guava提供新的集合类型 Multiset
 * Multiset在添加元素时：add(E,int)  remove(E,int) setCount(E,int) size() count(E)
 *
一个multiap <K, V>不是一个Map<K，集合<V>>，尽管这样的映射可能在一个multiap实现中使用。显著的差异包括:
    get(key)总是返回一个非空的，可能是空的集合。这并不意味着multiap花费任何内存与键关联
    相反，返回的集合是一个视图，允许添加键与之关联。

    containskey (key)是true，当且仅当有与指定键关联的任何元素时。
    如果一个k以前与一个或多个值相关联，而这些值后来已经从multiap中删除，则multiap.containskey(k)将返回false。

    entry()返回数multiap中所有键的所有条目。如果您想要所有的键集合条目，请使用asMap().entryset()。
    size()返回整个multiap中的条目数量，而不是不同键的数量。使用multiap.keyset().size()来获得不同键的数量。
 *
 */
public class ImmutableColle {

    /**
     * 不变集合: 用作常量，节省空间，线程安全
     * JDK的不变集合：Collections.unmodifiablexxx
     * 每一个Guava不变集合都不支持空值，需要使用null时，可以切换为一般的可变集合
     */
    private static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
            "red","orange","yellow","green","blue","purple"
    );

    private static final ImmutableSet<Color> GOOGLE_COLORS = ImmutableSet.<Color>builder()
                    .add(new Color(1,23,32))
                    .add(new Color(100,21,92))
                    .build();

    @Test
    public void test(){
        // 缺点：需要创建集合副本，和可变集合没有区别，只有没有对象持有原始的集合时，返回的集合才是真正不可变的
        Collections.unmodifiableList(new ArrayList<>());
        // creates a ListMultimap with tree keys and array list values
        ListMultimap<String, Integer> treeListMultimap =
                MultimapBuilder.treeKeys().arrayListValues().build();

        // creates a SetMultimap with hash keys and enum set values
        SetMultimap<Integer, Enum> hashEnumMultimap =
                MultimapBuilder.hashKeys().enumSetValues(Enum.class).build();

    }

    /**
     * 查询一个文档中单词出现的频率
     */
    public void countTimesWord(){
        String[] words = new String[]{"1","jack","aaa"};
        Map<String,Integer> counts = new HashMap<>();
        for(String word:words){
            Integer count = counts.get(word);
            if(Objects.isNull(count)){
                counts.put(word, 1);
            }else{
                counts.put(word, count+1);
            }
        }
        // 使用Guava的Multiset
    }

}
