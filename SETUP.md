# Quick Start Setup Guide

## 30-Second Setup

1. **Open project in VS Code:**
   ```bash
   code ConnectSphereLite
   ```

2. **Install Extension Pack for Java** (if not already installed)
   - Open Extensions (Ctrl+Shift+X)
   - Search for "Extension Pack for Java" by Microsoft
   - Click Install

3. **Run the application:**
   - Open `src/ConnectSphereApp.java`
   - Click the **Run** button (or press Ctrl+F5)
   - The application window will appear

4. **Done!** Start adding friends 🎉

## Detailed Setup (If Issues Arise)

### Step 1: Install JDK 17+
```bash
# Check your JDK version
java -version

# Should show: openjdk version "17" or higher
```

If you don't have JDK 17, download from:
- https://adoptium.net/
- https://www.oracle.com/java/technologies/downloads/

### Step 2: Verify Project Structure
Your project folder should look like:
```
ConnectSphereLite/
 ├── src/
 │    ├── ConnectSphereApp.java
 │    ├── FriendUI.java
 │    ├── Friend.java
 │    ├── FriendDAO.java
 │    └── DBConnection.java
 ├── lib/
 │    └── sqlite-jdbc-3.36.0.jar
 └── .vscode/
      ├── settings.json
      └── launch.json
```

### Step 3: Configure VS Code

**Install Extensions:**
- Open Extensions: `Ctrl+Shift+X`
- Search and install:
  - "Extension Pack for Java" by Microsoft
  - "Project Manager for Java" (optional)

**Verify Java Configuration:**
- Press `Ctrl+Shift+P`
- Type "Java: Configure Runtime"
- Make sure JDK 17+ is selected

### Step 4: Compile & Run

**Using VS Code:**
1. Right-click `ConnectSphereApp.java`
2. Select "Run"
3. Or press `Ctrl+F5`

**Using Terminal:**
```bash
# Navigate to project directory
cd ConnectSphereLite

# Compile all Java files
javac -cp "lib/*" -d bin src/connectsphere/*.java

# Run the application
java -cp "lib/*:bin" connectsphere.ConnectSphereApp
```

## First Run

✅ Database will be created automatically: `connectsphere.db`
✅ The friends table will be initialized
✅ The UI will open with an empty friends list
✅ Start adding friends!

## Common Issues & Solutions

### Issue: "Error: Could not find or load main class"
**Solution:**
- Ensure `ConnectSphereApp.java` has `package connectsphere;` at the top
- Rebuild: Press `Ctrl+Shift+P` → "Clean Workspace"
- Restart VS Code

### Issue: "JAR files not found"
**Solution:**
- Verify `sqlite-jdbc-3.36.0.jar` is in `lib/` folder
- Update `.vscode/settings.json`:
  ```json
  "java.project.referencedLibraries": ["lib/**/*.jar"]
  ```

### Issue: Database permission error
**Solution:**
- Delete `connectsphere.db` file
- Run again (it will be recreated)
- Ensure folder has write permissions

### Issue: Port already in use
**Solution:** Not applicable - this is a standalone app with no ports

## Key Keyboard Shortcuts

| Action | Shortcut |
|--------|----------|
| Run Application | `Ctrl+F5` |
| Debug | `F5` |
| Build | `Ctrl+Shift+B` |
| Format Code | `Shift+Alt+F` |
| Clean Build | `Ctrl+Shift+P` → "Clean Workspace" |

## Development Tips

### Add Sample Data
1. Click "➕ Add Friend"
2. Fill in details:
   - Name: John Doe
   - Email: john@example.com
   - Phone: 555-1234
   - City: New York
3. Click OK

### Test Search
1. Add a friend first
2. Type part of their name/email/phone in search field
3. Press Enter or click Search

### Test Update
1. Select friend from table
2. Click "✏️  Update"
3. Change any field
4. Click OK

## System Requirements

| Requirement | Minimum | Recommended |
|-------------|---------|-------------|
| JDK Version | 17 | 21 LTS |
| RAM | 512 MB | 2 GB |
| Disk Space | 50 MB | 200 MB |
| OS | Windows/Mac/Linux | Any |
| IDE | VS Code | Latest |

## Next Steps

- ✅ Explore the code in `src/` folder
- ✅ Customize the UI colors in `FriendUI.java`
- ✅ Add more fields to the Friend class
- ✅ Implement data export/import features
- ✅ Add dark mode support

## Need Help?

- Check the main **README.md** for detailed documentation
- Review **FriendUI.java** for UI code
- Check **FriendDAO.java** for database operations
- Look at **DBConnection.java** for connection setup

---

**Ready to go!** Click Run and enjoy ConnectSphere Lite! 🚀
