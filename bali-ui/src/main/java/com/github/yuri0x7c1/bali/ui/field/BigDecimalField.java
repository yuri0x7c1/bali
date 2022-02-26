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

package com.github.yuri0x7c1.bali.ui.field;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.AbstractNumberField;

import com.vaadin.event.FieldEvents;

/**
 * @author Matti Tahvonen
 * @author yuri0x7c1
 */
public class BigDecimalField extends AbstractNumberField<BigDecimalField, BigDecimal> {

	private String step = "any";
	private BigDecimal definedStep;

    public BigDecimalField() {
        setSizeUndefined();
    }

    public BigDecimalField(String caption) {
        setSizeUndefined();
        setCaption(caption);
    }

    @Override
    protected void userInputToValue(String str) {
        if (StringUtils.isNotBlank(str)) {
            // a hacky support for locales that use comma as a decimal separator
            // some browser do the conversion already on the browser, IE don't
            str = str.replaceAll(",", ".");
            value = new BigDecimal(str);
        } else {
            value = null;
        }
    }

    @Override
    public BigDecimalField withBlurListener(FieldEvents.BlurListener listener) {
        return (BigDecimalField) super.withBlurListener(listener);
    }

    @Override
    public BigDecimalField withFocusListener(FieldEvents.FocusListener listener) {
        return (BigDecimalField) super.withFocusListener(listener);
    }

	public BigDecimalField withStep(BigDecimal step) {
		this.definedStep = step;
		return this;
	}

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    protected void configureHtmlElement() {
		if (definedStep != null) {
			step = String.valueOf(definedStep);

		} else if (getValue() != null) {
			String s = String.valueOf(getValue()).replaceAll("\\d", "0").replaceAll("(\\d{1})$", "1");
			if ("any".equals(step) || s.length() > step.length()) {
				step = s;
			}
		}
		s.setProperty("step", step);

        s.setProperty("type", getHtmlFieldType());
        // prevent all but numbers or single dot after a number with a simple js
        s.setJavaScriptEventHandler("keypress",
                "function(e) {if(e.metaKey || e.ctrlKey) return true; var c = viritin.getChar(e); if( (c === '.' || c === ',') && this.value != '' && this.value.indexOf('.') == -1 && this.value.indexOf(',') == -1) return true; return c==null || /^[-\\d\\n\\t\\r]+$/.test(c);}");
        }

}