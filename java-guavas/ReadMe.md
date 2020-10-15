### 不可变集合

| **可变集合接口**   | **属于JDK还是Guava** | **不可变版本**              |
| ------------------ | -------------------- | --------------------------- |
| Collection         | JDK                  | ImmutableCollection         |
| List               | JDK                  | ImmutableList               |
| Set                | JDK                  | ImmutableSet                |
| Map                | JDK                  | ImmutableMap                |
| SortedMap          | JDK                  | ImmutableSortedMap          |
| Multiset           | Guava                | ImmutableMultiset           |
| SortedMultiset     | Guava                | ImmutableMultimap           |
| ListMultimap       | Guava                | ImmutableListMultimap       |
| SetMultimap        | Guava                | ImmutableSetMultimap        |
| BiMap              | Guava                | ImmutableBiMap              |
| ClassToInstanceMap | Guava                | ImmutableClassToInstanceMap |
| Table              | Guava                | ImmutableTable              |

### 新集合类型

#### SortedMultiset

SortedMultiset继承了Collection接口，可以多次添加相等的元素，他与普通的Collection实现类不同的是，它可以进行数据的统计。count(E e)统计元素e在集合中出现的次数，setCount(E, int)设置元素在Multiset中的计数，同时也会追加等同的元素到集合中

```java
public static void main(String[] args) {
    // MultiSet
    MultiSet set = new HashMultiSet();
    set.add("a1");
    set.add("a1");
    set.add("a1");
    set.add("a1");
    set.add("a2");
    set.add("a3");
    set.add("a3");
    set.add("a3");
    set.add("a2");
    set.add(null);
    set.add(null);
    set.add(null);
    set.add(null);
    set.add(null);
    set.setCount("a3", 10); // 将追加a3到集合中
    System.out.println(set.getCount("a1"));
    System.out.println(set.getCount(null));
    // 去重后的set
    for (Object o : set.uniqueSet()) {
        System.out.println(o);
    }
    // 带有统计的遍历
    Set entrySet = set.entrySet();
    Iterator iterator = entrySet.iterator();
    while (iterator.hasNext()) {
        MultiSet.Entry o = (MultiSet.Entry) iterator.next();
        System.out.println("element:" + o.getElement() + ", count:" + o.getCount());
    }
    // 遍历所有的数据
    for (Object s : set){
        System.out.println(s);
    }
}
```

Multiset的各种实现

| Map               | 对应的Multiset         | 是否支持null           |
| ----------------- | ---------------------- | ---------------------- |
| HashMap           | HashMultiset           | 是                     |
| TreeMap           | TreeMultiset           | 是(需要comparator支持) |
| LinkedHashMap     | LinkedHashMultiset     | 是                     |
| ConcurrentHashMap | ConcurrentHashMultiset | 否                     |
| ImmutableMap      | ImmutableMultiset      | 否                     |



#### Multimap

Multimap可用很容易的把一个键映射到多个值，方便实现`Map<K, List<V>>或Map<K, Set<V>>`。get(key)方法会返回一个Collection集合对象，asMap方法可用将Multimap转为`Map<K,Collection<V>>`，并且支持remove操作会对应到底层的Multimap，但是不支持put和putAll操作。

如果想为Multimap中没有的键返回null，而不是一个新的、可写的空集合，你就可以使用asMap().get(key)

```java
ArrayListMultimap<String, Object> multimap = ArrayListMultimap.create();
multimap.put("a1", "b1");
multimap.put("a1", "b2");
multimap.put("a1", "b3");
multimap.put("top1", "c1");
multimap.put("tes1", "d1");
System.out.println(multimap.asMap().get("jd1")); // null
multimap.get("a1").stream().forEach(e -> System.out.println(e)); // list[b1,b2,b3]
//        List l = Arrays.asList("1","2");
//        multimap.asMap().put("sm", l); // exception
// 遍历 key-value
for (Map.Entry<String, Object> entry : multimap.entries()) {
    System.out.println("key=" + entry.getKey() + ", value = " + entry.getValue());
}
// 遍历所有的key
for (String key : multimap.keys()) {
    System.out.println("key="+key);
}
System.out.println("-------------*****************---------------");
for (String key : multimap.keySet()) { // 去重
    System.out.println("key="+key);
}
// all values Collection
for (Object value : multimap.values()) {
    System.out.println(value);
}
```

Multimap的各种实现

