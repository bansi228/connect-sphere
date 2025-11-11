# ConnectSphere Lite

A modern, lightweight friend management application built with Java Swing and SQLite.

## Features

✅ **Add Friends** - Add new friends with name, email, phone, and city
✅ **View All Friends** - Display all friends in a clean JTable interface
✅ **Search Friends** - Search by name, email, or phone number
✅ **Update Friend** - Edit existing friend information
✅ **Delete Friend** - Remove friends from your database
✅ **About Dialog** - View application information

## Project Structure

```
ConnectSphereLite/
 ├── .vscode/
 │    ├── settings.json       (VS Code Java settings)
 │    └── launch.json         (VS Code debug configuration)
 ├── src/
 │    ├── ConnectSphereApp.java   (Entry point)
 │    ├── FriendUI.java           (Main Swing UI)
 │    ├── Friend.java             (Model class)
 │    ├── FriendDAO.java          (Data Access Object)
 │    └── DBConnection.java       (Database connection)
 ├── lib/
 │    └── sqlite-jdbc-3.36.0.jar  (SQLite JDBC driver)
 ├── connectsphere.db             (Auto-created database)
 └── README.md
```

## Requirements

- **JDK:** 17 or higher
- **IDE:** VS Code with Extension Pack for Java
- **Libraries:** sqlite-jdbc-3.36.0.jar (included)

## Installation & Setup

### 1. Open in VS Code

```bash
cd ConnectSphereLite
code .
```

### 2. Install Extensions

Open VS Code and install:
- **Extension Pack for Java** (Microsoft)
- Includes: Language Support for Java, Debugger for Java, Test Runner for Java, Maven for Java, Visual Studio IntelliCode

### 3. Configure Classpath

The classpath is automatically configured via `.vscode/settings.json`:
- Source paths: `src/`
- Output path: `bin/`
- Referenced libraries: `lib/**/*.jar`

### 4. Run the Application

**Option A: Using VS Code**
1. Open `src/ConnectSphereApp.java`
2. Click **Run** or press `Ctrl+F5`

**Option B: Command Line**
```bash
javac -cp "lib/*" -d bin src/connectsphere/*.java
java -cp "lib/*:bin" connectsphere.ConnectSphereApp
```

**Option C: VS Code Debug**
1. Press `F5` or go to **Run & Debug**
2. Select "Launch ConnectSphere Lite"
3. Click **Start Debugging**

## Database

- **Location:** `connectsphere.db` (in project root)
- **Auto-initialization:** Database and tables are created automatically on first run
- **Type:** SQLite (embedded, no server required)

### Database Schema

```sql
CREATE TABLE friends (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT NOT NULL,
    city TEXT NOT NULL
)
```

## Usage

### Add a Friend
1. Click **"➕ Add Friend"** button
2. Enter name, email, phone, and city
3. Click OK to save

### View Friends
- All friends are displayed in the main table on startup
- Refresh with **"🔄 Refresh"** button

### Search Friends
1. Enter search term in the search field (name, email, or phone)
2. Click **"🔍 Search"** or press Enter
3. Click **"🔄 Refresh"** to see all friends again

### Update Friend
1. Select a friend from the table
2. Click **"✏️  Update"** button
3. Modify the information
4. Click OK to save changes

### Delete Friend
1. Select a friend from the table
2. Click **"❌ Delete"** button
3. Confirm deletion

## Architecture

- **Friend.java** - POJO model for friend data
- **DBConnection.java** - SQLite connection management and database initialization
- **FriendDAO.java** - CRUD operations (Create, Read, Update, Delete)
- **FriendUI.java** - Swing GUI with tables and dialogs
- **ConnectSphereApp.java** - Application entry point

## Technical Details

- **UI Framework:** Java Swing with modern look and feel
- **Database:** SQLite 3 with JDBC driver
- **Connection:** Embedded (no server required)
- **Thread Safety:** PreparedStatements for SQL injection prevention
- **Error Handling:** Comprehensive try-catch with user feedback

## Troubleshooting

### "JAR file not found" error
- Ensure `sqlite-jdbc-3.36.0.jar` is in the `lib/` folder
- Check `.vscode/settings.json` references `lib/**/*.jar`

### "Cannot find symbol" errors
- Rebuild: Press `Ctrl+Shift+P` → "Clean Workspace" → Restart VS Code
- Ensure all files are in `src/connectsphere/` directory

### Database connection fails
- Check if `connectsphere.db` has write permissions
- Delete `connectsphere.db` to force recreation

## Version

**ConnectSphere Lite v1.0**

## License

Free to use and modify.

---

**Happy friend managing! 🌐**
