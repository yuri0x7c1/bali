package com.github.yuri0x7c1.bali.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
	public static final ObjectMapper MAPPER = new ObjectMapper();
	public static final ObjectMapper MAPPER_FORMATTED = new ObjectMapper();
	static {
		MAPPER_FORMATTED.enable(SerializationFeature.INDENT_OUTPUT);
	}

	/**
	 * Write value as string.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String writeValueAsString(Object value) {
		try {
			return MAPPER.writeValueAsString(value);
		}
		catch (Exception e) {
			return "";
		}
	}

	/**
	 * Write value as formatted string.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String writeValueAsFormattedString(Object value) {
		try {
			return MAPPER_FORMATTED.writeValueAsString(value);
		}
		catch (Exception e) {
			return "";
		}
	}

    /**
     * Read value.
     *
     * @param <T> the generic type
     * @param content the content
     * @param valueType the value type
     * @return the t
     */
    public static <T> T readValue(String content, Class<T> valueType) {
    	try {
			return MAPPER.readValue(content, valueType);
		} catch (Exception e) {
			return null;
		}
    }

}