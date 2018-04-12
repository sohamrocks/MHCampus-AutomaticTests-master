//package com.mcgraw.test.automation.framework.core.common;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import org.jdom2.Attribute;
//import org.jdom2.Content;
//import org.jdom2.Document;
//import org.jdom2.Element;
//import org.jdom2.JDOMException;
//import org.jdom2.input.SAXBuilder;
//
//
//public class XmlParser {
//
//	SAXBuilder saxBuilder;
//	File inputFile;
//	org.jdom2.Document document;
//
//public XmlParser(String filePath) throws JDOMException, IOException
//{
//    inputFile = new File(filePath);
//    saxBuilder = new SAXBuilder();
//    this.document = saxBuilder.build(inputFile);
//}	
//	
//   public String getCustomIdByAttributeValue(String attributeValue) {
//
//      try {
//    	  
//         Element rootElement = this.document.getRootElement();
//         List<Element> childrenList = rootElement.getChildren();
//      
//            for (int i = 0; i < childrenList.size(); i++) {	 
//               Element element = childrenList.get(i);
//               if (element.getName() != "custom") {
//            	   continue;
//               }
//               
//               List<Element> customList = element.getChildren();
//               
//               for (int j = 0; j < childrenList.size(); j++) {	 
//	               if(customList.get(j).getAttributes().get(0).getValue().equals(attributeValue))
//	               {
//	            	   return customList.get(j).getContent().get(0).getValue();
//	               }
//               }
//            }
//            return "";
//      }
//      catch(Exception e) {
//       e.printStackTrace();
//       return "";
//    }      
//   }
//}
