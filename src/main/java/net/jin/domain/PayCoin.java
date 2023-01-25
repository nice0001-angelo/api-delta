package net.jin.domain;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "historyNo")
@ToString
@Entity
@Table(name = "pay_coin_history")
public class PayCoin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyNo;

	private Long userNo;
	private Long itemId;

	@Transient
	private String itemName;

	private long amount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
