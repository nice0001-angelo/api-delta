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
@EqualsAndHashCode(of = "boardNo")
@ToString
@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo;

	@Column(length = 200, nullable = false)
	private String title;

	@Column(length = 50, nullable = false)
	private String writer;

	@Lob
	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
