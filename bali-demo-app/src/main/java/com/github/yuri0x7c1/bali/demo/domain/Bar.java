package com.github.yuri0x7c1.bali.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = "id")
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Bar {
	@Getter
	@GeneratedValue
	@Id
	Integer id;

	@Getter
	@Setter
	String value;
}
