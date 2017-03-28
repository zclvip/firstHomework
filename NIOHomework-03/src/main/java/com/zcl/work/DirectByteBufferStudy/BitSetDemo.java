package com.zcl.work.DirectByteBufferStudy;

import java.util.Arrays;
import java.util.BitSet;

/**
 SN	   Methods with ����
 1	void and(BitSet bitSet)
 ��������õ�����BitSet�ж�������Щָ��bitSet�������ŵ����ö���
 2	void andNot(BitSet bitSet)
 ����bitSetÿ1λ���ڵ���BitSet�е���Ӧλ���㡣
 3	int cardinality( )
 ��������λ�ĵ��ö����������
 4	void clear( )
 ����λ���㡣
 5	void clear(int index)
 indexָ����λ���㡣
 6	void clear(int startIndex, int endIndex)
 ����startIndex��endIndex���㡣
 7	Object clone( )
 �ظ�����BitSet�ж���
 8	boolean equals(Object bitSet)
 ����true�������λ�����൱��һ����bitSetͨ�������򣬸÷�������false��
 9	void flip(int index)
 ��ת��indexָ����λ��
 10	void flip(int startIndex, int endIndex)
 ��ת����startIndexλ��endIndex.
 11	boolean get(int index)
 ����ָ����������λ�ĵ�ǰ״̬��
 12	BitSet get(int startIndex, int endIndex)
 ����һ��BitSet�У��������ı��ؽ���startIndex��endIndex.1�����ö��󲻱��ı䡣
 13	int hashCode( )
 ���ص��ö���Ĺ�ϣ���롣
 14	boolean intersects(BitSet bitSet)
 ���������һ���Ե��ö����bitSet����ӦλΪ1���򷵻�true��
 15	boolean isEmpty( )
 ����true����ڵ��ö����е�����λ��Ϊ�㡣
 16	int length( )
 ���ص����е���BitSet�е���������ı����������ֵ�������1λ��λ�þ����ġ�
 17	int nextClearBit(int startIndex)
 �����¸�����λ����������������һ����λ��������startIndexָ����������ʼ
 18	int nextSetBit(int startIndex)
 ������һ��λ��������һ��1���أ�������������startIndexָ����������ʼ�����û��λ�����ã��򷵻�1��
 19	void or(BitSet bitSet)
 ORֵ���õ�����BitSet�ж���ͨ��BitSetָ������������õ����ö���
 20	void set(int index)
 ������indexָ����λ��
 21	void set(int index, boolean v)
 ������indexָ����v. trueΪ���ݵ�ֵ��λ����λ��false�������λ��
 22	void set(int startIndex, int endIndex)
 ����λ����startIndex��endIndex.1��
 23	void set(int startIndex, int endIndex, boolean v)
 ����λ��startIndex��endIndex.1�����������ݵ�ֵv����λ�����λΪfalse��
 24	int size( )
 ����λ�ڵ���BitSet�ж����������
 25	String toString( )
 �����ַ����൱�ڵ���BitSet�еĶ���
 26	void xor(BitSet bitSet)
 ��������BitSet�ж������������BitSetָ���������ŵ����ö���
 *
 * Created by zhaocl1 on 2017/3/28.
 */
public class BitSetDemo {

    /**
     * ��һ���ַ���������char
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
     * ������ �����޸���һ������1����Ȼ�����������1���������⣬���ܱ�������Ȼ������(��0���⣩������֮Ϊ����(������ �����Ϊ����
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
     * ��������
     */
    public static void sortArray() {
        int[] array = new int[] { 423, 700, 9999, 2323, 356, 6400, 1,2,3,2,2,2,2 };
        BitSet bitSet = new BitSet(2 << 13);
        // ��Ȼ�����Զ����ݣ��������ڹ���ʱָ�������С,Ĭ��Ϊ64
        System.out.println("BitSet size: " + bitSet.size());

        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i]);
        }
        int bitlen = bitSet.cardinality();//�޳��ظ����ֺ��Ԫ�ظ���
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
        //��ֱ�ӵ���BitSet��bitΪtrue��Ԫ��iterate over the true bits in a BitSet
        for(int i=bitSet.nextSetBit(0);i>=0;i = bitSet.nextSetBit(i+1)){
            System.out.print(i + "\t");
        }
        for(int i=0;i<bitSet.size();i++){
            System.out.println(bitSet.nextSetBit(i) + ",");
        }
    }

    /**
     * ��BitSet����ת��ΪByteArray
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
     * ��ByteArray����ת��ΪBitSet
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
     * ��ʹ��ʾ��
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

        //BitSet��Byte���黥תʾ��
        BitSet bitSet = new BitSet();
        bitSet.set(3, true);
        bitSet.set(98, true);
        System.out.println(bitSet.size() + "," + bitSet.cardinality());
        //��BitSet����ת��byte����
        byte[] bytes = BitSetDemo.bitSet2ByteArray(bitSet);
        System.out.println(Arrays.toString(bytes));

        //�ڽ�byte����ת����
        bitSet = BitSetDemo.byteArray2BitSet(bytes);
        System.out.println(bitSet.size()+","+bitSet.cardinality());
        System.out.println(bitSet.get(3));
        System.out.println(bitSet.get(98));
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i+"\t");
        }
    }
}
