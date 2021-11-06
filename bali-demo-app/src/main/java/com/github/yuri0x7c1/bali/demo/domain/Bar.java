package com.github.yuri0x7c1.bali.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@EqualsAndHashCode(of="id", callSuper=false)
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
