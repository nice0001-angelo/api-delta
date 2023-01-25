package net.jin.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;

	@Column(length = 50, nullable = false)
	private String itemName;

	private Integer price;

	@Column(length = 250)
	private String description;

	@Transient
	private MultipartFile picture;

	@Column(length = 200)
	private String pictureUrl;

	@Transient
	private MultipartFile preview;

	@Column(length = 200)
	private String previewUrl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

}
