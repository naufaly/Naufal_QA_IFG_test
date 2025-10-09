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
import kafka.KafkaConsumerKeywords as KafkaConsumerKeywords

// Kafka Consumer Test
// Created: Oct 2024
// Purpose: Test consuming messages from Kafka topic

println '=== Kafka Consumer Test ==='
println ''

KafkaConsumerKeywords kafka = new KafkaConsumerKeywords()

try {
	// Setup
	println 'Initializing Kafka consumer...'

	String kafkaServer = GlobalVariable.KAFKA_BOOTSTRAP_SERVERS
	String topicName = GlobalVariable.KAFKA_TOPIC
	String group = GlobalVariable.KAFKA_GROUP_ID
	long timeoutMs = GlobalVariable.KAFKA_CONSUMER_TIMEOUT

	println "Kafka Server: ${kafkaServer}"
	println "Topic: ${topicName}"
	println "Group: ${group}"
	println ''

	kafka.createKafkaConsumer(kafkaServer, group, "earliest")
	kafka.subscribeToTopic(topicName)

	println 'Waiting for messages...'
	println ''

	// Consume
	def msgs = kafka.consumeMessages(timeoutMs, 10)

	if (msgs.isEmpty()) {
		println 'No messages received'
		println 'Make sure Kafka is running and topic has data'
		println ''
		println 'Quick test: kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test-topic'
	} else {
		println "Received ${msgs.size()} message(s)"
		println ''

		// Display messages
		msgs.eachWithIndex { rec, idx ->
			println "Message ${idx + 1}:"
			println "  Topic: ${rec.topic()}"
			println "  Partition: ${rec.partition()}"
			println "  Offset: ${rec.offset()}"
			println "  Key: ${rec.key()}"
			println "  Value: ${rec.value()}"
			println "  Timestamp: ${new Date(rec.timestamp())}"
			println ''
		}

		// Basic validation
		if (msgs.size() > 0) {
			def first = kafka.getMessageByIndex(0)
			println "First message: ${first.value()}"
		}
	}

	// Cleanup
	kafka.closeConsumer()
	println 'Consumer closed'
	println ''

	// Summary
	println '=== Test Summary ==='
	println "Topic: ${topicName}"
	println "Messages: ${msgs.size()}"
	println 'Status: OK'
	println ''

} catch (Exception ex) {
	println ''
	println '!!! ERROR !!!'
	println "Message: ${ex.message}"
	ex.printStackTrace()

	// Try to cleanup
	try {
		kafka.closeConsumer()
	} catch (Exception ignore) {}

	throw ex
}
