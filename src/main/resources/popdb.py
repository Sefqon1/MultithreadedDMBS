import sqlite3
import random

# Vor- und Nachnamen als Quelle für die zufällige Generierung von Lehrer- und Schüler-Namen
nachnamen = ["Müller", "Schmidt", "Schneider", "Fischer", "Meyer", "Weber", "Schulz", "Wagner", "Becker", "Hoffmann"]
vornamen = ["Max", "Paul", "Anna", "Laura", "Julia", "Lena", "Felix", "Simon", "Tom", "Marie"]

# Verbindung zur Datenbank herstellen
conn = sqlite3.connect('EduDB.db')
c = conn.cursor()

# Zufällige Lehrer-Datensätze einfügen
for i in range(1, 11):
    lehrer_name = f"{random.choice(vornamen)} {random.choice(nachnamen)}"
    fach = random.choice(["Mathematik", "Deutsch", "Englisch", "Biologie", "Chemie", "Physik"])
    c.execute("INSERT INTO Teacher (name, subject) VALUES (?, ?)", (lehrer_name, fach))

# Zufällige Klassen-Datensätze einfügen
for i in range(1, 21):
    lehrer_id = random.randint(1, 10)
    c.execute("INSERT INTO Classes (teacher_id) VALUES (?)", (lehrer_id,))

# Zufällige Schüler-Datensätze einfügen
for i in range(1, 51):
    class_id = random.randint(1, 20)
    schueler_name = f"{random.choice(vornamen)} {random.choice(nachnamen)}"
    c.execute("INSERT INTO Student (name, class) VALUES (?, ?)", (schueler_name, class_id))




# Datenbank speichern und Verbindung schließen
conn.commit()
conn.close()
