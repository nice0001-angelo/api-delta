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
@EqualsAndHashCode(of = "userItemNo")
@ToString
@Entity
@Table(name = "user_item")
public class UserItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userItemNo;

	private Long userNo;
	private Long itemId;

	@Transient
	private String itemName;
	@Transient
	private Integer price;
	@Transient
	private String description;
	@Transient
	private String pictureUrl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
