import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import groovy.json.JsonSlurper
import java.text.SimpleDateFormat

// Test RESTful API - Producer & Consumer pattern
// Created: 2024-10
// Author: QA Team

println '=== API Test: Producer-Consumer Pattern ==='
println ''

// Generate dynamic test data
def dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss")
def timestamp = dateFormat.format(new Date())
def random = new Random().nextInt(9999)

String email = "testuser_${timestamp}_${random}@example.com"
String name = "Test User ${timestamp}"
String gender = ['male', 'female'][new Random().nextInt(2)]
String status = 'active'

println "Generated test data:"
println "  Name: ${name}"
println "  Email: ${email}"
println "  Gender: ${gender}"
println "  Status: ${status}"
println ''

// Step 1: Send POST request (Producer)
println 'Step 1: Creating new user via POST...'

def postResp = WS.sendRequest(findTestObject('API/POST_CreateUser', [
	('name') : name,
	('email') : email,
	('gender') : gender,
	('status') : status
]))

WS.verifyResponseStatusCode(postResp, 201)
println "Status: ${postResp.getStatusCode()}"

def parser = new JsonSlurper()
def postData = parser.parseText(postResp.getResponseText())

assert postData.name == name
assert postData.email == email
assert postData.gender == gender
assert postData.status == status

def userId = postData.id
println "User created, ID: ${userId}"
println ''

// Step 2: Verify via GET request (Consumer)
println 'Step 2: Fetching user data via GET...'

Thread.sleep(1000) // wait for data sync

def getResp = WS.sendRequest(findTestObject('API/GET_UserById', [
	('userId') : userId
]))

WS.verifyResponseStatusCode(getResp, 200)
println "Status: ${getResp.getStatusCode()}"

def getData = parser.parseText(getResp.getResponseText())

// verify all fields match
assert getData.id == userId
assert getData.name == name
assert getData.email == email
assert getData.gender == gender
assert getData.status == status

println "Data verified successfully"
println "Response: ${getData}"
println ''

// Summary
println '=== Test Completed ==='
println "POST (Producer): Created user ${userId}"
println "GET (Consumer): Verified user data"
println ''