| 实现                  | 键行为类似    | 值行为类似    |
| --------------------- | ------------- | ------------- |
| ArrayListMultimap     | HashMap       | ArrayList     |
| HashMultimap          | HashMap       | HashSet       |
| LinkedListMultimap    | LinkedHashMap | LinkedList    |
| LinkedHashMultimap    | LinkedHashMap | LinkedHashMap |
| TreeMultimap          | TreeMap       | TreeSet       |
| ImmutableListMultimap | ImmutableMap  | ImmutableList |
| ImmutableSetMultimap  | ImmutableMap  | ImmutableSet  |



#### BiMap

BiMap可用自动实现键值的双向映射。inverse()反转BiMap<K, V>的键值映射，保证值是唯一的，因此 values()返回Set而不是普通的Collection。

在BiMap中，如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常。如果对特定值，你想要强制替换它的键，请使用 BiMap.forcePut(key, value)

```java
public static void main(String[] args) {
    HashBiMap<String, Integer> biMap = HashBiMap.create();
    biMap.put("May", 5);
    biMap.put("June", 6);
    biMap.put("July", 7);
    biMap.put("August", 8);
    System.out.println(biMap.get("Fe"));
    System.out.println(biMap.inverse().get(6));
}
```

BiMap的各种实现

| 键–值实现    | 值–键实现    | 对应的BiMap实现 |
| ------------ | ------------ | --------------- |
| HashMap      | HashMap      | HashBiMap       |
| ImmutableMap | ImmutableMap | ImmutableBiMap  |
| EnumMap      | EnumMap      | EnumBiMap       |
| EnumMap      | HashMap      | EnumHashBiMap   |



#### Table

```java
Table<Vertex, Vertex, Double> weightedGraph = HashBasedTable.create();
weightedGraph.put(v1, v2, 4);
weightedGraph.put(v1, v3, 20);
weightedGraph.put(v2, v3, 5);
weightedGraph.row(v1); // returns a Map mapping v2 to 4, v3 to 20
weightedGraph.column(v3); // returns a Map mapping v1 to 20, v2 to 5
```

当你想使用多个键做索引的时候，你可能会用类似Map<FirstName, Map<LastName, Person>>的实现，这种方式很丑陋，使用上也不友好。Guava为此提供了新集合类型Table，它有两个支持所有类型的键：”行”和”列”。

+ rowMap()：用Map<R, Map<C, V>>表现Table<R, C, V>。同样的， rowKeySet()返回”行”的集合`Set<R>`。
+ row(r) ：用Map<C, V>返回给定”行”的所有列，对这个map进行的写操作也将写入Table中。
  类似的列访问方法：columnMap()、columnKeySet()、column(c)。（基于列的访问会比基于的行访问稍微低效点）
+ cellSet()：用元素类型为Table.Cell<R, C, V>的Set表现Table<R, C, V>。Cell类似于Map.Entry，但它是用行和列两个键区分的。

Table的各种实现

| Table          | 本质的实现类型                      |
| -------------- | ----------------------------------- |
| HashBasedTable | HashMap<R, HashMap<C, V>>           |
| TreeBasedTable | TreeMap<R, TreeMap<C,V>>            |
| ImmutableTable | ImmutableMap<R, ImmutableMap<C, V>> |
| ArrayTable     | 二维数组实现                        |



#### ClassToInstanceMap

一种特殊的Map：它的键是类型，而值是符合键所指类型的对象。对于Map的扩展在于新增了两个方法：

+ `T getInstance(Class<T>) `和`T putInstance(Class<T>, T)`，从而避免强制类型转换，同时保证了类型安全。

```java
public interface ClassToInstanceMap<B> extends Map<Class<? extends B>, B> {
	<T extends B> T getInstance(Class<T> type);
    <T extends B> T putInstance(Class<T> type, @Nullable T value);
}
```

它仅有两个实现类：MutableClassToInstanceMap 和 ImmutableClassToInstanceMap(不可变)

#### RangeSet和RangeMap

RangeSet描述了一组不相连的、非空的区间。当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略。

RangeMap描述了”不相交的、非空的区间”到特定值的映射。和RangeSet不同，RangeMap不会合并相邻的映射，即便相邻的区间映射到相同的值。

```java
RangeSet<Integer> rangeSet = TreeRangeSet.create();
rangeSet.add(Range.closed(1, 10)); // {[1,10]}  区间合并使用span
rangeSet.add(Range.closedOpen(11, 15));// 不相连区间:{[1,10], [11,15)}
rangeSet.add(Range.closedOpen(15, 20)); // 相连区间; {[1,10], [11,20)}
rangeSet.add(Range.openClosed(0, 0)); // 空区间; {[1,10], [11,20)}
rangeSet.remove(Range.open(5, 10)); // 分割[1, 10]; {[1,5], [10,10], [11,20)}
RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}
```

RangeSet的操作方法：

