# OpenRewrite-Data-Scraping

This API's will give you the properties that have been deprecated from Spring boot 2.7 to 3.0

  APIS : 
    1. GET/ -> /openRewrite/propertiesWithProperty - Get the properties which have been deprecated along with the replaced properties.
    2. GET/ -> /openRewrite/propertiesWithComment - Get the deprecated propeties which don't have any replacement properties but some descriptions are there.
    3. GET/ -> /openRewrite/propertiesWithProperty/download - Get the excel file for the deprecated properties along with the new properties.
    4. GET/ -> openRewrite/propertiesWithComment/download - Get the excel file for the deprecated properties with the descriptions.
