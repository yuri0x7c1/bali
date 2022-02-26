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

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.AbstractNumberField;

import com.vaadin.event.FieldEvents;

/**
 * @author yuri0x7c1
 */
public class BigIntegerField extends AbstractNumberField<BigIntegerField, BigInteger> {

    public BigIntegerField() {
        setSizeUndefined();
    }

    public BigIntegerField(String caption) {
        setCaption(caption);
    }

    @Override
    protected void userInputToValue(String str) {
        if (StringUtils.isNotBlank(str)) {
            value = new BigInteger(str);
        } else {
            value = null;
        }
    }

    @Override
    public BigIntegerField withBlurListener(FieldEvents.BlurListener listener) {
        return (BigIntegerField) super.withBlurListener(listener);
    }

    @Override
    public BigIntegerField withFocusListener(FieldEvents.FocusListener listener) {
        return (BigIntegerField) super.withFocusListener(listener);
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

}