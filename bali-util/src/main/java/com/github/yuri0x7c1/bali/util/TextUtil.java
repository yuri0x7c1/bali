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

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author yuri0x7c1
 *
 */
public class TextUtil {
	/**
	 * Create phrase from camel case.
	 *
	 * @param name the name
	 * @return the string
	 */
	public static String createPhraseFromCamelCase(String name) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(name);
		StringBuilder phrase = new StringBuilder();
		for (int c = 0; c < words.length; c++) {
			phrase.append(words[c].toLowerCase());
			if (c != words.length-1) {
				phrase.append(' ');
			}
		}
		return phrase.toString();
	}

	/**
	 * Create caption from camel case.
	 *
	 * @param name the name
	 * @return the string
	 */
	public static String createCaptionFromCamelCase(String name) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(name);
		StringBuilder phrase = new StringBuilder();
		for (int c = 0; c < words.length; c++) {
			phrase.append(StringUtils.capitalize(words[c].toLowerCase()));
			if (c != words.length-1) {
				phrase.append(' ');
			}
		}
		return phrase.toString();
	}

    /**
     * Require non blank.
     *
     * @param str the str
     * @return the string
     */
    public static String requireNonBlank(String str) {
        if (StringUtils.isBlank(str))
            throw new RuntimeException("Non blank string is required!");
        return str;
    }

    public static String joinWithDot(String... elements) {
    	return StringUtils.join(elements, ".");
    }
}
