package net.jin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import net.jin.common.security.domain.*;
import net.jin.domain.*;
import net.jin.prop.*;
import net.jin.service.*;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

	private final ItemService itemService;

	private final ShopProperties shopProperties;

	private final MemberService memberService;

	private final UserItemService userItemService;

	private final MessageSource messageSource;

	@GetMapping
	public ResponseEntity<List<Item>> list() throws Exception {
		return new ResponseEntity<List<Item>>(itemService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Item> register(@RequestPart("item") String itemString,
			@RequestPart("file") MultipartFile originalImageFile, @RequestPart("file2") MultipartFile previewImageFile)
			throws Exception {
		log.info("itemString: " + itemString);

		Item item = new ObjectMapper().readValue(itemString, Item.class);

		String itemName = item.getItemName();
		String description = item.getDescription();

		if (itemName != null) {
			log.info("item.getItemName(): " + itemName);

			item.setItemName(itemName);
		}

		if (description != null) {
			log.info("item.getDescription(): " + description);

			item.setDescription(description);
		}

		item.setPicture(originalImageFile);
		item.setPreview(previewImageFile);

		MultipartFile pictureFile = item.getPicture();
		MultipartFile previewFile = item.getPreview();

		if (pictureFile != null) {
			log.info("register pictureFile != null " + pictureFile.getOriginalFilename());
		} else {
			log.info("register pictureFile == null ");
		}

		String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
		String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

		item.setPictureUrl(createdPictureFilename);
		item.setPreviewUrl(createdPreviewFilename);

		itemService.register(item);

		log.info("register member.getItemId() = " + item.getItemId());

		Item createdItem = new Item();
		createdItem.setItemId(item.getItemId());

		return new ResponseEntity<Item>(createdItem, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{itemId}")
	public ResponseEntity<Item> modify(@PathVariable("itemId") Long itemId, @RequestPart("item") String itemString,
			@RequestPart(name = "file", required = false) MultipartFile originalImageFile,
			@RequestPart(name = "file2", required = false) MultipartFile previewImageFile) throws Exception {

		log.info("itemString: " + itemString);

		Item item = new ObjectMapper().readValue(itemString, Item.class);

		item.setItemId(itemId);

		String itemName = item.getItemName();
		String description = item.getDescription();

		if (itemName != null) {
			log.info("item.getItemName(): " + itemName);

			item.setItemName(itemName);
		}

		if (description != null) {
			log.info("item.getDescription(): " + description);

			item.setDescription(description);
		}

		item.setPicture(originalImageFile);
		item.setPreview(previewImageFile);

		MultipartFile pictureFile = item.getPicture();

		if (pictureFile != null && pictureFile.getSize() > 0) {
			String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());

			item.setPictureUrl(createdFilename);
		} else {
			Item oldItem = this.itemService.read(item.getItemId());
			item.setPictureUrl(oldItem.getPictureUrl());
		}

		MultipartFile previewFile = item.getPreview();

		if (previewFile != null && previewFile.getSize() > 0) {
			String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

			item.setPreviewUrl(createdFilename);
		} else {
			Item oldItem = this.itemService.read(item.getItemId());
			item.setPreviewUrl(oldItem.getPreviewUrl());
		}

		itemService.modify(item);

		Item createdItem = new Item();
		createdItem.setItemId(item.getItemId());

		return new ResponseEntity<Item>(createdItem, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> remove(@PathVariable("itemId") Long itemId) throws Exception {
		itemService.remove(itemId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();

		String createdFileName = uid.toString() + "_" + originalName;

		File target = new File(shopProperties.getUploadPath(), createdFileName);

		FileCopyUtils.copy(fileData, target);

		return createdFileName;
	}

	@GetMapping("/display")
	public ResponseEntity<byte[]> displayFile(@RequestParam("itemId") Long itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		String fileName = itemService.getPicture(itemId);

		log.info("displayFile itemId = " + itemId);
		log.info("displayFile fileName = " + fileName);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(shopProperties.getUploadPath() + File.separator + fileName);

			if (mType != null) {
				headers.setContentType(mType);
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	@GetMapping("/preview")
	public ResponseEntity<byte[]> previewFile(@RequestParam("itemId") Long itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		String fileName = itemService.getPreview(itemId);

		log.info("displayFile itemId = " + itemId);
		log.info("displayFile fileName = " + fileName);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(shopProperties.getUploadPath() + File.separator + fileName);

			if (mType != null) {
				headers.setContentType(mType);
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	private MediaType getMediaType(String formatName) {
		if (formatName != null) {
			if (formatName.equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}

			if (formatName.equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}

			if (formatName.equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}

		return null;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/download/{itemId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("itemId") Long itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		String fullName = itemService.getPicture(itemId);

		try {
			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(shopProperties.getUploadPath() + File.separator + fullName);

			String fileName = fullName.substring(fullName.indexOf("_") + 1);

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}

		return entity;
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<Item> read(@PathVariable("itemId") Long itemId) throws Exception {
		Item item = itemService.read(itemId);

		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping(value = "/buy/{itemId}", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> buy(@PathVariable("itemId") Long itemId,
			@AuthenticationPrincipal CustomUser customUser) throws Exception {
		Long userNo = customUser.getUserNo();

		log.info("buy userNo = " + userNo);

		Member member = memberService.read(userNo);

		member.setCoin(memberService.getCoin(userNo));

		Item item = itemService.read(itemId);

		userItemService.register(member, item);

		String message = messageSource.getMessage("item.purchaseComplete", null, Locale.KOREAN);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

}
