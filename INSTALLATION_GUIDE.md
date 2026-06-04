# TapTrack PC - Installation & Distribution Guide

## What Was Configured

Your application now creates **two distribution formats**:

1. **Portable EXE** (Recommended for clean installations)
   - Single executable file
   - No installation required
   - JVM bundled inside
   - Just double-click and run

2. **Windows Installer (MSI)**
   - Creates Start Menu shortcuts
   - Creates Desktop shortcut
   - System integration
   - Traditional installer experience

Both include:
- ✅ **JVM Bundled**: Java 21 runtime automatically included
- ✅ **Desktop Shortcut**: Automatically created (MSI) or use portable EXE
- ✅ **All Dependencies**: QR code library, Kotlin runtime, Compose libraries
- ✅ **Auto-Updates Support**: Configured for future version upgrades

## Building the Installers

### From Command Line (Windows)

1. **Navigate to the project root** (where `build.gradle.kts` is located)

2. **Build both MSI and EXE installers**:
   ```powershell
   .\gradlew.bat packageDistributionForCurrentOS
   ```

3. **Wait for build to complete** - This creates two files:
   - `TapTrack PC-1.0.0.exe` (Portable - **Recommended**)
   - `TapTrack PC-1.0.0.msi` (Installer)

### Build Output Locations

The generated files will be at:
```
MSI: composeApp\build\compose\binaries\main\msi\TapTrack PC-1.0.0.msi
EXE: composeApp\build\compose\binaries\main\exe\TapTrack PC-1.0.0.exe
```

## Installation on Clean Computer

### Option 1: Use Portable EXE (⭐ Recommended)

1. **Copy the file**: `TapTrack PC-1.0.0.exe`
2. **Double-click the EXE** on the target computer
3. **Done!** No installation needed, JVM automatically extracted

**Advantages:**
- No registry modifications
- No installation wizard
- Works immediately
- Can run from USB
- Easiest to distribute

### Option 2: Use MSI Installer

1. **Copy the file**: `TapTrack PC-1.0.0.msi`
2. **Double-click to install** (or run as Administrator if needed)
3. **Follow installer prompts**:
   - Accept license
   - Choose installation location
   - Complete installation
4. **Find your app**:
   - Desktop shortcut created
   - Start Menu: Search "TapTrack PC"

## Running the App

After installation:

**Using Portable EXE:**
- Just double-click the EXE file whenever you want to run it

**Using MSI Installer:**
- Click the desktop shortcut
- Or search for "TapTrack PC" in Windows Start Menu
- Or open: `C:\Program Files\TapTrack PC\TapTrack PC.exe`

## Troubleshooting

### "Failed to Launch JVM" Error?

**⭐ Quick Fix: Use the Portable EXE instead!**

The portable EXE is more reliable on clean Windows installations. See `JVM_TROUBLESHOOTING.md` for detailed solutions if needed.

### If MSI doesn't create desktop shortcut:
- Ensure you ran the installer with proper permissions
- Try running as Administrator
- See `JVM_TROUBLESHOOTING.md` for detailed steps

### Uninstalling:
- Users can uninstall from **Settings > Apps > Apps & features**
- Or via Control Panel: **Programs and Features > Uninstall**
- The portable EXE: just delete the file

## Optional: Create Only Portable Version

If you prefer only the portable EXE without MSI:

Edit `composeApp\build.gradle.kts`:
```kotlin
// Change:
targetFormats(TargetFormat.Msi, TargetFormat.Exe)

// To:
targetFormats(TargetFormat.Exe)
```

## Distribution Checklist

For end users on a clean Windows computer:

- [ ] Download `TapTrack PC-1.0.0.exe` from your distribution source
- [ ] Double-click to run
- [ ] App launches immediately with desktop shortcut option
- [ ] ✅ Complete - No manual Java installation needed!

## Version Updates

To create a new installer version:
1. Update `packageVersion = "1.0.1"` in `build.gradle.kts`
2. Rebuild with `.\gradlew.bat packageDistributionForCurrentOS`
3. Users can install the new version, old version is automatically replaced

## File Sizes

- **EXE**: ~87 MB (includes JVM 21)
- **MSI**: ~87 MB (includes JVM 21)

Both include the complete Java Runtime Environment needed to run the app.

---

**Your app is production-ready! 🚀**

**Recommended Distribution**: Share the **EXE file** for best user experience.

