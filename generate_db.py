#!/usr/bin/env python3
"""
Generiert die pre-populated windrad.db aus den Quelldaten.

Verwendung:
    python3 generate_db.py

Voraussetzungen:
    pip install numbers-parser   (für das Parsen der Apple Numbers-Datei)

Eingabe-Dateien (im gleichen Verzeichnis):
    - alleWindräder.csv    (Apple Numbers-Datei, trotz .csv Endung)
    - sights.csv           (Standard-CSV mit ID, Name, Höhe)

Ausgabe:
    - shared/src/androidMain/assets/databases/windrad.db
"""

import csv
import os
import sqlite3
import sys
from pathlib import Path

try:
    from numbers_parser import Document
except ImportError:
    print("FEHLER: 'numbers-parser' ist nicht installiert.")
    print("Bitte installiere es mit: pip install numbers-parser")
    sys.exit(1)


# --- Konfiguration ---

SCRIPT_DIR = Path(__file__).parent
WINDMILL_SOURCE = SCRIPT_DIR / "alleWindräder.numbers"
SIGHTS_SOURCE = SCRIPT_DIR / "sights.csv"
DB_OUTPUT = SCRIPT_DIR / "shared" / "src" / "androidMain" / "assets" / "databases" / "windrad.db"

# Mapping: Deutsche Spaltennamen (aus Numbers) → Englische DB-Spalten
# Die Reihenfolge entspricht der Reihenfolge in der Numbers-Tabelle (33 Spalten).
# "Netzbetreiberprüfung" (Index 29) wird nicht in die DB übernommen.
COLUMN_MAPPING = {
    "MaStR-Nr. der Einheit": "unit_MaStR_ID",
    "Anzeige-Name der Einheit": "unit_display_name",
    "Betriebs-Status": "operating_status",
    "Bruttoleistung der Einheit": "unit_gross_capacity",
    "Nettonennleistung der Einheit": "unit_net_nominal_capacity",
    "Inbetriebnahmedatum der Einheit": "unit_commissioning_date",
    "Registrierungsdatum der Einheit": "unit_registration_date",
    "Bundesland": "federal_state",
    "Postleitzahl": "postal_code",
    "Ort": "city",
    "Gemarkung": "cadastral_district",
    "Gemeindeschlüssel": "municipality_code",
    "Gemeinde": "municipality",
    "Landkreis": "district",
    "Koordinate: Breitengrad (WGS84)": "latitude_wgs84",
    "Koordinate: Längengrad (WGS84)": "longitude_wgs84",
    "Technologie der Stromerzeugung": "power_generation_technology",
    "Volleinspeisung oder Teileinspeisung": "feed_in_type",
    "Name des Windparks": "wind_plant_name",
    "Wind an Land oder auf See": "onshore_offshore_type",
    "Nabenhöhe der Windenergieanlage": "turbine_hub_height",
    "Rotordurchmesser der Windenergieanlage": "turbine_rotor_diameter",
    "Hersteller der Windenergieanlage": "turbine_manufacturer",
    "Typenbezeichnung": "type_designation",
    "Datum der endgültigen Stilllegung": "final_decommissioning_date",
    "Datum der geplanten Inbetriebnahme": "planned_commissioning_date",
    "Name des Anlagenbetreibers (nur Org.)": "plant_operator_name_org",
    # Hinweis: Der Tab-Prefix im Header (\tMaStR-Nr. des Anlagenbetreibers)
    "MaStR-Nr. des Anlagenbetreibers": "operator_mastr_no",
    "Name des Anschluss-Netzbetreibers": "connecting_grid_operator_name",
    # "Netzbetreiberprüfung" wird NICHT übernommen
    "Spannungsebene": "voltage_level",
    "Inbetriebnahmedatum der EEG-Anlage": "eeg_plant_commissioning_date",
    "Installierte Leistung der EEG-Anlage": "eeg_plant_installed_capacity",
}

# Spalten, die als REAL in der DB gespeichert werden
REAL_COLUMNS = {
    "unit_gross_capacity",
    "unit_net_nominal_capacity",
    "latitude_wgs84",
    "longitude_wgs84",
    "turbine_hub_height",
    "turbine_rotor_diameter",
    "eeg_plant_installed_capacity",
}

