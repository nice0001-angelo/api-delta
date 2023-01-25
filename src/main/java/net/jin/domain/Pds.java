package net.jin.domain;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "pds")
public class Pds {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;

	private String itemName;

	private String description;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id")
	private List<PdsFile> pdsFiles = new ArrayList<PdsFile>();

	@Transient
	private String[] files;

	private Integer viewCnt = 0;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	private LocalDateTime regDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	private LocalDateTime updDate;

	public void addItemFile(PdsFile itemFile) {
		pdsFiles.add(itemFile);
	}

	public void clearItemFile() {
		pdsFiles.clear();
	}

}
