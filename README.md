# 🌞 Growatt Solar Dashboard

Plně funkční webová aplikace pro vizualizaci solárních dat. Backend je postavený na Spring Boot, frontend na Reactu s TypeScriptem a knihovnou Recharts pro vykreslení grafů.

---

## 📁 Struktura projektu

growatt-dashboard/
├── backend/ # Spring Boot aplikace (Java 21, MSSQL)
│ ├── controller/
│ ├── entity/
│ ├── repository/
│ ├── service/
│ ├── statistics/ # Strategy Pattern
│ └── audit/ # Audit logování
│
└── frontend/ # React (TypeScript, Vite, TailwindCSS)
├── components/
└── Dashboard.tsx

yaml
Zkopírovat

---

## ⚙️ Backend (Spring Boot)

### 📌 Technologie

- Java 21
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- SQL Server (MSSQL)
- Hibernate
- Maven
- Lombok

---

### 📂 Klíčové balíčky

| Balíček        | Popis                                         |
|----------------|-----------------------------------------------|
| `entity`       | Datová entita `SolarData` mapovaná na MSSQL  |
| `repository`   | Přístup k datům pomocí Spring Data JPA        |
| `service`      | Aplikační logika nad solárními daty           |
| `statistics`   | Strategie pro výpočty metrik (viz níže)       |
| `controller`   | REST API pro frontend                         |
| `audit`        | Logování příchozích požadavků (interceptor)   |

---

### 📊 Použitý návrhový vzor: Strategy Pattern

Použit pro výpočet statistik z tabulky `SolarData`:

#### ✅ Rozhraní
```java
public interface SolarStatStrategy {
    String getName();
    double compute(List<SolarData> data);
}
✅ Implementace
Každá statistika má vlastní třídu, např.:

AverageProductionStat

AverageConsumptionStat

MaxProductionStat

MinConsumptionStat

✅ Kontextová třída
java
Zkopírovat
public class SolarStatisticsContext {
    private final List<SolarStatStrategy> strategies = List.of(...);

    public Map<String, Double> calculateAll(List<SolarData> data) {
        Map<String, Double> result = new LinkedHashMap<>();
        for (SolarStatStrategy stat : strategies) {
            result.put(stat.getName(), stat.compute(data));
        }
        return result;
    }
}
🧠 Výhody:
Rozšiřitelnost bez úpravy existující logiky

Snadné testování a ladění každé metriky

Odpovídá principům OOP (SOLID)

🔐 Audit Logging
Pomocí HandlerInterceptor (AuditInterceptor) logujeme každý příchozí request (čas, IP, URL), což přidává auditní vrstvu do aplikace.

📡 API endpoints
Endpoint	Popis
GET /api/solar/all	Vrací všechna solární data
GET /api/solar/statistics	Vrací průměry, maxima, minima

🔧 Nastavení databáze
V application.properties:

properties
Zkopírovat
server.port=30400

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=GrowattData
spring.datasource.username=Growatt
spring.datasource.password=Heslo123!

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
🖥️ Frontend (React)
📌 Technologie
React + TypeScript

Vite

TailwindCSS

Axios

Recharts

📂 Klíčové komponenty
Dashboard.tsx – hlavní dashboard s 4 grafy:

Výroba (Production)

Spotřeba (Consumption)

Stav baterie (Battery Level)

Odběr ze sítě (Grid Import)

🎨 Design
Dark mode pomocí TailwindCSS

Responsivní layout

Barvy grafů:

Žlutá: Výroba

Modrá: Spotřeba

Oranžová: Baterie

Fialová: Odběr ze sítě

🔄 API komunikace
Ukázka načtení dat v Dashboard.tsx:

ts
Zkopírovat
useEffect(() => {
  axios.get<SolarData[]>("http://46.13.167.200:30400/api/solar/all")
       .then((res) => setData(res.data));
}, []);
🚀 Jak to spustit
1. Backend
bash
Zkopírovat
cd backend
./mvnw spring-boot:run
2. Frontend
bash
Zkopírovat
cd frontend
npm install
npm run dev
Frontend očekává backend na http://46.13.167.200:30400. Pokud běží jinde, uprav URL v Dashboard.tsx.

✨ Možná rozšíření
Autentizace (např. JWT)

Export dat (CSV, Excel)

Ukládání statistik do DB

Notifikace při extrémních hodnotách

Predikce produkce pomocí AI

Historické porovnání dní/týdnů

🧠 Shrnutí návrhového vzoru
Použitím Strategy Patternu jsme získali čistý, rozšiřitelný a udržitelný způsob výpočtu různých statistik. Každá metrika je oddělená, snadno testovatelná a přehledná. Kontextová třída (SolarStatisticsContext) agreguje výsledky a nabízí je kontroleru – frontend pak tyto výsledky zobrazuje jako doplněk ke grafům.
