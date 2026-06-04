# TapTrack PC - JVM Launch Failure - Troubleshooting Guide

## ❌ "Failed to Launch JVM" Error

If you're seeing this error when running the installer or launching the app, follow these solutions:

## Solution 1: Use the Portable EXE (Easiest)

Instead of the MSI installer, use the **standalone EXE**:

```
TapTrack PC-1.0.0.exe
```

- Just double-click the EXE file
- No installation needed
- No registry modifications
- JVM bundled inside

**This is the most reliable option for clean Windows installations.**

---

## Solution 2: Reinstall with Admin Privileges

1. **Right-click** on `TapTrack PC-1.0.0.msi`
2. Select **"Run as Administrator"**
3. Follow the installation wizard
4. Complete installation
5. Try running the app

---

## Solution 3: Manual Install with Detailed Logging

This helps diagnose the exact issue:

```powershell
# Open Command Prompt as Administrator, then run:
msiexec /i "C:\Path\To\TapTrack PC-1.0.0.msi" /L*V "C:\install.log"
```

After installation:
1. Check `C:\install.log` for detailed error messages
2. Look for lines containing "error" or "JVM"
3. Share this log for support

---

## Solution 4: Uninstall and Clean Reinstall

If the app was previously installed but is now failing:

```powershell
# Uninstall via Control Panel
# Or via command line:
msiexec /x "C:\Path\To\TapTrack PC-1.0.0.msi"

# Wait for uninstall to complete, then:
# Delete the app folder (usually C:\Program Files\TapTrack PC)
# Delete remaining files from C:\ProgramData\TapTrack (if exists)

# Now reinstall with the new MSI/EXE
```

---

## Solution 5: Check System Requirements

Ensure your computer meets these requirements:

- **Windows 10 or later** (64-bit)
- **At least 500 MB free disk space**
- **.NET Framework 4.8+** (usually pre-installed on modern Windows)
- **Visual C++ Redistributable** (usually pre-installed)

To verify you have the required runtime:
```powershell
# Check installed redistributables
Get-WmiObject Win32_Product | Where-Object {$_.Name -like "*Visual*" -or $_.Name -like "*Runtime*"}
```

---

## Solution 6: Check Event Viewer for Detailed Errors

Windows logs installation errors in Event Viewer:

1. Press `Win + R`, type `eventvwr.msc`, press Enter
2. Navigate to **Windows Logs > Application**
3. Look for recent errors related to "TapTrack" or "JavaVM"
4. Check the error details for more information

---

## Solution 7: Try the Alternative: Portable Folder

If none of the above work, use the portable version:

1. Extract the bundled files
2. Run `TapTrack PC.exe` directly from the application folder
3. No installation required

---

## ✅ Recommended: Use the Portable EXE

For the best experience on a clean computer:

```
D:\TapTrack-PC\composeApp\build\compose\binaries\main\exe\TapTrack PC-1.0.0.exe
```

Simply double-click and it works! The JVM is already bundled.

---

## Still Having Issues?

If problems persist:

1. **Collect diagnostic information**:
   - Windows version: `winver`
   - System info: `msinfo32`
   - Installation logs from `msiexec`

2. **Try on a different computer** to determine if it's system-specific

3. **Check for conflicting applications** that might interfere with JVM:
   - Antivirus software blocking the JVM
   - System restrictions/Group Policy

4. **Verify the installer wasn't corrupted**:
   ```powershell
   Get-FileHash "TapTrack PC-1.0.0.msi" -Algorithm SHA256
   ```

---

## Technical Details

The application bundles:
- **Java Runtime Environment (JRE) 21**
- **Compiled Kotlin/Compose UI**
- **QR Code scanning library (ZXing)**
- **All required dependencies**

The JVM launch failure typically indicates:
- Installation didn't complete fully
- JVM files weren't extracted properly
- System permissions prevented extraction
- Antivirus blocked the JVM executable

Using the **portable EXE** bypasses most of these issues because it extracts the JVM to a temporary location that's usually less restricted.

---

## Version Information

- **App**: TapTrack PC v1.0.0
- **Java Version**: 21 LTS
- **Build Date**: 2026-04-16
- **Platform**: Windows 64-bit

---

**Recommended Action**: Use `TapTrack PC-1.0.0.exe` for best compatibility! 🚀

