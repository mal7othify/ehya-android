import sqlite3
import json

# Connect to SQLite database (or create it if it doesn't exist)
conn = sqlite3.connect('../app/src/main/assets/sunan.db')
cursor = conn.cursor()

# Create Categories table
cursor.execute('''
CREATE TABLE Categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    title TEXT UNIQUE NOT NULL,
    imageId TEXT,
    color TEXT
);
''')

# Create Sunan table
cursor.execute('''
CREATE TABLE Sunan (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    title TEXT NOT NULL,
    quantity TEXT,
    category_id INTEGER NOT NULL, -- Ensure NOT NULL
    hadith TEXT NOT NULL, -- Ensure NOT NULL
    strength TEXT NOT NULL, -- Ensure NOT NULL
    howto TEXT NOT NULL, -- Ensure NOT NULL
    swipeResult TEXT DEFAULT 'NONE',
    FOREIGN KEY (category_id) REFERENCES Categories(id) ON DELETE CASCADE
);

''')


# Function to add data to the Categories table
def add_category(category_title, imageId, color):
  cursor.execute('''
    INSERT OR IGNORE INTO Categories (title, imageId, color) VALUES (?, ?, ?)
    ''', (category_title, imageId, color))
  conn.commit()
  # Return the id of the category
  cursor.execute('SELECT id FROM Categories WHERE title = ?', (category_title,))
  return cursor.fetchone()[0]


# Function to add data to the Sunan table
def add_sunnah(title, quantity, category_id, hadith, strength, howto):
  cursor.execute('''
    INSERT INTO Sunan (title, quantity, category_id, hadith, strength, howto)
    VALUES (?, ?, ?, ?, ?, ?)
    ''', (title, quantity, category_id, hadith, strength, howto))
  conn.commit()


# Function to remove Sunnah by ID
def remove_sunnah(sunnah_id):
  cursor.execute('''
    DELETE FROM Sunan WHERE id = ?
    ''', (sunnah_id,))
  conn.commit()


# Function to remove category by ID (if no Sunan is linked to it)
def remove_category(category_id):
  cursor.execute('''
    DELETE FROM Categories WHERE id = ? AND NOT EXISTS
    (SELECT 1 FROM Sunan WHERE category_id = Categories.id)
    ''', (category_id,))
  conn.commit()


# Load JSON data
with open('sunan.json', 'r', encoding='utf-8') as f:
  sunan_data = json.load(f)

# Insert data into the database
for sunnah in sunan_data:
  # Add category and get its id
  category_id = add_category(sunnah['category']['title'], sunnah['category']['imageId'], sunnah['category']['color'])

  # Add sunnah data
  add_sunnah(sunnah['title'], sunnah['quantity'], category_id, sunnah['hadith'], sunnah['strength'], sunnah['howto'])

# Close the database connection
conn.close()
