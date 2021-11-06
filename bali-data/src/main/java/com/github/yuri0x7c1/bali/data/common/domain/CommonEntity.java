package com.github.yuri0x7c1.bali.data.common.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class CommonEntity {
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";

	/**
	 * JPA Version
	 */
	@Getter
	@Version
	@Column(name="v", columnDefinition = "bigint default 0")
	protected Long v;

	@Getter
    @Column(name="created_at")
    protected LocalDateTime createdAt;

	@Getter
    @Column(name="created_by")
    protected String createdBy;

	@Getter
    @Column(name="updated_at")
    protected LocalDateTime updatedAt;

	@Getter
    @Column(name="updated_by")
    protected String updatedBy;

	@Getter
	@Setter
    @Column(name="valid_from")
    protected LocalDateTime validFrom;

	@Getter
	@Setter
    @Column(name="valid_till")
    protected LocalDateTime validTill;

	@PrePersist
	public void setCreationTime() {
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	public void setUpdateTime() {
		updatedAt = LocalDateTime.now(ZoneOffset.UTC);
	}

	/**
	 * Gets the default order by.
	 *
	 * @return the default order by
	 */
	public static String[] getDefaultOrderBy() {
		return new String[] {"createdAt"};
	}

	/**
	 * Gets the default order.
	 *
	 * @return the default order
	 */
	public static String getDefaultOrder() {
		return ORDER_ASC;
	}

}