# DB-Spalten in der gewünschten Reihenfolge
DB_COLUMNS = [
    "unit_MaStR_ID", "unit_display_name", "operating_status",
    "unit_gross_capacity", "unit_net_nominal_capacity",
    "unit_commissioning_date", "unit_registration_date",
    "federal_state", "postal_code", "city",
    "cadastral_district", "municipality_code", "municipality", "district",
    "latitude_wgs84", "longitude_wgs84",
    "power_generation_technology", "feed_in_type",
    "wind_plant_name", "onshore_offshore_type",
    "turbine_hub_height", "turbine_rotor_diameter",
    "turbine_manufacturer", "type_designation",
    "final_decommissioning_date", "planned_commissioning_date",
    "plant_operator_name_org", "operator_mastr_no",
    "connecting_grid_operator_name", "voltage_level",
    "eeg_plant_commissioning_date", "eeg_plant_installed_capacity",
]


def create_tables(cursor: sqlite3.Cursor) -> None:
    """Erstellt die drei Datenbank-Tabellen."""
    cursor.execute("""
        CREATE TABLE windmill_units (
            unit_MaStR_ID TEXT PRIMARY KEY,
            unit_display_name TEXT,
            operating_status TEXT,
            unit_gross_capacity REAL,
            unit_net_nominal_capacity REAL,
            unit_commissioning_date TEXT,
            unit_registration_date TEXT,
            federal_state TEXT,
            postal_code TEXT,
            city TEXT,
            cadastral_district TEXT,
            municipality_code TEXT,
            municipality TEXT,
            district TEXT,
            latitude_wgs84 REAL,
            longitude_wgs84 REAL,
            power_generation_technology TEXT,
            feed_in_type TEXT,
            wind_plant_name TEXT,
            onshore_offshore_type TEXT,
            turbine_hub_height REAL,
            turbine_rotor_diameter REAL,
            turbine_manufacturer TEXT,
            type_designation TEXT,
            final_decommissioning_date TEXT,
            planned_commissioning_date TEXT,
            plant_operator_name_org TEXT,
            operator_mastr_no TEXT,
            connecting_grid_operator_name TEXT,
            voltage_level TEXT,
            eeg_plant_commissioning_date TEXT,
            eeg_plant_installed_capacity REAL
        )
    """)

    cursor.execute("""
        CREATE TABLE collected_windmill_units (
            unit_MaStR_ID TEXT PRIMARY KEY,
            collected INTEGER NOT NULL DEFAULT 0,
            FOREIGN KEY(unit_MaStR_ID) REFERENCES windmill_units(unit_MaStR_ID)
        )
    """)

    cursor.execute("""
        CREATE TABLE comparable_sights (
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            height REAL NOT NULL
        )
    """)


def clean_value(value, db_col: str):
    """Bereinigt einen Wert für die Datenbank."""
    if value is None:
        return None

    # Konvertiere numbers_parser Typen zu nativen Python-Typen
    val = value

    # Postleitzahl: als String mit führenden Nullen
    if db_col == "postal_code":
        if isinstance(val, (int, float)):
            return str(int(val)).zfill(5)
        return str(val).zfill(5) if val else None

    # Gemeindeschlüssel: als String
    if db_col == "municipality_code":
        if isinstance(val, (int, float)):
            return str(int(val))
        return str(val) if val else None

    # REAL-Spalten: als float
    if db_col in REAL_COLUMNS:
        if isinstance(val, (int, float)):
            return float(val)
        try:
            return float(val) if val else None
        except (ValueError, TypeError):
            return None

    # String-Spalten
    if isinstance(val, str):
        val = val.strip()
        return val if val else None

    return str(val) if val is not None else None


