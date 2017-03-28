package com.zcl.work.DirectByteBufferStudy;

import java.util.BitSet;

/**
 * public void set(int pos): λ��pos����λ����Ϊtrue��
 public void set(int bitIndex, boolean value) ��ָ����������λ����Ϊָ����ֵ��
 public void clear(int pos): λ��pos����λ����Ϊfalse��
 public void clear() : ���� BitSet �е�����λ����Ϊ false��
 public int cardinality() ���ش� BitSet ������Ϊ true ��λ����
 public boolean get(int pos): ����λ����pos����λֵ��
 public void and(BitSet other): otherͬ����λ������������������Ϊ����λ������ֵ��
 public void or(BitSet other): otherͬ����λ�����л�����������Ϊ����λ������ֵ��
 public void xor(BitSet other): otherͬ����λ�������������������Ϊ����λ������ֵ��
 public void andNot(BitSet set) ����� BitSet �����е�λ,set - �������δ� BitSet �� BitSet
 public int size(): ���ش� BitSet ��ʾλֵʱʵ��ʹ�ÿռ��λ����
 public int length() ���ش� BitSet �ġ��߼���С����BitSet ���������λ�������� 1��
 public int hashCode(): ���ظü���Hash �룬 �����ͬ�����е���λֵ�йء�
 public boolean equals(Object other): ���other�е���λͬ�����е���λ��ͬ������true��
 public Object clone() ��¡�� BitSet������һ����֮��ȵ��� BitSet��
 public String toString() ���ش�λ set ���ַ�����ʾ��ʽ��
 *
 *
 * java.util.BitSet���ṩ���������㷨��
 ������һ�����֣���Ҫ�洢��source=[3,5,6,9]
 ��int����Ҫ4*4���ֽڡ�
 java.util.BitSet���Դ�true/false��
 �����java.util.BitSet������ٺܶ࣬��ԭ���ǣ�
 1�����ҳ����������ֵmaxvalue=9
 2������һ��BitSet bs,����size��maxvalue+1=10
 3����������source��bs[source[i]]���ó�true.
 ����ֵ�ǣ�
 (0Ϊfalse;1Ϊtrue)
 bs [0,0,0,1,0,1,1,0,0,1]
           3,  5,6,    9
 ����һ������Ҫint����Ҫռ4�ֽڹ�32λ����������ֻ����1λ��
 ����32:1
 ������ʡ���˺ܴ�ռ䡣
 *
 * Created by zhaocl1 on 2017/3/28.
 */
public class BitSetStudy {

    /**
     * ˵��Ĭ�ϵĹ��캯������һ��64λ��BitSet��ֵ����false��
     * �����Ҫ�õ�λ������Ĭ��size,����������64λ�������Ǳ���
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
     * ע��������ظ������ֹ��ˣ�����˵��һ�����ֳ��ֹ�����2�ε������ǳ�1.
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
     * ȡ���ֵ
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
    //��ֵ
    public static void putVauleIntoBitSet(int[] shu,BitSet bs){
        for(int i=0;i<shu.length;i++){
            bs.set(shu[i],true);
        }
    }

    //true,false����1,0Ϊ�˺ÿ�
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
