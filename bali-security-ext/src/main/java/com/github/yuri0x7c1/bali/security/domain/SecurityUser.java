package com.github.yuri0x7c1.bali.security.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.github.yuri0x7c1.bali.data.common.domain.CommonEntityWithUuid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Security User
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
@Entity
public class SecurityUser extends CommonEntityWithUuid {
	/**
	 * Name
	 */
	@NotNull
	@Getter
	@Setter
	@Column(nullable=false, unique=true)
	String name;

	/**
	 * Password Hash
	 */
	@Getter
	@Setter
	@Column(name="password_hash")
	String passwordHash;

	/**
	 * Password
	 */
	@Getter
	@Setter
	@Transient
	String password;

	/**
	 * Password confirmation
	 */
	@Getter
	@Setter
	@Transient
	String passwordConfirmation;

	/**
	 * Groups
	 */
	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.EAGER)
	List<SecurityGroup> groups = new ArrayList<>();
}
