<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>POST request to create a new user</description>
   <name>POST_CreateUser</name>
   <tag></tag>
   <elementGuidId>a1b2c3d4-e5f6-7890-abcd-ef1234567890</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <authorizationRequest>
      <authorizationInfo>
         <entry>
            <key>bearerToken</key>
            <value>59fbaedc0891cd6a509abb966330d7f8d0dda6462dbccf6e808d90d7f27304c8</value>
         </entry>
      </authorizationInfo>
      <authorizationType>Bearer</authorizationType>
   </authorizationRequest>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;name\&quot;: \&quot;${name}\&quot;,\n  \&quot;email\&quot;: \&quot;${email}\&quot;,\n  \&quot;gender\&quot;: \&quot;${gender}\&quot;,\n  \&quot;status\&quot;: \&quot;${status}\&quot;\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>Bearer 59fbaedc0891cd6a509abb966330d7f8d0dda6462dbccf6e808d90d7f27304c8</value>
      <webElementGuid>a1b2c3d4-e5f6-7890-abcd-ef1234567891</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>b2c3d4e5-f6a7-8901-bcde-f12345678901</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>c3d4e5f6-a7b8-9012-cdef-123456789012</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>8.6.0</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>https://gorest.co.in/public/v2/users</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>'John Doe'</defaultValue>
      <description>User name</description>
      <id>d4e5f6a7-b8c9-0123-def1-234567890123</id>
      <masked>false</masked>
      <name>name</name>
   </variables>
   <variables>
      <defaultValue>'john.doe@example.com'</defaultValue>
      <description>User email</description>
      <id>e5f6a7b8-c9d0-1234-ef12-345678901234</id>
      <masked>false</masked>
      <name>email</name>
   </variables>
   <variables>
      <defaultValue>'male'</defaultValue>
      <description>User gender</description>
      <id>f6a7b8c9-d0e1-2345-f123-456789012345</id>
      <masked>false</masked>
      <name>gender</name>
   </variables>
   <variables>
      <defaultValue>'active'</defaultValue>
      <description>User status</description>
      <id>a7b8c9d0-e1f2-3456-1234-567890123456</id>
      <masked>false</masked>
      <name>status</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()
ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()
</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
