# Run TapTrack PC from Command Line (No Installation)

## ⚡ Quick Commands

### Option 1: Run Normally (Compiles & Runs)
```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRun
```

### Option 2: Run with Hot Reload (Recommended for Dev)
```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRunHot
```

---

## What Each Does

| Command | What Happens |
|---------|--------------|
| `desktopRun` | Compiles code → Runs app → Closes when you exit |
| `desktopRunHot` | Compiles → Runs with live editing → Edit code & see changes instantly |

---

## 🚀 Try These Commands Right Now

**First time? This downloads everything needed (~5-10 min):**
```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRun
```

**Then just run it again whenever you want:**
```powershell
.\gradlew.bat desktopRun
```

---

## 🔥 For Active Development (Hot Reload)

```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRunHot
```

Then:
1. App window opens
2. Edit your `.kt` files
3. Save file (Ctrl+S)
4. Changes appear in running app automatically
5. Close window to stop

---

## ⏹️ Stop the App

- **Close the window**, or
- **Press Ctrl+C** in terminal

---

## 📋 Full Command Breakdown

```
.\gradlew.bat          ← Gradle wrapper (no need to install Gradle separately)
desktopRun             ← Task name for desktop app
```

That's it! No installation needed.

---

## ❌ If It Fails

**"Cannot find gradlew.bat":**
- Make sure you're in `D:\TapTrack-PC` (project root)
- Check: `dir gradlew.bat` should show the file

**"Build failed":**
```powershell
.\gradlew.bat clean desktopRun
```

---

## 🎯 Most Common Commands

```powershell
# Run once
.\gradlew.bat desktopRun

# Run with live editing
.\gradlew.bat desktopRunHot

# Clean & rebuild
.\gradlew.bat clean desktopRun

# Just build (no run)
.\gradlew.bat build
```

---

**Start here:** `.\gradlew.bat desktopRun`

