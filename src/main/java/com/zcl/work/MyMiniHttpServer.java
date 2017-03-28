package com.zcl.work;

import javax.script.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zhaocl1 on 2017/3/6.
 */
public class MyMiniHttpServer {

    public static void geteEine(){
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory f : factories) {
            System.out.println(
                    "egine name:"+f.getEngineName()+"\r\n"+
                            ",engine version:"+f.getEngineVersion()+"\r\n"+
                            ",language name:"+f.getLanguageName()+"\r\n"+
                            ",language version:"+f.getLanguageVersion()+"\r\n"+
                            ",names:"+f.getNames()+"\r\n"+
                            ",mime:"+f.getMimeTypes()+"\r\n"+
                            ",extension:"+f.getExtensions()+"\r\n");
        }
    }

    public static void helloworld(){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        String script = "print ('hello script')";
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //������ű��д��ݱ�����ʹ��Java������Ժͽű����뽻��
    public static void transParam(){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("a",4);
        engine.put("b",6);

        try {
            Object maxnum = engine.eval("function max_num(a,b){return (a>b)?a:b;}max_num(a,b);");
            System.out.println("maxNum="+maxnum);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    //��̬���� ����������ж�����һ��JavaScript����max_num������ͨ��Invocable�ӿ�����ε��ýű����еĺ�����Invocable�ӿ��� ScriptEngine��ѡʵ�ֵĽӿ�
    public static void invocableTest(){
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine= engineManager.getEngineByName("js");
        try {
            engine.eval("function max_num(a,b){return (a>b)?a:b;}");
            Invocable invocable = (Invocable)engine;
            Object maxNum = invocable.invokeFunction("max_num",4,6);
            System.out.println("maxNum="+maxNum);
            maxNum = invocable.invokeFunction("max_num",8,9);
            System.out.println("maxNum="+maxNum);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void requestTest(){
        try {

            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            Compilable compilable = (Compilable) engine;
            Bindings bindings = engine.createBindings(); //Local�����Binding
            String script = "function add(op1,op2){return op1+op2} add(a, b)"; //���庯��������
            CompiledScript JSFunction = compilable.compile(script); //��������ű�����
            bindings.put("a", 1);
            bindings.put("b", 2); //ͨ��Bindings�������
            Object result = JSFunction.eval(bindings);
            System.out.println("result="+result); //���û����ŵĽű���������Bindings��Ϊ������������
        }
        catch (ScriptException e) {}
    }

    public static void main(String[] args){
//        helloworld();
        requestTest();
    }
}
