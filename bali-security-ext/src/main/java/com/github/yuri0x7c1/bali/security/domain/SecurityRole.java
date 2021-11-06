package com.github.yuri0x7c1.bali.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.github.yuri0x7c1.bali.data.common.domain.CommonEntityWithUuid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PROTECTED)
@Entity
public class SecurityRole extends CommonEntityWithUuid {

	/**
	 * Name
	 */
	@NotNull
	@Getter
	@Setter
	@Column(nullable=false, unique=true)
	String name;
}