- complement()：返回RangeSet的补集视图。complement也是RangeSet类型，包含了不相连的、非空的区间。
- subRangeSet(Range<C>)：返回RangeSet与给定Range的交集视图。这扩展了传统排序集合中的headSet、subSet和tailSet操作。
- asRanges()：用Set<Range<C>>表现RangeSet，这样可以遍历其中的Range。
- asSet(DiscreteDomain<C>)（仅ImmutableRangeSet支持）：用ImmutableSortedSet<C>表现RangeSet，以区间中所有元素的形式而不是区间本身的形式查看。（这个操作不支持DiscreteDomain 和RangeSet都没有上边界，或都没有下边界的情况）

RangeSet的查询方法

为了方便操作，RangeSet直接提供了若干查询方法，其中最突出的有:

- contains(C)：RangeSet最基本的操作，判断RangeSet中是否有任何区间包含给定元素。
- rangeContaining(C)：返回包含给定元素的区间；若没有这样的区间，则返回null。
- encloses(Range<C>)：简单明了，判断RangeSet中是否有任何区间包括给定区间。
- span()：返回包括RangeSet中所有区间的最小区间。

RangeMap遍历

- asMapOfRanges()：用`Map<Range<K>, V>`表现RangeMap。这可以用来遍历RangeMap。
- `subRangeMap(Range<K>)`：用RangeMap类型返回RangeMap与给定Range的交集视图。这扩展了传统的headMap、subMap和tailMap操作

### 集合工具

工具类对应集合类型一览表：

| 集合接口   | 属于JDK还是Guava | Guava工具类  |
| ---------- | ---------------- | ------------ |
| Collection | JDK              | Collections2 |
| List       | JDK              | Lists        |
| Set        | JDK              | Sets         |
| SortedSet  | JDK              | Sets         |
| Map        | JDK              | Maps         |
| SortedMap  | JDK              | Maps         |
| Queue      | JDK              | Queues       |
| Multiset   | Guava            | Multisets    |
| Multimap   | Guava            | Multimaps    |
| BiMap      | Guava            | Maps         |
| Table      | Guava            | Tables       |

以Lists工具类API举例

```java
public static <E> ArrayList<E> newArrayList(E... elements) // 构建ArrayList对象
public static <E> LinkedList<E> newLinkedList() 
public static <E> List<E> asList(@Nullable E first, E[] rest) 
public static <B> List<List<B>> cartesianProduct(List<? extends B>... lists) // 矩阵组合
public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) // 对list中的数据进行转换
public static <T> List<List<T>> partition(List<T> list, int size) // 对list进行分组
public static ImmutableList<Character> charactersOf(String string) // 将string转为不可变的字符集合
public static <T> List<T> reverse(List<T> list) // 对list内部数据进行顺序倒置    
```

更多的工具类API参考：http://ifeve.com/google-guava-collectionutilities/  和  http://ifeve.com/google-guava-collectionhelpersexplained/













































### FAB

首先，一份好的简历不光说明事实，更通过FAB模式来增强其说服力。

- Feature：是什么
- Advantage：比别人好在哪些地方
- Benefit：如果雇佣你，招聘方会得到什么好处

其次，写简历和写议论文不同，过分的论证会显得自夸，反而容易引起反感，所以要点到为止。这里的技巧是，提供论据，把论点留给阅读简历的人自己去得出。放论据要具体，最基本的是要数字化，好的论据要让人印象深刻。

举个例子，下边内容是虚构的：

2006年，我参与了手机XX网发布系统WAPCMS的开发（`这部分是大家都会写的`）。作为核心程序员，我不但完成了网站界面、调度队列的开发工作，更提出了高效的组件级缓存系统，通过碎片化缓冲有效的提升了系统的渲染效率。（`这部分是很多同学忘掉的，要写出你在这个项目中具体负责的部分，以及你贡献出来的价值。`）在该系统上线后，Web前端性能从10QPS提升到200QPS，服务器由10台减少到3台（`通过量化的数字来增强可信度`）。2008年我升任WAPCMS项目负责人，带领一个3人小组支持着每天超过2亿的PV（`这就是Benefit。你能带给前雇主的价值，也就是你能带给新雇主的价值。`）。

有同学问，如果我在项目里边没有那么显赫的成绩可以说怎么办？讲不出成绩时，就讲你的成长。因为学习能力也是每家公司都看中的东西。你可以写你在这个项目里边遇到了一个什么样的问题，别人怎么解决的，你怎么解决的，你的方案好在什么地方，最终这个方案的效果如何。

具体、量化、有说服力，是技术简历特别需要注重的地方。

（以上内容在写完简历后，对每一段进行评估，完成后再删除）



















