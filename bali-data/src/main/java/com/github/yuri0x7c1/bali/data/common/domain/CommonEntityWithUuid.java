package com.github.yuri0x7c1.bali.data.common.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded=true, callSuper=false)
@MappedSuperclass
public abstract class CommonEntityWithUuid extends CommonEntity {
	/**
	 * UUID
	 */
	@EqualsAndHashCode.Include
	@Getter
	@Setter
	@GeneratedValue(generator = "uuid_generator")
	@GenericGenerator(name = "uuid_generator", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	@Column(name="uuid")
	UUID uuid;
}