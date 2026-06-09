# TapTrack PC

A desktop companion application for the TapTrack ecosystem, responsible for device discovery, connection management, and real-time communication with Android clients over a local network.

Built using Kotlin Multiplatform and Compose for Desktop, TapTrack PC acts as the communication layer between mobile devices and native desktop input controls.

**Version:** 1.1.0

<img width="1536" height="1024" alt="taptrack_pc_hero" src="https://github.com/user-attachments/assets/7954808d-af1b-4961-bc96-529360ef3e42" />


---

## Overview

TapTrack PC serves as the desktop-side communication server for the TapTrack platform.

The application listens for incoming connections from Android devices, manages active sessions, processes incoming UDP packets, and translates user interactions into desktop control events.

The system was designed with a focus on:

* Low-latency communication
* Reliable connection management
* Responsive desktop performance
* Scalable architecture
* Real-time device interaction

---

## System Architecture

```text
┌───────────────────────┐
│  Android Application  │
└───────────┬───────────┘
            │
            │ UDP Communication
            ▼
┌───────────────────────┐
│      TapTrack PC      │
│   Desktop Server      │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│ Native Desktop Events │
└───────────────────────┘
```

---

## Key Features

### Device Discovery

Receives and identifies incoming client connections across a local network.

### Real-Time Communication

Processes UDP packets with minimal overhead to support responsive remote interaction.

### Connection Management

Maintains active device sessions and handles connection lifecycle events safely.

### Reactive Desktop Interface

Displays live connection information and application state using a modern desktop UI.

### Multithreaded Networking

Network operations run independently from the UI to ensure smooth user interaction and stable performance.

### Lifecycle-Aware Resource Management

Sockets and networking resources are created and disposed of safely to prevent connection conflicts and resource leaks.

---

## Technical Highlights

### Desktop Development

* Kotlin Multiplatform
* Compose for Desktop
* JVM Desktop Target

### Networking

* UDP Socket Communication
* Datagram Packet Processing
* Custom Communication Protocol
* Client Discovery
* Connection Tracking

### State Management

* Kotlin Coroutines
* StateFlow
* Reactive UI Updates

### Architecture

* MVVM Architecture
* Dependency Injection Ready
* Separation of Concerns
* Modular Design

---

## How It Works

```text
Android Device
        │
        ▼
QR Pairing
        │
        ▼
UDP Connection
        │
        ▼
TapTrack PC Server
        │
        ▼
Packet Processing
        │
        ▼
Desktop Input Events
```

---

## Performance Considerations

TapTrack PC was designed with responsiveness as a primary objective.

Key optimizations include:

* Lightweight UDP communication
* Non-blocking network operations
* Coroutine-based concurrency
* Reactive state management
* Safe socket lifecycle handling
* Minimal packet processing overhead

---

## Installation

Clone the repository:

```bash
git clone https://github.com/Sachinslemenceu/TapTrack-PC
```

Build and run the desktop application using Gradle:

```bash
.\gradlew :composeApp:run
```

Ensure the Android client and desktop application are connected to the same local network.

---

## Companion Android Application

TapTrack PC works together with the Android client application.

Android Repository:

https://github.com/Sachinslemenceu/TapTrack

The Android application provides touchpad functionality and communicates with TapTrack PC over UDP.

---

## Current Capabilities

Version 1.1.0 currently supports:

* Client Discovery
* Device Pairing
* UDP Communication
* Connection Monitoring
* Real-Time Packet Processing
* Desktop Session Management

---

## Roadmap

Future improvements under consideration:

* Multi-Device Support
* Device Management Dashboard
* Enhanced Connection Diagnostics
* Secure Session Authentication
* Custom Input Profiles
* Performance Monitoring Tools

---

## Why TapTrack PC?

TapTrack PC demonstrates practical experience in:

* Desktop Application Development
* Real-Time Networking
* UDP Communication Systems
* Kotlin Multiplatform
* Concurrent Programming
* Software Architecture Design
* End-to-End Product Development

---

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a pull request

---

## Author

Sachin Pradeep Singh

Software Engineer focused on Android Development, Real-Time Systems, Product Engineering, and Cross-Platform Applications.

---

## License

This project is licensed under the MIT License.
