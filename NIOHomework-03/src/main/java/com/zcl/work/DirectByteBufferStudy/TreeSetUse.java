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
     * ���ش� set �д��ڵ��ڸ���Ԫ�ص���СԪ�أ����������������Ԫ�أ��򷵻� null��
     *
     * @param get
     * @return
     */
    public T ceiling(T get){
        return treeSet.ceiling(get);
    }

    /**
     * higher�����ش� set ���ϸ���ڸ���Ԫ�ص���СԪ�أ����������������Ԫ�أ��򷵻� null��
     *
     * @param item
     * @return
     */
    public T higher(T item){
        return treeSet.higher(item);
    }

    /**
     * lower�����ش� set ���ϸ�С�ڸ���Ԫ�ص����Ԫ�أ����������������Ԫ�أ��򷵻� null��
     *
     * @param item
     * @return
     */
    public T lower(T item){
        return treeSet.lower(item);
    }

    /**
     * ���ش� set �Ĳ�����ͼ����Ԫ�ش� fromElement���������� toElement������������
     *
     * @param from
     * @param to
     * @return
     */
    public SortedSet<T> subSet(T from,T to){
        //from true����ͼ������ʼƥ������
        //to false ��ͼ������ƥ��Ľ�������
        return new TreeSet<T>(treeSet.subSet(from,true, to,false));
    }

    /**
     * ���ش� set �Ĳ�����ͼ����Ԫ�ش��ڣ�����ڣ���� inclusive Ϊ true��fromElement��
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
        // ʹ�ô��ڵ��ڽ���ƥ��
        System.out.println(treeIter.ceiling(10));
        // ʹ�ô��ڽ���ƥ��
        System.out.println(treeIter.higher(10));
        // ʹ��С�ڽ���ƥ��
        System.out.println(treeIter.lower(10));

        treeIter.remove(15);
        //���з�Χ��ȡ��ָ����ʼ�����
        System.out.println(treeIter.subSet(0, 20));

        treeIter.add(15);

        //ָ����ʼ���л�ȡ
        System.out.println(treeIter.tailSet(10));
        System.out.println(treeIter.pollFirst());
        System.out.println(treeIter.pollFirst());
        System.out.println(treeIter.pollFirst());
    }
}
