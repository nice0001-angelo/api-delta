package net.jin.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(value = "hibernateLazyInitializer")
@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(of = "groupCode")
@Table(name = "code_group")
public class CodeGroup {

	@Id
	@Column(length = 3)
	private String groupCode;

	@Column(length = 30, nullable = false)
	private String groupName;

	@Column(length = 1)
	private String useYn = "Y";

	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "groupCode")
	private List<CodeDetail> codeDetails;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
