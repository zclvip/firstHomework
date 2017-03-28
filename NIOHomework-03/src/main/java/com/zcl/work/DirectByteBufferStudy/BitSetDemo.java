package com.zcl.work.DirectByteBufferStudy;

import java.util.Arrays;
import java.util.BitSet;

/**
 SN	   Methods with 描述
 1	void and(BitSet bitSet)
 与运算调用的内容BitSet中对象与那些指定bitSet。结果存放到调用对象。
 2	void andNot(BitSet bitSet)
 对于bitSet每1位，在调用BitSet中的相应位清零。
 3	int cardinality( )
 返回设置位的调用对象的数量。
 4	void clear( )
 所有位清零。
 5	void clear(int index)
 index指定的位清零。
 6	void clear(int startIndex, int endIndex)
 将从startIndex到endIndex清零。
 7	Object clone( )
 重复调用BitSet中对象。
 8	boolean equals(Object bitSet)
 返回true如果调用位设置相当于一个在bitSet通过。否则，该方法返回false。
 9	void flip(int index)
 逆转由index指定的位。
 10	void flip(int startIndex, int endIndex)
 反转将从startIndex位到endIndex.
 11	boolean get(int index)
 返回指定索引处的位的当前状态。
 12	BitSet get(int startIndex, int endIndex)
 返回一个BitSet中，它包含的比特将从startIndex到endIndex.1。调用对象不被改变。
 13	int hashCode( )
 返回调用对象的哈希代码。
 14	boolean intersects(BitSet bitSet)
 如果至少有一个对调用对象和bitSet内相应位为1，则返回true。
 15	boolean isEmpty( )
 返回true如果在调用对象中的所有位均为零。
 16	int length( )
 返回到持有调用BitSet中的内容所需的比特数。这个值是由最后1位的位置决定的。
 17	int nextClearBit(int startIndex)
 返回下个清零位的索引，（即，下一个零位），从由startIndex指定的索引开始
 18	int nextSetBit(int startIndex)
 返回下一组位（即，下一个1比特）的索引，从由startIndex指定的索引开始。如果没有位被设置，则返回1。
 19	void or(BitSet bitSet)
 OR值调用的内容BitSet中对象，通过BitSet指定。结果被放置到调用对象。
 20	void set(int index)
 设置由index指定的位。
 21	void set(int index, boolean v)
 设置由index指定在v. true为传递的值的位设置位，false则清除该位。
 22	void set(int startIndex, int endIndex)
 设置位将从startIndex到endIndex.1。
 23	void set(int startIndex, int endIndex, boolean v)
 设置位从startIndex到endIndex.1，在真正传递的值v设置位，清除位为false。
 24	int size( )
 返回位在调用BitSet中对象的数量。
 25	String toString( )
 返回字符串相当于调用BitSet中的对象。
 26	void xor(BitSet bitSet)
 在异或调用BitSet中对象的内容与由BitSet指定。结果存放到调用对象。
 *
 * Created by zhaocl1 on 2017/3/28.
 */
public class BitSetDemo {

    /**
     * 求一个字符串包含的char
     * @param str
     */
    public static void containChars(String str) {
        BitSet used = new BitSet();
        for(int i=0;i<str.length();i++){
            used.set(str.charAt(i));
        }
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        int size = used.size();
        for(int i=0;i<size;i++){
            if(used.get(i)){
                sb.append((char) i);
            }
        }
        sb.append("]");
        System.out.println("containChars:"+sb.toString());
    }

