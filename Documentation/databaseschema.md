### Teacher
- id INTEGER PRIMARY KEY AUTOINCREMENT
- name TEXT
- subject TEXT

### Student
- id INTEGER PRIMARY KEY AUTOINCREMENT
- name TEXT
- class INTEGER

### Classes
- id INTEGER PRIMARY KEY AUTOINCREMENT
- teacher_id INTEGER
- FOREIGN KEY (teacher_id) REFERENCES Teacher(id)


![image](./edudb.jpg)

---
``` SQL
CREATE TABLE Teacher (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  subject TEXT
);

CREATE TABLE Student (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  class INTEGER
);

CREATE TABLE Classes (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  teacher_id INTEGER,
  FOREIGN KEY (teacher_id) REFERENCES Teacher(id)
);
```
