package com.github.yuri0x7c1.bali.data.common.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded=true, callSuper=false)
@MappedSuperclass
public abstract class CommonEntityWithLongPk extends CommonEntity {
	/**
	 * Id
	 */
	@EqualsAndHashCode.Include
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	Long id;
}