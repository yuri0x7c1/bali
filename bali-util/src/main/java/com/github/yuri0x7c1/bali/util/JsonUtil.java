/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.util;

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