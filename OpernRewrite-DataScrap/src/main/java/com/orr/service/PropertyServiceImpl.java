package com.orr.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.orr.model.SBDeprPropertyWComment;
import com.orr.model.SBDeprPropertyWNewProperty;

@Service
public class PropertyServiceImpl implements PropertyService {

	String url = "https://docs.openrewrite.org/recipes/java/spring/boot3/springbootproperties_3_0";

	@Override
	public List<SBDeprPropertyWNewProperty> getOldProeprtyWithNewProperty() {

		List<SBDeprPropertyWNewProperty> allProperties = new ArrayList<>();
		try {

			Document document = Jsoup.connect(url).get();

			Elements recipes = document.select("ul > li:has(ul)");

			for (Element recipe : recipes) {
				Element oldKeyElement = recipe.selectFirst("ul > li:contains(oldPropertyKey) code");
				Element newKeyElement = recipe.selectFirst("ul > li:contains(newPropertyKey) code");

				if (oldKeyElement != null) {
					String oldPropertyKey = oldKeyElement.text();
					String newPropertyKey = newKeyElement.text();

					SBDeprPropertyWNewProperty sbDPWN = new SBDeprPropertyWNewProperty();
					sbDPWN.setOldPropertyKey(oldPropertyKey);
					sbDPWN.setNewPropertyKey(newPropertyKey);
					allProperties.add(sbDPWN);
				}
			}
			System.out.println(allProperties.size());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return allProperties;
	}

	@Override
	public List<SBDeprPropertyWComment> getPropetyWithComment() {

		List<SBDeprPropertyWComment> allProperties = new ArrayList<>();

		try {
			Document document = Jsoup.connect(url).get();
			Elements recipes = document.select("ul > li:has(ul)");

			for (Element recipe : recipes) {
				Element propertyKeyElement = recipe.selectFirst("ul > li:contains(propertyKey) code");
				Element commentElement = recipe.selectFirst("ul > li:contains(comment) code");

				if (propertyKeyElement != null && commentElement != null) {
					String propertyKey = propertyKeyElement.text();
					String comment = commentElement.text();

					SBDeprPropertyWComment sbDPWC = new SBDeprPropertyWComment();
					sbDPWC.setPropertyKey(propertyKey);
					sbDPWC.setComment(comment);
					allProperties.add(sbDPWC);
				}
			}
			System.out.print(allProperties.size());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return allProperties;
	}

	@Override
	public ByteArrayOutputStream generateExcelForPropertiesWithProperty() {
		List<SBDeprPropertyWNewProperty> properties = getOldProeprtyWithNewProperty();
		return createExcelFile(properties, "Old Property Key", "New Property Key");
	}

	@Override
	public ByteArrayOutputStream generateExcelForPropertiesWithComment() {
		List<SBDeprPropertyWComment> properties = getPropetyWithComment();
		return createExcelFile(properties, "Property Key", "Comment");
	}

	private <T> ByteArrayOutputStream createExcelFile(List<T> data, String col1Header, String col2Header) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Data");

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue(col1Header);
			header.createCell(1).setCellValue(col2Header);

			int rowIdx = 1;
			for (T item : data) {
				Row row = sheet.createRow(rowIdx++);
				if (item instanceof SBDeprPropertyWNewProperty) {
					SBDeprPropertyWNewProperty prop = (SBDeprPropertyWNewProperty) item;
					row.createCell(0).setCellValue(prop.getOldPropertyKey());
					row.createCell(1).setCellValue(prop.getNewPropertyKey());
				} else if (item instanceof SBDeprPropertyWComment) {
					SBDeprPropertyWComment prop = (SBDeprPropertyWComment) item;
					row.createCell(0).setCellValue(prop.getPropertyKey());
					row.createCell(1).setCellValue(prop.getComment());
				}
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			return out;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error creating Excel file", e);
		}
	}

}
