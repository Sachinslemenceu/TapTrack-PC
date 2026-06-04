# How to Run TapTrack PC for Debugging

## ⚡ Quick Start - Run the App Now

### Option 1: Regular Debug Run
```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRun
```

**What it does:**
- Compiles your Kotlin code
- Starts the app in a window
- Closes when you close the app window

### Option 2: Hot Reload Run (Recommended for Development)
```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRunHot
```

**What it does:**
- Starts the app with hot reload enabled
- Make changes to code → save → see changes immediately
- No need to restart
- ⭐ **BEST for active development**

---

## 📋 First Time Setup (If Needed)

If it's your first time:

```powershell
# Go to project root
cd D:\TapTrack-PC

# Download all dependencies (one-time, ~5-10 minutes)
.\gradlew.bat build

# Then run with hot reload
.\gradlew.bat desktopRunHot
```

---

## 🎮 Running in Your IDE (JetBrains IDE)

If you're using IntelliJ IDEA or similar JetBrains IDE:

1. Open the project in IDE
2. Navigate to: `composeApp > build.gradle.kts`
3. Find the `desktopRun` or `desktopRunHot` task in Gradle panel
4. Right-click → **Run** or double-click to run

Or press: **Ctrl+Shift+A** → Search "desktopRun" → Run

---

## 🔧 What Each Command Does

| Command | Purpose | Hot Reload | Speed |
|---------|---------|-----------|-------|
| `desktopRun` | Normal debug run | ❌ No | ⚡ Fast |
| `desktopRunHot` | Hot reload run | ✅ Yes | 🔄 Restart needed |
| `build` | Build to JAR/installers | - | ⏱️ Slow |
| `packageDistributionForCurrentOS` | Create MSI/EXE installers | - | ⏱️ Very Slow |

---

## 🛑 Stopping the App

- **Click the X button** on the app window to close it
- **Ctrl+C** in the terminal if using command line
- App will exit and return to terminal prompt

---

## 💡 Tips for Development

### Make Quick Code Changes
```powershell
.\gradlew.bat desktopRunHot
# Then edit your code and save
# Changes appear immediately (most of the time)
```

### Debug Console Output
The console will show:
- Logs from your app
- Stack traces if crashes occur
- Useful for debugging QR code scanning, etc.

### Rebuild if Hot Reload Doesn't Work
```powershell
# Stop current run (Ctrl+C)
# Then run:
.\gradlew.bat desktopRun
```

---

## 📱 Typical Development Flow

1. **Start debug run:**
   ```powershell
   .\gradlew.bat desktopRunHot
   ```

2. **Edit your code** in the IDE
   - Change UI, fix bugs, add features

3. **Save the file** (Ctrl+S)
   - Changes reload automatically

4. **Test in the running app window**
   - Try your QR code scanner
   - Check UI responsiveness

5. **Stop when done** (close window or Ctrl+C)

---

## 🚀 Build Order

When developing:
1. **Use `desktopRunHot`** ← Most of the time (fast iteration)
2. **Use `desktopRun`** ← If hot reload fails (full rebuild)
3. **Use `build`** ← When you want to test the JAR
4. **Use `packageDistributionForCurrentOS`** ← Ready to distribute (creates MSI/EXE)

---

## ⚙️ Environment Setup (First Time Only)

Make sure you have:
- ✅ Java 21 installed (check: `java -version`)
- ✅ Gradle installed (check: `gradle -v`)
- ✅ Git (if cloning the project)

Check all at once:
```powershell
java -version
gradle -v
git --version
```

---

## 🎯 Next Steps

**To start debugging right now:**
```powershell
cd D:\TapTrack-PC
.\gradlew.bat desktopRunHot
```

Then edit your code and watch it update in real-time! 🎉

---

## Common Issues & Fixes

### "Command not found: gradlew.bat"
- Make sure you're in `D:\TapTrack-PC` (project root)
- Check: `dir *.bat` should show `gradlew.bat`

### "Build failed"
```powershell
# Clean build
.\gradlew.bat clean desktopRun
```

### "Out of memory"
JVM already configured with 1GB in your build.gradle.kts, should be fine.

### "App doesn't start"
- Check console output for error messages
- Check your `MainKt` file exists and is correct
- Run: `.\gradlew.bat clean build desktopRun`

---

**Ready to debug! 🚀 Start with:** `.\gradlew.bat desktopRunHot`

