package com.zcl.work.scriptengine;

import com.zcl.work.request.UrlParameter;

import javax.script.*;

public class ScriptEngineGroovy {
	private static ScriptEngineManager factory = new ScriptEngineManager();  
	private static ScriptEngine engine = factory.getEngineByName("groovy");  

	public static boolean evalIfElseScript(UrlParameter req, String script) throws Exception{
		CompiledScript cs = ((Compilable) engine).compile(script);
		Bindings bindings = new SimpleBindings();
		bindings.put("request", req);
//		return (boolean)cs.eval(bindings);
		return true;
	}
}
