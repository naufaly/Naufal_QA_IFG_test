# Test Automation Project

Project ini berisi dua skenario pengujian otomatisasi:
1. API Testing (REST API)
2. Kafka Consumer Testing

---

## API Testing

### Deskripsi

Pengujian ini fokus pada operasi CRUD terhadap REST API dengan pendekatan producer-consumer pattern.
Tujuannya untuk memastikan proses pembuatan dan pengambilan data berjalan sesuai ekspektasi.

### Cara Menjalankan
1. Buka Katalon Studio
2. Navigasi ke Test Cases > API Tests > TC_API_Producer_Consumer
3. Klik Run

### Yang Diuji
- POST: Membuat user baru
- GET: Mengambil data user yang telah dibuat
- Validasi: Memastikan data yang diterima sesuai dengan data yang dikirim

API yang digunakan: gorest.co.in/public/v2
(Public API — tidak memerlukan setup tambahan)

---

## Kafka Consumer Testing

### Deskripsi

Pengujian ini bertujuan untuk memverifikasi kemampuan consumer dalam menerima pesan dari Kafka topic secara real-time.

### Setup Kafka

**Install:**

```bash
brew install kafka
```

**Menjalankan Kafka:**

```bash
# Terminal 1 - Zookeeper
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties

# Terminal 2 - Kafka Server
kafka-server-start /usr/local/etc/kafka/server.properties
```

**Membuat Topic:**

```bash
kafka-topics --create --topic test-topic --bootstrap-server localhost:9092
```

**Mengirim Test Message:**

```bash
kafka-console-producer --bootstrap-server localhost:9092 --topic test-topic
> test message 1
> test message 2
```

### Cara Menjalankan
1. Pastikan Kafka sudah berjalan
2. Jalankan gradle build untuk membangun dependencies
3. Buka Katalon Studio
4. Jalankan Test Cases > Kafka Tests > TC_Kafka_Consumer

### Konfigurasi

Ubah konfigurasi di Profiles/default.glbl sesuai kebutuhan:
- Server: localhost:9092
- Topic: test-topic
- Group ID: katalon-test-group
- Timeout: 10000 ms

---

## Troubleshooting

**Kafka tidak berjalan:**
- Cek status: ps aux | grep kafka
- Cek daftar topic: kafka-topics --list --bootstrap-server localhost:9092

**API test gagal:**
- Pastikan koneksi internet stabil
- Coba akses https://gorest.co.in dari browser

**Dependencies error:**
- Jalankan ulang gradle build
- Restart Katalon Studio

---

## Struktur Proyek

```bash
Project/
├── Test Cases/
│   ├── API Tests/TC_API_Producer_Consumer
│   └── Kafka Tests/TC_Kafka_Consumer
├── Keywords/kafka/KafkaConsumerKeywords.groovy
├── Object Repository/API/
└── Profiles/default.glbl
```

---
