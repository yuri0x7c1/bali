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

package com.github.yuri0x7c1.bali.data.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @author yuri0x7c1
 *
 */
public class CommonMessages {

	List<String> successMessages = new ArrayList<>();
	
	List<String> warningMessages = new ArrayList<>();
	
	List<String> errorMessages = new ArrayList<>();

	public List<String> getSuccessMessages() {
		return Collections.unmodifiableList(successMessages);
	}

	public void setSuccessMessages(List<String> successMessages) {
		Objects.requireNonNull(successMessages);
		this.successMessages = successMessages;
	}
	
	public void addSuccessMessage(String successMessage) {
		this.successMessages.add(successMessage);
	}
	
	public void addSuccessMessages(String... successMessages) {
		this.successMessages.addAll(Arrays.asList(successMessages));
	}
	
	public CommonMessages withSuccessMessage(String successMessage) {
		this.successMessages.add(successMessage);
		return this;
	}
	
	public CommonMessages withSuccessMessages(String... successMessages) {
		this.successMessages.addAll(Arrays.asList(successMessages));
		return this;
	}
	
	public boolean containsSuccessMessages() {
		return CollectionUtils.isNotEmpty(successMessages);
	}

	public List<String> getWarningMessages() {
		return Collections.unmodifiableList(warningMessages);
	}

	public void setWarningMessages(List<String> warningMessages) {
		Objects.requireNonNull(warningMessages);
		this.warningMessages = warningMessages;
	}
	
	public void addWarningMessage(String warningMessage) {
		this.warningMessages.add(warningMessage);
	}
	
	public void addWarningMessages(String... warningMessages) {
		this.warningMessages.addAll(Arrays.asList(warningMessages));
	}
	
	public CommonMessages withWarningMessage(String warningMessage) {
		this.warningMessages.add(warningMessage);
		return this;
	}
	
	public CommonMessages withWarningMessages(String... warningMessages) {
		this.warningMessages.addAll(Arrays.asList(warningMessages));
		return this;
	}
	
	public boolean containsWarningMessages() {
		return CollectionUtils.isNotEmpty(warningMessages);
	}

	public List<String> getErrorMessages() {
		return Collections.unmodifiableList(errorMessages);
	}

	public void setErrorMessages(List<String> errorMessages) {
		Objects.requireNonNull(errorMessages);
		this.errorMessages = errorMessages;
	}
	
	public void addErrorMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}
	
	public void addErrorMessages(String... errorMessages) {
		this.errorMessages.addAll(Arrays.asList(errorMessages));
	}
	
	public CommonMessages withErrorMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
		return this;
	}
	
	public CommonMessages withErrorMessages(String... errorMessages) {
		this.errorMessages.addAll(Arrays.asList(errorMessages));
		return this;
	}
	
	public boolean containsErrorMessages() {
		return CollectionUtils.isNotEmpty(errorMessages);
	}
}
