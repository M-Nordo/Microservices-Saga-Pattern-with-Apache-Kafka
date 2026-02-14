# 🚀 Microservices Saga Pattern with Apache Kafka
This project is a professional implementation of the Saga Pattern (Choreography-based) used to ensure data consistency in microservices architectures. An asynchronous workflow is managed through Order and Stock services.

Bu proje, mikroservis mimarilerinde veri tutarlılığını (data consistency) sağlamak için kullanılan Saga Pattern (Choreography-based) yönteminin profesyonel bir uygulamasıdır. Sipariş ve Stok servisleri üzerinden asenkron bir iş akışı yönetilmektedir.

## 🇺🇸 Project Summary
The system operates through message traffic between two main services:

**1. Order Service (Port: 8081):** Creates an order in PENDING state and notifies Kafka.

**2. Stock Service (Port: 8082):** Performs stock check. If sufficient, it decreases stock (COMPLETED), otherwise rejects the transaction (CANCELLED).

**3. Saga Cycle:** Order Service updates the final order status based on the feedback from Stock Service.

## 🇹🇷 Proje Özeti
Sistem, iki ana servis arasındaki mesajlaşma trafiği ile çalışır:

**1. Order Service (Port: 8081):** Siparişi PENDING olarak oluşturur ve Kafka'ya bildirir.

**2. Stock Service (Port: 8082):** Stok kontrolü yapar. Stok yeterliyse düşer (COMPLETED), yetersizse işlemi reddeder (CANCELLED).

**3. Saga Cycle:** Order Service, stoktan gelen cevaba göre siparişi nihai durumuna günceller.

## 🛠️ Tech Stack
**• Java 17 & Spring Boot 3.3.x**

**• Apache Kafka:** Event-driven communication.

**• H2 Database:** Isolated storage for each service.

**• Docker & Docker Compose:** Infrastructure management.

## 🛠️ Teknolojiler
**• Java 17 & Spring Boot 3.3.x**

**• Apache Kafka:** Event-driven iletişim.

**• H2 Database:** Her servis için izole veri depolama.

**• Docker & Docker Compose:** Altyapı yönetimi.

## 📊 Test Scenarios & Screenshots (Test Senaryoları & Görseller)
**1️⃣ Successful Order Flow (Başarılı Sipariş Akışı)**

When stock is sufficient, the system transitions to COMPLETED state.

Stokta yeterli ürün olduğunda sistemin COMPLETED durumuna geçişi.

<img width="1225" height="339" alt="Screenshot_8" src="https://github.com/user-attachments/assets/20bd9e75-d616-45e5-b3ac-afcd97af542c" />

<img width="1215" height="326" alt="Screenshot_9" src="https://github.com/user-attachments/assets/8db845c1-07ef-486a-a603-2cf2e5975e49" />

<img width="1223" height="279" alt="Screenshot_10" src="https://github.com/user-attachments/assets/f85c6db1-4bb0-4035-82cf-5459b49966ff" />


**2️⃣ Insufficient Stock / Rollback Flow (Yetersiz Stok / İptal Akışı)**

Saga automatically cancels the order when stock is insufficient.

Stok yetmediğinde Saga'nın otomatik olarak siparişi iptal etmesi.

**3️⃣ Service Logs (Terminal Logları)**

Terminal output showing how services communicate with each other via Kafka.

Servislerin Kafka üzerinden birbiriyle nasıl haberleştiğinin terminal çıktısı.

<img width="369" height="58" alt="Screenshot_11" src="https://github.com/user-attachments/assets/039e8826-d788-4e92-b587-eed309f22fa5" />


## 🚀 Installation (Kurulum)

**1. Start Infrastructure:**

Run Kafka and Zookeeper using Docker Compose:
```
docker-compose up -d
```

<img width="1206" height="160" alt="Screenshot_12" src="https://github.com/user-attachments/assets/ab9cb1bf-47a2-4dde-acc5-7715f08a9f49" />


**2. Build and Run Services:**

Navigate to both orderservice and stockservice directories and run:
```
mvn clean package -DskipTests
java -jar target/*.jar
```

**3. API Endpoints**

**• Order Service:**
```
POST http://localhost:8081/orders
```

**• Stock Service:**
```
POST http://localhost:8082/stocks
GET http://localhost:8082/stocks
```

## 💡 Architectural Insights (Mimari Notlar)

**• Loose Coupling:** Services are decoupled; they do not know each other's REST endpoints. They communicate purely through Kafka topics.

**• Gevşek Bağlılık:** Servisler birbirinin uç noktalarını (REST endpoints) bilmez; sadece Kafka topic'leri üzerinden asenkron haberleşirler.

**• Data Consistency (Saga):** Even though each service has its own isolated database (H2), eventual consistency is guaranteed via event-driven compensation logic.

**• Veri Tutarlılığı:** Her servisin kendi veritabanı (H2) olmasına rağmen, Saga Pattern sayesinde dağıtık veri tutarlılığı sağlanır.

**• Resilience:** If the Stock Service is down, messages are persisted in Kafka. The system automatically resumes processing once the service is back online.

**• Dayanıklılık:** Bir servis kapalı olsa bile mesajlar Kafka'da saklanır ve servis açıldığında işlenmeye devam eder.

**• Type Mapping Solution:** Cross-service communication is handled by disabling type headers and using default type mapping to resolve ClassNotFoundException issues between different packages.

**• Tip Eşleme:** Farklı paket isimlerinden kaynaklanan ClassNotFoundException sorunu, default.type konfigürasyonu ile aşılmıştır.

## 📝 Technical Note (Teknik Not)

In this project, **Choreography-based Saga** is implemented. This means services make their own decisions based on the "events" emitted by others, without a central Orchestrator.

Bu projede **Choreography-based Saga** kullanılmıştır. Yani merkezi bir yönetici (Orchestrator) olmadan, servisler birbirlerinin fırlattığı "event"lere (olaylara) göre kendi kararlarını verirler.