def import_windmills(cursor: sqlite3.Cursor) -> int:
    """
    Liest Windrad-Daten aus der Apple Numbers-Datei und fügt sie in die DB ein.
    Gibt die Anzahl importierter Zeilen zurück.
    """
    print(f"Lese Numbers-Datei: {WINDMILL_SOURCE}")
    doc = Document(str(WINDMILL_SOURCE))

    sheet = doc.sheets[0]
    table = sheet.tables[0]

    print(f"  Tabelle: '{table.name}', {table.num_rows} Zeilen, {table.num_cols} Spalten")

    # Header lesen und Mapping erstellen
    headers = [table.cell(0, col).value for col in range(table.num_cols)]

    # Header-Index zu DB-Spalte Mapping
    col_index_to_db = {}
    for col_idx, header in enumerate(headers):
        if header is None:
            continue
        # Bereinige den Header (Tab-Prefix entfernen)
        clean_header = header.strip().lstrip('\t')
        if clean_header in COLUMN_MAPPING:
            col_index_to_db[col_idx] = COLUMN_MAPPING[clean_header]

    # Prüfe, ob alle DB-Spalten gemappt wurden
    mapped_db_cols = set(col_index_to_db.values())
    missing = set(DB_COLUMNS) - mapped_db_cols
    if missing:
        print(f"  WARNUNG: Folgende DB-Spalten konnten nicht gemappt werden: {missing}")

    placeholders = ", ".join(["?"] * len(DB_COLUMNS))
    insert_sql = f"INSERT OR IGNORE INTO windmill_units ({', '.join(DB_COLUMNS)}) VALUES ({placeholders})"
    collected_sql = "INSERT INTO collected_windmill_units (unit_MaStR_ID, collected) VALUES (?, 0)"

    count = 0
    batch_windmills = []
    batch_collected = []

    for row_idx in range(1, table.num_rows):
        # Baue eine Zeile mit den Werten in der richtigen Reihenfolge
        row_dict = {}
        for col_idx, db_col in col_index_to_db.items():
            raw_val = table.cell(row_idx, col_idx).value
            row_dict[db_col] = clean_value(raw_val, db_col)

        # Überspringe Zeilen ohne MaStR-ID
        mastr_id = row_dict.get("unit_MaStR_ID")
        if not mastr_id:
            continue

        row_values = [row_dict.get(col) for col in DB_COLUMNS]
        batch_windmills.append(tuple(row_values))
        batch_collected.append((mastr_id,))
        count += 1

        # Batch-Insert alle 5000 Zeilen
        if count % 5000 == 0:
            cursor.executemany(insert_sql, batch_windmills)
            cursor.executemany(collected_sql, batch_collected)
            batch_windmills.clear()
            batch_collected.clear()
            print(f"  ... {count} Zeilen verarbeitet")

    # Rest einfügen
    if batch_windmills:
        cursor.executemany(insert_sql, batch_windmills)
        cursor.executemany(collected_sql, batch_collected)

    print(f"  {count} Windräder importiert")
    return count


def import_sights(cursor: sqlite3.Cursor) -> int:
    """Liest Vergleichs-Sehenswürdigkeiten aus der CSV-Datei."""
    print(f"Lese Sights-CSV: {SIGHTS_SOURCE}")

    count = 0
    with open(SIGHTS_SOURCE, "r", encoding="utf-8") as f:
        reader = csv.DictReader(f)
        for row in reader:
            cursor.execute(
                "INSERT INTO comparable_sights (id, name, height) VALUES (?, ?, ?)",
                (int(row["ID"]), row["Name"], float(row["Höhe"])),
            )
            count += 1

    print(f"  {count} Sehenswürdigkeiten importiert")
    return count


def main():
    # Prüfe Quelldateien
    if not WINDMILL_SOURCE.exists():
        print(f"FEHLER: {WINDMILL_SOURCE} nicht gefunden!")
        sys.exit(1)
    if not SIGHTS_SOURCE.exists():
        print(f"FEHLER: {SIGHTS_SOURCE} nicht gefunden!")
        sys.exit(1)

    # Erstelle Ausgabeverzeichnis
    DB_OUTPUT.parent.mkdir(parents=True, exist_ok=True)

    # Lösche alte DB falls vorhanden
    if DB_OUTPUT.exists():
        DB_OUTPUT.unlink()

    print(f"Erstelle Datenbank: {DB_OUTPUT}")
    conn = sqlite3.connect(str(DB_OUTPUT))
    cursor = conn.cursor()

    # Foreign Keys aktivieren
    cursor.execute("PRAGMA foreign_keys = ON")

    try:
        create_tables(cursor)
        windmill_count = import_windmills(cursor)
        sights_count = import_sights(cursor)

        conn.commit()

        # Statistiken
        cursor.execute("SELECT COUNT(*) FROM windmill_units")
        db_windmills = cursor.fetchone()[0]
        cursor.execute("SELECT COUNT(*) FROM collected_windmill_units")
        db_collected = cursor.fetchone()[0]
        cursor.execute("SELECT COUNT(*) FROM comparable_sights")
        db_sights = cursor.fetchone()[0]
        cursor.execute("SELECT COUNT(DISTINCT federal_state) FROM windmill_units")
        db_states = cursor.fetchone()[0]

        print(f"\n=== Datenbank erfolgreich erstellt ===")
        print(f"  Windräder:        {db_windmills}")
        print(f"  Collected-Einträge: {db_collected}")
        print(f"  Sehenswürdigkeiten: {db_sights}")
        print(f"  Bundesländer:      {db_states}")
        print(f"  Datei:             {DB_OUTPUT}")
        print(f"  Größe:             {DB_OUTPUT.stat().st_size / 1024 / 1024:.1f} MB")

    except Exception as e:
        conn.rollback()
        print(f"FEHLER: {e}")
        raise
    finally:
        conn.close()


if __name__ == "__main__":
    main()
