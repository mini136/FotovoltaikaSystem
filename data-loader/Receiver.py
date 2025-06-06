from growatt import GrowattApi
import pyodbc
from datetime import datetime
import time

def get_growatt_data():
    print("[INFO] Přihlašuji se k Growatt API...")
    try:
        api = GrowattApi()
        login_response = api.login('Uziv. jmeno', 'Heslo')
        print("[INFO] Přihlášení úspěšné.")

        plants = login_response['plantData']
        if not plants:
            raise Exception("Žádné elektrárny nenalezeny.")

        plant = plants[0]
        device_sn = plant['deviceSn']

        print(f"[INFO] Načítám data z měniče SN: {device_sn}")
        inverter_data = api.get_inverter_data(device_sn)

        print(f"[INFO] Data z měniče: {inverter_data}")

        return {
            'timestamp': datetime.now(),
            'production': float(inverter_data.get('ppv', 0)),
            'consumption': float(inverter_data.get('loadPower', 0)),
            'battery': float(inverter_data.get('soc', 0)),
            'grid': float(inverter_data.get('gridPower', 0))
        }

    except Exception as e:
        print(f"[ERROR] Chyba při získávání dat z Growatt API: {e}")
        return None

def save_to_sql(data):
    print("[INFO] Připojuji se k MSSQL...")
    try:
        conn = pyodbc.connect(
            'DRIVER={ODBC Driver 17 for SQL Server};'
            'SERVER=46.13.167.200,20500;'
            'DATABASE=GrowattData;'
            'UID=User;PWD=Password;'
            'TrustServerCertificate=yes;'
        )
        cursor = conn.cursor()
        print("[INFO] Připojení úspěšné. Vkládám data...")

        cursor.execute("""
            INSERT INTO SolarData (Timestamp, Production, Consumption, Battery, Grid)
            VALUES (?, ?, ?, ?, ?)
        """, (
            data['timestamp'], data['production'], 
            data['consumption'], data['battery'], 
            data['grid']
        ))
        conn.commit()
        conn.close()
        print("[INFO] Data uložena do databáze.")
    except Exception as e:
        print(f"[ERROR] Chyba při ukládání do databáze: {e}")

if __name__ == "__main__":
    while True:
        print("[INFO] Nový cyklus začíná...")
        data = get_growatt_data()
        if data:
            save_to_sql(data)
        else:
            print("[WARNING] Data nebyla získána, ukládání přeskočeno.")
        print("[INFO] Čekám 5 minut...\n")
        time.sleep(300)
