package kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import com.kms.katalon.core.annotation.Keyword
import java.time.Duration
import java.util.Properties
import java.util.Arrays

/**
 * Kafka Consumer helper class
 * For testing message consumption from Kafka topics
 */
public class KafkaConsumerKeywords {

	private KafkaConsumer<String, String> consumer
	private List<ConsumerRecord<String, String>> messages = []

	@Keyword
	def createKafkaConsumer(String servers, String groupId, String reset = "earliest") {
		println "Setting up consumer..."
		println "Servers: ${servers}"
		println "Group: ${groupId}"

		Properties config = new Properties()
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers)
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, reset)
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
		config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000")

		try {
			consumer = new KafkaConsumer<>(config)
			println "Consumer ready"
			return consumer
		} catch (Exception ex) {
			println "Failed to create consumer: ${ex.message}"
			throw ex
		}
	}

	@Keyword
	def subscribeToTopic(String topic) {
		if (!consumer) {
			throw new IllegalStateException("Consumer not initialized")
		}

		println "Subscribing to: ${topic}"
		try {
			consumer.subscribe(Arrays.asList(topic))
			println "Subscribed"
		} catch (Exception ex) {
			println "Subscribe failed: ${ex.message}"
			throw ex
		}
	}

	@Keyword
	def consumeMessages(long timeout = 10000, int max = 10) {
		if (!consumer) {
			throw new IllegalStateException("Consumer not initialized")
		}

		println "Polling messages (timeout: ${timeout}ms, max: ${max})..."

		messages.clear()
		long start = System.currentTimeMillis()
		long deadline = start + timeout

		try {
			while (System.currentTimeMillis() < deadline && messages.size() < max) {
				ConsumerRecords<String, String> recs = consumer.poll(Duration.ofMillis(1000))

				for (ConsumerRecord<String, String> rec : recs) {
					messages.add(rec)
					println "Got message:"
					println "  Topic: ${rec.topic()}"
					println "  Partition: ${rec.partition()}"
					println "  Offset: ${rec.offset()}"
					println "  Key: ${rec.key()}"
					println "  Value: ${rec.value()}"
					println "  Time: ${rec.timestamp()}"

					if (messages.size() >= max) break
				}
			}

			println "Total received: ${messages.size()}"
			return messages

		} catch (Exception ex) {
			println "Error consuming: ${ex.message}"
			throw ex
		}
	}

	@Keyword
	def getConsumedMessages() {
		return messages
	}

	@Keyword
	def verifyMessageCount(int expected) {
		int actual = messages.size()
		println "Checking count - Expected: ${expected}, Actual: ${actual}"

		if (actual == expected) {
			println "Count OK"
			return true
		} else {
			println "Count mismatch"
			return false
		}
	}

	@Keyword
	def verifyMessageContains(String text) {
		println "Looking for: '${text}'"

		for (ConsumerRecord<String, String> rec : messages) {
			if (rec.value().contains(text)) {
				println "Found in: ${rec.value()}"
				return true
			}
		}

		println "Not found"
		return false
	}

	@Keyword
	def getMessageByIndex(int idx) {
		if (idx < 0 || idx >= messages.size()) {
			throw new IndexOutOfBoundsException("Index ${idx} invalid (total: ${messages.size()})")
		}
		return messages.get(idx)
	}

	@Keyword
	def closeConsumer() {
		if (consumer) {
			println "Closing consumer..."
			try {
				consumer.close()
				println "Closed"
			} catch (Exception ex) {
				println "Close error: ${ex.message}"
				throw ex
			}
		}
	}

	@Keyword
	def resetConsumedMessages() {
		messages.clear()
		println "Messages cleared"
	}
}
