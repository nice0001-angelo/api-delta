package net.jin.common.domain;

import java.util.Date;

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
@EqualsAndHashCode(of = "logNo")
@ToString
@Entity
@Table(name = "performance_log")
public class PerformanceLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logNo;

	@Column(length = 50, nullable = false)
	private String signatureName;

	@Column(length = 100, nullable = false)
	private String signatureTypeName;

	private Long durationTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private Date regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private Date updDate;

}
