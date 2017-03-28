package com.zcl.work.parser;


import com.zcl.work.parser.exception.ScriptFormatException;
import com.zcl.work.request.UrlParameter;
import com.zcl.work.scriptengine.ScriptEngineGroovy;

public class IfElseScriptParser {
	private static final String RETURN_TRUE = "\n return true; \n";
	private static final String RETURN_FALSE = "\n return false; \n";
	private static final String TOKEN_START = "<%";
	private static final String TOKEN_END = "%>";
	private static final int TOKEN_SIZE = 2;
	/**
	 * 输入脚本格式
	 <%if (request.getParameter('user').equals('aa') && request.getParameter('password').equals('bb')) { %>
      yes
     <%} else {%>
      no
     <% } %>
	 */
	public static String parse(UrlParameter param, String script) throws Exception{
		int pos = 0;
		int ifStart = -1, ifEnd = -1, elseStart = -1, elseEnd = -1, endIfStart = -1, endIfEnd = -1;
		String htmlForTrue;		//if条件满足时需要输出的HTML信息
		String htmlForFalse;	//elese分支需要输出的HTML信息
		
		if(null == script){
			throw new NullPointerException("脚本内容为空");
		}
		
		pos = script.indexOf(TOKEN_START, pos);
		if (-1 != pos){
			ifStart = pos + TOKEN_SIZE;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		}
		
		pos = script.indexOf(TOKEN_END, pos + TOKEN_SIZE);
		if (-1 != pos){
			ifEnd = pos -1;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		}
		
		pos = script.indexOf(TOKEN_START, pos + TOKEN_SIZE);
		if (-1 != pos){
			elseStart = pos + TOKEN_SIZE;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		}

		pos = script.indexOf(TOKEN_END, pos + TOKEN_SIZE);
		if (-1 != pos){
			elseEnd = pos -1;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		}

		pos = script.indexOf(TOKEN_START, pos + TOKEN_SIZE);
		if (-1 != pos){
			endIfStart = pos + TOKEN_SIZE;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		}

		pos = script.indexOf(TOKEN_END, pos + TOKEN_SIZE);
		if (-1 != pos){
			endIfEnd = pos -1;
		} else {
			throw new ScriptFormatException("脚本格式错误");
		}
		
		htmlForTrue = script.substring(ifEnd + TOKEN_SIZE + 1, elseStart - TOKEN_SIZE -1);
		htmlForFalse = script.substring(elseEnd + TOKEN_SIZE + 1, endIfStart - TOKEN_SIZE -1);
		
		//使用return true,或者 return false替换脚本中的HTML片段
		StringBuilder groovy = new StringBuilder();
		groovy.append(script.substring(ifStart, ifEnd + 1));
		groovy.append(RETURN_TRUE);
		groovy.append(script.substring(elseStart, elseEnd + 1 ));
		groovy.append(RETURN_FALSE);
		groovy.append(script.substring(endIfStart, endIfEnd + 1));
		
		//根据groovy脚本的执行结果返回对应的HTML片段
		boolean grooVyResult = ScriptEngineGroovy.evalIfElseScript(param, groovy.toString());
		return grooVyResult? htmlForTrue: htmlForFalse;
	}
}
