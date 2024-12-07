package com.orr.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.orr.model.SBDeprPropertyWComment;
import com.orr.model.SBDeprPropertyWNewProperty;
import com.orr.service.PropertyService;

@RestController
@RequestMapping("/openRewrite")
public class OpenRewriteController {

	@Autowired
	private PropertyService propertyService;

	@GetMapping("/propertiesWithProperty")
	public List<SBDeprPropertyWNewProperty> getPropertyList() {
		return this.propertyService.getOldProeprtyWithNewProperty();
	}

	@GetMapping("/propertiesWithComment")
	public List<SBDeprPropertyWComment> getProperties() {
		return this.propertyService.getPropetyWithComment();
	}

	@GetMapping("/propertiesWithProperty/download")
	public ResponseEntity<byte[]> downloadPropertiesWithProperty() {
		try (ByteArrayOutputStream out = propertyService.generateExcelForPropertiesWithProperty()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=properties_with_new_property.xlsx");
			return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/propertiesWithComment/download")
	public ResponseEntity<byte[]> downloadPropertiesWithComment() {
		try (ByteArrayOutputStream out = propertyService.generateExcelForPropertiesWithComment()) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=properties_with_comment.xlsx");
			return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/test")
	public String testString() {
		return "This is test string";
	}
}
