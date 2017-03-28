package com.zcl.work.DirectByteBufferStudy;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by zhaocl1 on 2017/3/27.
 */
public class TreeSetUse<T> {

    private TreeSet<T> treeSet = new TreeSet<T>();

    public void add(T item){
        treeSet.add(item);
    }

    public void remove(T item){
        treeSet.remove(item);
    }

    public T pollFirst(){
        return treeSet.pollFirst();
    }

    /**
     * 返回此 set 中大于等于给定元素的最小元素；如果不存在这样的元素，则返回 null。
     *
     * @param get
     * @return
     */
    public T ceiling(T get){
        return treeSet.ceiling(get);
    }

    /**
     * higher：返回此 set 中严格大于给定元素的最小元素；如果不存在这样的元素，则返回 null。
     *
     * @param item
     * @return
     */
    public T higher(T item){
        return treeSet.higher(item);
    }

    /**
     * lower：返回此 set 中严格小于给定元素的最大元素；如果不存在这样的元素，则返回 null。
     *
     * @param item
     * @return
     */
    public T lower(T item){
        return treeSet.lower(item);
    }

    /**
     * 返回此 set 的部分视图，其元素从 fromElement（包括）到 toElement（不包括）。
     *
     * @param from
     * @param to
     * @return
     */
    public SortedSet<T> subSet(T from,T to){
        //from true，视图包括开始匹配数据
        //to false 视图不包括匹配的结束数据
        return new TreeSet<T>(treeSet.subSet(from,true, to,false));
    }

    /**
     * 返回此 set 的部分视图，其元素大于（或等于，如果 inclusive 为 true）fromElement。
     * @param from
     * @return
     */
    public SortedSet<T> tailSet(T from){
        return  new TreeSet<T>(treeSet.tailSet(from));
    }

    public static void main(String[] args) {
        TreeSetUse<Integer> treeIter = new TreeSetUse<Integer>();

        for (int i = 0; i < 30; i++) {
            treeIter.add(i);
        }
        // 使用大于等于进行匹配
        System.out.println(treeIter.ceiling(10));
        // 使用大于进行匹配
        System.out.println(treeIter.higher(10));
        // 使用小于进行匹配
        System.out.println(treeIter.lower(10));

        treeIter.remove(15);
        //进行范围截取，指定开始与结束
        System.out.println(treeIter.subSet(0, 20));

        treeIter.add(15);

        //指定开始进行获取
        System.out.println(treeIter.tailSet(10));
        System.out.println(treeIter.pollFirst());
        System.out.println(treeIter.pollFirst());
        System.out.println(treeIter.pollFirst());
    }
}
