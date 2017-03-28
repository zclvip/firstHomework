package com.zcl.work.DirectByteBufferStudy;

import java.util.BitSet;

/**
 * public void set(int pos): 位置pos的字位设置为true。
 public void set(int bitIndex, boolean value) 将指定索引处的位设置为指定的值。
 public void clear(int pos): 位置pos的字位设置为false。
 public void clear() : 将此 BitSet 中的所有位设置为 false。
 public int cardinality() 返回此 BitSet 中设置为 true 的位数。
 public boolean get(int pos): 返回位置是pos的字位值。
 public void and(BitSet other): other同该字位集进行与操作，结果作为该字位集的新值。
 public void or(BitSet other): other同该字位集进行或操作，结果作为该字位集的新值。
 public void xor(BitSet other): other同该字位集进行异或操作，结果作为该字位集的新值。
 public void andNot(BitSet set) 清除此 BitSet 中所有的位,set - 用来屏蔽此 BitSet 的 BitSet
 public int size(): 返回此 BitSet 表示位值时实际使用空间的位数。
 public int length() 返回此 BitSet 的“逻辑大小”：BitSet 中最高设置位的索引加 1。
 public int hashCode(): 返回该集合Hash 码， 这个码同集合中的字位值有关。
 public boolean equals(Object other): 如果other中的字位同集合中的字位相同，返回true。
 public Object clone() 克隆此 BitSet，生成一个与之相等的新 BitSet。
 public String toString() 返回此位 set 的字符串表示形式。
 *
 *
 * java.util.BitSet就提供了这样的算法。
 比如有一堆数字，需要存储，source=[3,5,6,9]
 用int就需要4*4个字节。
 java.util.BitSet可以存true/false。
 如果用java.util.BitSet，则会少很多，其原理是：
 1，先找出数据中最大值maxvalue=9
 2，声明一个BitSet bs,它的size是maxvalue+1=10
 3，遍历数据source，bs[source[i]]设置成true.
 最后的值是：
 (0为false;1为true)
 bs [0,0,0,1,0,1,1,0,0,1]
           3,  5,6,    9
 这样一个本来要int型需要占4字节共32位的数字现在只用了1位！
 比例32:1
 这样就省下了很大空间。
 *
 * Created by zhaocl1 on 2017/3/28.
 */
public class BitSetStudy {

    /**
     * 说明默认的构造函数声明一个64位的BitSet，值都是false。
     * 如果你要用的位超过了默认size,它会再申请64位，而不是报错。
     */
    public static void testInit(){
        BitSet bs = new BitSet();
        System.out.println(bs.isEmpty()+"--"+bs.size());
        bs.set(0);
        System.out.println(bs.isEmpty() + "--" + bs.size()+"--"+bs.get(0)+"--"+bs.get(1));
        bs.set(1);
        System.out.println(bs.isEmpty() + "--" + bs.size()+"--"+bs.get(0)+"--"+bs.get(1));
        System.out.println(bs.get(65));
        System.out.println(bs.isEmpty()+"--"+bs.size());
        bs.set(65);
        System.out.println(bs.isEmpty() + "--" + bs.size());
    }

    /**
     * 注意它会对重复的数字过滤，就是说，一个数字出现过超过2次的它都记成1.
     */
    public static void printTest(){
        int[] shu={2,42,5,6,6,18,33,15,25,31,28,37};
        BitSet bitSet = new BitSet(getMaxValue(shu));
        System.out.println("bitSet.size()="+bitSet.size());
        putVauleIntoBitSet(shu, bitSet);
        printBitSet(bitSet);
        System.out.println("---"+bitSet.cardinality());
    }

    public static void printBitSet(BitSet bs){
        StringBuffer sbbuf = new StringBuffer();
        sbbuf.append("[\n");
        for(int i=0;i<bs.size();i++){
            if(i < bs.size()-1){
                sbbuf.append(getBitTo10(bs.get(i))+",");
            }else {
                sbbuf.append(getBitTo10(bs.get(i)));
            }
            if((i+1)%8 == 0 && i!=0){
                sbbuf.append("\n");
            }
        }
        sbbuf.append("]");
        System.out.println(sbbuf.toString());
    }
    /**
     * 取最大值
     * @param zu
     * @return
     */
    private static int getMaxValue(int[] zu){
        int temp = 0;
        temp = zu[0];
        for(int i=0;i<zu.length;i++){
            if(temp < zu[i]){
                temp = zu[i];
            }
        }
        System.out.println("maxValue="+temp);
        return temp;
    }
    //存值
    public static void putVauleIntoBitSet(int[] shu,BitSet bs){
        for(int i=0;i<shu.length;i++){
            bs.set(shu[i],true);
        }
    }

    //true,false换成1,0为了好看
    public static String getBitTo10(boolean flag){
        if(flag == true){
            return "1";
        }else {
            return "0";
        }
    }

    public static void main(String[] args){
//        testInit();
        printTest();
    }
}
