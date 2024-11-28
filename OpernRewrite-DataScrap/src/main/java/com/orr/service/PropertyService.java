package com.orr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.orr.model.SBDeprPropertyWComment;
import com.orr.model.SBDeprPropertyWNewProperty;

public interface PropertyService {
	
	public List<SBDeprPropertyWNewProperty> getOldProeprtyWithNewProperty();
	public List<SBDeprPropertyWComment> getPropetyWithComment();
	public ByteArrayOutputStream generateExcelForPropertiesWithProperty();
	public ByteArrayOutputStream generateExcelForPropertiesWithComment();
	

}
