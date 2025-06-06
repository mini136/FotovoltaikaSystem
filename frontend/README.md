# 🌞 Growatt Solar Dashboard

Plně funkční webová aplikace pro vizualizaci solárních dat pomocí Spring Boot (backend) a React + Recharts (frontend).

---

## 📁 Struktura projektu

growatt-dashboard/
│
├── backend/ # Spring Boot aplikace (Java 21, Maven, JPA)
└── frontend/ # React aplikace (TypeScript, Vite, TailwindCSS, Recharts)

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

### 📂 Klíčové balíčky

| Balíček        | Popis                                         |
|----------------|-----------------------------------------------|
| `entity`       | Datová entita `SolarData` mapovaná na MSSQL  |
| `repository`   | Interface pro přístup k datům                |
| `service`      | Logika pro práci s daty                      |
| `statistics`   | Strategy pattern pro výpočty statistik       |
| `controller`   | REST API pro frontend                        |
| `audit`        | Automatické logování requestů                |

---

### 📊 Strategy Pattern

Použit pro výpočet různých statistik nad daty. Každá statistika implementuje:

```java
public interface SolarStatStrategy {
    String getName();
    double compute(List<SolarData> data);
}
Implementace:
AverageProductionStat

AverageConsumptionStat

MaxProductionStat

MinConsumptionStat

Třída SolarStatisticsContext spravuje všechny strategie a umožňuje spočítat vše najednou.

🔐 Audit Logging
Součástí projektu je interceptor AuditInterceptor, který loguje každý request na konzoli. Ukazuje základní auditní vrstvu systému.

📡 API endpoints
Endpoint	Popis
GET /api/solar/all	Vrací veškerá data z tabulky
GET /api/solar/statistics	Vrací agregované statistiky jako JSON

🔧 Nastavení databáze
V souboru application.properties:

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
Dashboard.tsx: Hlavní dashboard se čtyřmi grafy:

Production

Consumption

Battery Level

Grid Import

🎨 Design
Dark Mode UI postavený s Tailwindem

Reaktivní grafy pomocí Recharts

Barvy:

Žlutá: Production

Modrá: Consumption

Oranžová: Battery

Fialová: Grid import

🔄 API komunikace (ukázka)
ts
Zkopírovat
useEffect(() => {
  axios.get<SolarData[]>("http://46.13.167.200:30400/api/solar/all").then((res) => {
    setData(res.data);
  });
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
ℹ️ Frontend očekává backend na http://46.13.167.200:30400. Můžeš si to upravit v Dashboard.tsx.

✨ Možné rozšíření
JWT autentizace a správa uživatelů

Ukládání uživatelských preferencí (např. oblíbené metriky)

Logování do databáze (místo na konzoli)

Export dat (CSV, Excel)

Prediktivní analýzy nebo grafy s predikcí

🧠 Použité návrhové vzory
Strategy Pattern
Oddělení výpočtových algoritmů pomocí interface SolarStatStrategy, který implementuje každá konkrétní statistika. Snadno rozšiřitelné o nové výpočty bez úpravy hlavní logiky.