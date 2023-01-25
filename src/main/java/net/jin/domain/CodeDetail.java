package net.jin.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "groupCode", "codeValue" })
@Entity
@IdClass(CodeDetailId.class)
@Table(name = "code_detail")
public class CodeDetail {

	@Id
	@Column(length = 3)
	private String groupCode;

	@Id
	@Column(length = 3)
	private String codeValue;

	@Column(length = 30, nullable = false)
	private String codeName;

	private int sortSeq;

	@Column(length = 1)
	private String useYn = "Y";

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
