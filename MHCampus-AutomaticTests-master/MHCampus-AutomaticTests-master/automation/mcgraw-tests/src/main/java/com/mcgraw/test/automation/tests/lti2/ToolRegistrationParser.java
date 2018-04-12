/*package com.mcgraw.test.automation.tests.lti2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ToolRegistrationParser {

       public static void main(String[] args) throws IOException {
             // test
             String resfileName = "C:\\temp1\\tool_registration_res_success.html";
             byte[] bytes = Files.readAllBytes(Paths.get(resfileName));
           String response = new String(bytes, StandardCharsets.UTF_8);
             
             HashSet<String> capabilities = new HashSet();
             capabilities.add("basic-lti-launch-request");
              capabilities.add("Context.id");
          capabilities.add("Membership.role");
          capabilities.add("ResourceLink.id");
              capabilities.add("User.id");
             
             String registrationFailedErrorUrl = "http://ltiapps.net/test/tc-return.php?lti_errormsg=Tool+registration+process+failed&lti_msg=failure&lti_errorlog=We+are+unable+to+complete+the+registration+as+the+following+are+required+capabilities%2fservices+needed+for+the+integration%3a+%0d%0aMissing+Capabilities+-+basic-lti-launch-request%2c+Context.id%2c+ResourceLink.id%2c+User.id%2c+Membership.role%0d%0a";
             
             ToolRegistrationParser toolRegistrationParser = new ToolRegistrationParser();
             
             HashSet<String> expectedMissingCapabilities = new HashSet();
       expectedMissingCapabilities.add("basic-lti-launch-request");
       expectedMissingCapabilities.add("ResourceLink.id");
       expectedMissingCapabilities.add("User.id");
       expectedMissingCapabilities.add("Membership.role");
             
             boolean result = toolRegistrationParser.ValidateMissingCapabilities(302, registrationFailedErrorUrl, expectedMissingCapabilities);
             //boolean result = toolRegistrationParser.ValidateCapabilities(response, capabilities);
       }
       
       public boolean ValidateMissingCapabilities(int statusCode, String repsonseUrl, HashSet<String> expectedMissingCapabilities) 
                    throws UnsupportedEncodingException {
             boolean result = true;
             if (statusCode != 302)
                    return false;
             
             List<NameValuePair> params = URLEncodedUtils.parse(repsonseUrl, Charset.defaultCharset());
             for (NameValuePair param : params) {
                    if (param.getName().equals("lti_msg")) {
                           if (!param.getValue().equals("failure"))
                                  return false;
                    }
                    
                    if (param.getName().equals("lti_errormsg")) {
                           if (!param.getValue().equals("Tool registration process failed"))
                                  return false;
                    }
                    
                    if (param.getName().equals("lti_errorlog")) {
                           String decodedValue = URLDecoder.decode(param.getValue(), "UTF-8");
                           String capabilities = decodedValue.substring(decodedValue.indexOf("Missing Capabilities") + 23); // Capabilities list
                           
                           List<String> capabilitiesList = new ArrayList();
                           for (String c : capabilities.split(",")) {
                                  capabilitiesList.add(c.trim());
                           }
                                                     
                           for (String capability : expectedMissingCapabilities) {
                                 if (!capabilitiesList.contains(capability))
                                        return false;
                           }
                    }
             }
             
             return result;
       }
       
       
       public boolean ValidateCapabilities(String repsonseHtml, HashSet<String> capabilities) {
             boolean result = false;
             
              // 1. Parse response to html document
             Document doc = Jsoup.parse(repsonseHtml);
             // 2. Build a list of capabilities
             Elements pElements = doc.getElementsByTag("p"); // search for <p>Capabilities</p>
             for (Element p : pElements) {
                    if (p.nodeName().equals("Capabilities")) {
                           Elements sibs = p.siblingElements();
                    }
             }
             // 3. Compare original capabilities list to target list (found in response)
             
              return result;
       }

       
}
*/