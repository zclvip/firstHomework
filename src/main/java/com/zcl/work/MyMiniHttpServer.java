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

    //可以向脚本中传递变量，使得Java代码可以和脚本代码交互
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

    //动态调用 上面的例子中定义了一个JavaScript函数max_num，可以通过Invocable接口来多次调用脚本库中的函数，Invocable接口是 ScriptEngine可选实现的接口
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
            Bindings bindings = engine.createBindings(); //Local级别的Binding
            String script = "function add(op1,op2){return op1+op2} add(a, b)"; //定义函数并调用
            CompiledScript JSFunction = compilable.compile(script); //解析编译脚本函数
            bindings.put("a", 1);
            bindings.put("b", 2); //通过Bindings加入参数
            Object result = JSFunction.eval(bindings);
            System.out.println("result="+result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入
        }
        catch (ScriptException e) {}
    }

    public static void main(String[] args){
//        helloworld();
        requestTest();
    }
}
