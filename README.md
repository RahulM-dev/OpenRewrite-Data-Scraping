# OpenRewrite-Data-Scraping

This API provides information about the properties deprecated from Spring Boot 2.7 to 3.0.

## APIs:

1. **GET** `/openRewrite/propertiesWithProperty`  
   - Retrieves the properties that have been deprecated along with their replacement properties.

2. **GET** `/openRewrite/propertiesWithComment`  
   - Retrieves the deprecated properties that don't have replacement properties but include descriptions.

3. **GET** `/openRewrite/propertiesWithProperty/download`  
   - Downloads an Excel file containing the deprecated properties along with their replacement properties.

4. **GET** `/openRewrite/propertiesWithComment/download`  
   - Downloads an Excel file containing the deprecated properties with their descriptions.

