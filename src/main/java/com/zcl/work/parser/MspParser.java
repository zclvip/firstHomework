package com.zcl.work.parser;

import com.zcl.work.parser.exception.ScriptFormatException;
import com.zcl.work.request.UrlParameter;

public class MspParser {
	public static final String TOKEN_START = "<%";
	public static final String TOKEN_END = "%>";
	public static final int TOKEN_SIZE = 2;

	public static String parse(UrlParameter param, String msp) throws Exception{
		int scriptStart = -1, scriptEnd = -1;
		String head, script, foot;
		int pos = 0;
		if(null == msp){
			throw new NullPointerException("HTML内容为空");
		}
		
		pos = msp.indexOf(TOKEN_START, pos);
		if (-1 != pos){
			scriptStart = pos;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		} 
		
		pos = msp.lastIndexOf(TOKEN_END, msp.length());
		if (-1 != pos){
			scriptEnd = pos + TOKEN_SIZE;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		} 
		
		head = msp.substring(0, scriptStart);
		System.out.println("head:"+head);
		script = msp.substring(scriptStart, scriptEnd + 1);
		System.out.println("script:"+script);
		script = IfElseScriptParser.parse(param, script);
		System.out.println("script:"+script);
		foot = msp.substring(scriptEnd + 1);
		System.out.println("foot:"+foot);
		StringBuilder result = new StringBuilder();
		//拼接脚本之前的网页
		result.append(head);
		//拼接脚本计算结果网页
		result.append(script);
		//拼接脚本之后的网页
		result.append(foot);
		return result.toString();
	} 

}