    /**
     * 求素数 有无限个。一个大于1的自然数，如果除了1和它本身外，不能被其他自然数整除(除0以外）的数称之为素数(质数） 否则称为合数
     */
    public static void computePrime() {
        BitSet bitSet = new BitSet(1024);
        int size = bitSet.size();
        System.out.println("size="+size);
        for(int i=2;i<size;i++){
            bitSet.set(i);
        }
        int finalBit = (int)Math.sqrt(bitSet.size());
        System.out.println("finalBit=" + finalBit);
        for(int i=2;i<finalBit;i++){
            if(bitSet.get(i)){
                for(int j=2*i;j<size;j+=i){
                    bitSet.clear(j);
                }
            }
        }
        int counter = 0;
        for (int i=0;i<size;i++){
            if(bitSet.get(i)){
                System.out.printf("%5d", i);
                if (++counter % 15 == 0)
                    System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * 数字排序
     */
    public static void sortArray() {
        int[] array = new int[] { 423, 700, 9999, 2323, 356, 6400, 1,2,3,2,2,2,2 };
        BitSet bitSet = new BitSet(2 << 13);
        // 虽然可以自动扩容，但尽量在构造时指定估算大小,默认为64
        System.out.println("BitSet size: " + bitSet.size());

        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i]);
        }
        int bitlen = bitSet.cardinality();//剔除重复数字后的元素个数
        System.out.println("cardinality: "+bitlen);
        int[] orderedArray = new int[bitlen];
        int k = 0;
        for(int i=bitSet.nextSetBit(0);i>0; i = bitSet.nextSetBit(i+1)){
            orderedArray[k++] = i;
        }
        System.out.println("After ordering: ");
        for (int i = 0; i < bitlen; i++) {
            System.out.print(orderedArray[i] + "\t");
        }

        System.out.println("iterate over the true bits in a BitSet");
        //或直接迭代BitSet中bit为true的元素iterate over the true bits in a BitSet
        for(int i=bitSet.nextSetBit(0);i>=0;i = bitSet.nextSetBit(i+1)){
            System.out.print(i + "\t");
        }
        for(int i=0;i<bitSet.size();i++){
            System.out.println(bitSet.nextSetBit(i) + ",");
        }
    }

    /**
     * 将BitSet对象转化为ByteArray
     * @param bitSet
     * @return
     */
    public static byte[] bitSet2ByteArray(BitSet bitSet) {
        byte[] bytes = new byte[bitSet.size() / 8];
        for (int i = 0; i < bitSet.size(); i++) {
            int index = i / 8;
            int offset = 7 - i % 8;
            bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
        }
        return bytes;
    }

    /**
     * 将ByteArray对象转化为BitSet
     * @param bytes
     * @return
     */
    public static BitSet byteArray2BitSet(byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true
                        : false);
            }
        }
        return bitSet;
    }

    /**
     * 简单使用示例
     */
    public static void simpleExample() {
        String names[] = { "Java", "Source", "and", "Support" };
        BitSet bits = new BitSet();
        for (int i = 0, n = names.length; i < n; i++) {
            if ((names[i].length() % 2) == 0) {
                bits.set(i);
            }
        }

        System.out.println(bits);
        System.out.println("Size : " + bits.size());
        System.out.println("Length: " + bits.length());
        for (int i = 0, n = names.length; i < n; i++) {
            if (!bits.get(i)) {
                System.out.println(names[i] + " is odd");
            }
        }
        BitSet bites = new BitSet();
        bites.set(0);
        bites.set(1);
        bites.set(2);
        bites.set(3);
        bites.andNot(bits);
        System.out.println(bites);
    }

    public static void main(String[] args){
//        containChars("hello world!");
//        computePrime();
//        sortArray();
//        simpleExample();

        //BitSet与Byte数组互转示例
        BitSet bitSet = new BitSet();
        bitSet.set(3, true);
        bitSet.set(98, true);
        System.out.println(bitSet.size() + "," + bitSet.cardinality());
        //将BitSet对象转成byte数组
        byte[] bytes = BitSetDemo.bitSet2ByteArray(bitSet);
        System.out.println(Arrays.toString(bytes));

        //在将byte数组转回来
        bitSet = BitSetDemo.byteArray2BitSet(bytes);
        System.out.println(bitSet.size()+","+bitSet.cardinality());
        System.out.println(bitSet.get(3));
        System.out.println(bitSet.get(98));
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i+"\t");
        }
    }
}
