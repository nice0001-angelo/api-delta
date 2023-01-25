package net.jin.common.domain;

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
@EqualsAndHashCode(of = "logNo")
@ToString
@Entity
@Table(name = "access_log")
public class AccessLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logNo;

	@Column(length = 200, nullable = false)
	private String requestUri;

	@Column(length = 100, nullable = false)
	private String className;

	@Column(length = 50, nullable = false)
	private String classSimpleName;

	@Column(length = 100, nullable = false)
	private String methodName;

	@Column(length = 50, nullable = false)
	private String remoteAddr;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
