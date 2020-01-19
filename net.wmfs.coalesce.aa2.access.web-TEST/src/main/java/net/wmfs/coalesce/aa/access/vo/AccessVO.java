package net.wmfs.coalesce.aa.access.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Accessors;

// equals should use every attribute for EqualsVerifier
// @EqualsAndHashCode(of = {"empNo"}) @Getter @Setter @AllArgsConstructor @ToString(exclude = {"id"}, includeFieldNames = false) @Accessors(fluent = true)
// @Value
// @Data
@EqualsAndHashCode @Getter @Setter @AllArgsConstructor @ToString @Accessors(fluent = true)
public final class AccessVO {
	// error on setting final
	// @Setter
	private final @NonNull Long id;

	// @Setter
	// final added for EqualsVerifier
	private final Long empNo;

	// @Getter
	private final String taskGroup;

	private final String org;

	private final String dutyType;

	private final String justification;

	private final String isApproved;
}
