package com.ljh.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

public class JacksonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	 
	public static String bean2Json(Object obj) throws Exception {
		StringWriter sw = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
		mapper.writeValue(gen, obj);
		gen.close();
		return sw.toString();
	}
 
	public static <T> T json2Bean(String jsonStr, Class<T> objClass)
			throws Exception {
		return mapper.readValue(jsonStr, objClass);
	}

}
