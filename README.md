<div align="center">

🛡️ SnoopGuard
On-device privacy protection against shoulder surfing and unattended-device snooping.

<p> <strong>Protect your screen. Detect suspicious activity. Keep evidence local.</strong> </p>

<p> SnoopGuard is an Android privacy and security application designed to detect potential visual observation and unauthorized interaction with a device — with a strong focus on local, privacy-first processing. </p>

<br>

<a href="https://github.com/EthYusuf/secretOS/releases/tag/v.0.0.1"> <img src="https://img.shields.io/badge/Download-APK-111111?style=for-the-badge&logo=android&logoColor=white" alt="Download APK"> </a> &nbsp; <a href="https://github.com/EthYusuf/secretOS"> <img src="https://img.shields.io/badge/View-Repository-24292f?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Repository"> </a>

<br><br>

<img src="https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=flat-square&logo=kotlin&logoColor=white" alt="Kotlin"> <img src="https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=flat-square&logo=android&logoColor=white" alt="Jetpack Compose"> <img src="https://img.shields.io/badge/Android-API%2026%2B-3DDC84?style=flat-square&logo=android&logoColor=white" alt="Android"> <img src="https://img.shields.io/badge/TensorFlow%20Lite-On--Device%20ML-FF6F00?style=flat-square&logo=tensorflow&logoColor=white" alt="TensorFlow Lite"> <img src="https://img.shields.io/badge/Privacy-Local--First-111111?style=flat-square" alt="Privacy">

<br><br>

<img src="docs/screenshots/problem_guide_banner.jpg" alt="SnoopGuard Privacy Protection Overview" width="100%">

</div>

📖 Overview

Modern smartphones contain highly sensitive information — private conversations, authentication codes, photographs, documents, financial applications and personal accounts.

SnoopGuard focuses on two practical privacy threats:

Threat	Description
👀 Shoulder Surfing	Someone visually observing your screen while you are using your device.
📱 Unattended Device Snooping	Someone interacting with your device while you are away from it.

SnoopGuard combines real-time visual threat detection, a decoy lockscreen, and a local forensic gallery into a single Android security experience.

Core Protection Modules
Module	Purpose
🛡️ Live Shield	Monitors the camera view for potential unauthorized observers.
🎭 Decoy Trap	Provides a lockscreen-style protection mode for unattended devices.
🧾 Forensic Gallery	Organizes locally stored security incidents and detection metadata.
✨ Features
🛡️ Live Shield

Real-time protection designed to identify potential unauthorized observers using the device camera.

Highlights
Real-time threat status
Owner biometric profile
Facial landmark analysis
Gaze-related analysis
Configurable detection sensitivity
Visual alerts
Haptic feedback
Optional audio alerts
Local processing
Built-in demonstration mode
🎭 Decoy Trap

A lockscreen-style protection mode designed for situations where your device is left unattended.

Highlights
Dynamic time and date display
Lock-style user interface
Owner-only exit mechanism
Intrusion-triggered camera capture
Local incident storage
Timestamped evidence
Anti-tampering-oriented interaction flow
🧾 Forensic Gallery

A local incident history for reviewing previously detected events.

Highlights
Intruder snapshots
Incident timestamps
Detection confidence
Incident classification
Individual evidence deletion
Complete evidence purge
Optional evidence export workflow
📸 Application Preview

<div align="center">

<img src="docs/screenshots/home_shield.jpg" width="31%" alt="Live Shield Dashboard"> <img src="docs/screenshots/decoy_trap.jpg" width="31%" alt="Decoy Trap"> <img src="docs/screenshots/forensics_log.jpg" width="31%" alt="Forensic Gallery">

<br>

<sub> <b>Live Shield</b> &nbsp;•&nbsp; <b>Decoy Trap</b> &nbsp;•&nbsp; <b>Forensic Gallery</b> </sub>

</div>

🔐 Privacy by Design

Privacy is not an optional feature of SnoopGuard.

The project is designed around a local-first architecture, minimizing the need to transfer security-sensitive information to remote services.

Local Processing

Security-related processing is designed to remain on the device, including:

Facial analysis
Gaze-related processing
Threat assessment
Image processing
Incident storage
Detection metadata
No Cloud Dependency

The core protection workflow is designed to operate without requiring a remote security backend.

This allows the application to be evaluated and used without depending on:

Cloud security servers
Remote biometric processing
Third-party analytics platforms
Mandatory account infrastructure
User Control

SnoopGuard provides controls for:

Camera permissions
Biometric profile management
Incident review
Individual evidence deletion
Complete local data deletion
Demonstration mode

Important: Privacy and security claims should always be verified against the actual source code, Android permissions, dependencies, release configuration and device behavior.

🧠 How SnoopGuard Works
┌──────────────────────────────────────────────────────────────┐
│                         SNOOPGUARD                           │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│   🛡️ LIVE SHIELD     🎭 DECOY TRAP     🧾 FORENSICS         │
│                                                              │
├──────────────────────────────────────────────────────────────┤
│                        PRESENTATION                          │
│                  Jetpack Compose + M3                        │
├──────────────────────────────────────────────────────────────┤
│                         VIEW MODELS                          │
│                     StateFlow / UI State                     │
├──────────────────────────────────────────────────────────────┤
│                       DOMAIN LAYER                           │
│                 Detection & Security Logic                   │
├──────────────────────────────────────────────────────────────┤
│                         DATA LAYER                           │
│                Room / Local Storage / Models                 │
├──────────────────────────────────────────────────────────────┤
│                     DEVICE SERVICES                          │
│             CameraX · Android APIs · Local ML                │
└──────────────────────────────────────────────────────────────┘
Detection Flow
Camera Input
     │
     ▼
┌───────────────┐
│ Local Vision  │
│   Analysis    │
└───────┬───────┘
        │
        ▼
┌───────────────┐
│ Threat        │
│ Assessment    │
└───────┬───────┘
        │
        ├──────────────► No Threat
        │
        ▼
┌───────────────┐
│ Protection    │
│ Response      │
└───────┬───────┘
        │
        ├──► Visual Alert
        ├──► Haptic Feedback
        ├──► Optional Audio
        └──► Incident Logging
                    │
                    ▼
            ┌────────────────┐
            │ Local Forensic │
            │    Gallery     │
            └────────────────┘
🔄 Protection Workflow
01 — Owner Setup

The user establishes an owner profile during application setup.

02 — Enable Protection

The user enables the desired protection mechanisms from the Shield interface.

03 — Local Analysis

The camera view can be analyzed locally for potential observation events.

04 — Threat Detection

When a suspected event is detected, configured responses may include:

Visual notification
Device vibration
Optional audio alert
Incident logging
05 — Evidence Storage

If an enabled protection mechanism records an incident, the relevant information is stored locally.

06 — Forensic Review

Recorded incidents can be reviewed chronologically through the Forensic Gallery.

🏗️ Application Architecture

SnoopGuard follows a modern Android architecture focused on separation of concerns, reactive state management and local data handling.

┌─────────────────────────────────────────────┐
│                UI / PRESENTATION            │
│          Jetpack Compose · Material 3       │
├─────────────────────────────────────────────┤
│                  VIEW MODELS                │
│             StateFlow · UI State             │
├─────────────────────────────────────────────┤
│                  DOMAIN                     │
│       Security · Detection · Use Cases      │
├─────────────────────────────────────────────┤
│                   DATA                      │
│         Room · Repositories · Models        │
├─────────────────────────────────────────────┤
│             DEVICE / PLATFORM               │
│       CameraX · Android APIs · Local ML     │
└─────────────────────────────────────────────┘
Main Application Areas
Shield Dashboard
Master protection toggle
Threat status
Owner profile status
Detection controls
Educational scenarios
Demonstration mode
Protection statistics
Decoy Trap
Lock-style interface
Intrusion trigger
Camera capture workflow
Local evidence handling
Owner exit authentication
Forensics
Incident timeline
Captured images
Detection metadata
Confidence information
Evidence deletion
Complete purge
Settings
Detection sensitivity
Alert preferences
Biometric profile management
Camera permission controls
Privacy controls
Application reset
⚡ Technical Capabilities
Capability	Description
Live Shield	Real-time camera-based observation monitoring
Gaze Analysis	Facial landmark and gaze-related threat analysis
Decoy Trap	Lock-style interface for unattended-device protection
Biometric Profile	Local owner-profile calibration
Forensic Gallery	Timestamped local incident history
Sensitivity Control	Adjustable protection sensitivity
Demo Mode	Hardware-free feature demonstration
Local Storage	Device-resident incident persistence
Privacy Controls	Profile, permission and data management
🧰 Technology Stack
Technology	Role
Kotlin 2.0	Primary development language
Jetpack Compose	Declarative Android UI
Material 3	Modern Android design system
MVVM	Presentation architecture
Clean Architecture	Separation of application responsibilities
StateFlow	Reactive state management
Kotlin Coroutines	Asynchronous operations
Room	Local database abstraction
KSP	Compile-time code generation
CameraX	Camera lifecycle and hardware abstraction
TensorFlow Lite	On-device ML inference
NNAPI	Hardware-accelerated ML where supported
📱 Requirements
Minimum Requirements
Requirement	Version
Android	8.0 / API 26+
JDK	17
Gradle	8.x+
Camera	Front-facing camera for camera-dependent features
Recommended Development Environment
Android Studio Iguana, Jellyfish, Ladybug or newer
Physical Android device for camera testing
Android Emulator for UI and non-camera testing

Device behavior may vary depending on Android version, camera hardware, manufacturer restrictions and available hardware acceleration.

📥 Download

A pre-built APK is available through the GitHub release page.

Current Release

v.0.0.1

<div align="center">

<a href="https://github.com/EthYusuf/secretOS/releases/tag/v.0.0.1"> <img src="https://img.shields.io/badge/Download%20SnoopGuard-APK-111111?style=for-the-badge&logo=android&logoColor=white"> </a>

</div>

Installation
Download the APK from the release page.
Transfer it to your Android device if necessary.
Install the application.
Review the requested permissions.
Enable only the features you want to use.
Use Demonstration Mode first if you want to explore the interface without camera access.

Android may require permission to install applications obtained from sources outside Google Play.

🔨 Build From Source
1. Clone the Repository
git clone https://github.com/EthYusuf/secretOS.git
cd secretOS
2. Clean the Project
./gradlew clean
3. Build a Debug APK
./gradlew assembleDebug
4. Run Unit Tests
./gradlew :app:testDebugUnitTest
5. Run Instrumented Tests
./gradlew :app:connectedDebugAndroidTest
6. Build a Release APK
./gradlew assembleRelease

A release build requires a properly configured Android signing setup.

🧪 Testing

SnoopGuard can be evaluated at multiple levels.

UI & Functional Testing

Validate:

Navigation
Protection state changes
Settings
Permission flows
Incident display
Evidence deletion
Demonstration mode
Unit Testing
./gradlew :app:testDebugUnitTest
Instrumented Testing
./gradlew :app:connectedDebugAndroidTest
Physical Device Testing

Camera-dependent functionality should be tested on real Android hardware.

Camera APIs, performance, background restrictions and manufacturer-specific behavior can vary significantly across devices.

🔒 Security Considerations

SnoopGuard handles potentially sensitive information. Security should therefore be treated as an ongoing engineering process.

Before production distribution, review:

Android permission declarations
Camera lifecycle behavior
Local database encryption
Android Keystore usage
Backup configuration
APK signing
Dependency vulnerabilities
Debug logging
Evidence export functionality
Screenshot and screen-recording behavior
Data deletion guarantees
Security Disclosure

If you discover a security vulnerability, please avoid publicly posting sensitive exploit details before the issue can be responsibly assessed.

For general bugs and feature requests, use GitHub Issues.

🗺️ Roadmap

Development priorities may evolve as the project grows.

Detection

Improve observation detection

Improve environmental adaptation

Expand device compatibility

Improve detection metadata

Privacy & Security

Multi-user trusted profiles

Additional privacy controls

Optional encrypted backup

Optional encrypted synchronization

Engineering

Expand automated test coverage

Improve accessibility

Improve hardware compatibility

Expand documentation

Roadmap items are subject to change.

🤝 Contributing

Contributions, ideas, bug reports and security feedback are welcome.

Suggested Workflow
Fork
  │
  ▼
Create Feature Branch
  │
  ▼
Implement Changes
  │
  ▼
Run Tests
  │
  ▼
Commit
  │
  ▼
Open Pull Request

Please keep security-sensitive changes documented and avoid introducing unnecessary data collection or network dependencies.

🐛 Issues & Feedback

For bugs, feature requests and general project discussions, please use GitHub Issues.

When reporting a bug, include:

Android version
Device model
SnoopGuard version
Steps to reproduce
Expected behavior
Actual behavior
Relevant logs or screenshots, if safe to share
Please do not include
Private photographs
Biometric information
Passwords
Authentication tokens
Personal credentials
Other sensitive information

in public issues.

⚖️ Responsible Use

SnoopGuard is intended for personal device protection, privacy awareness and security research/education.

Appropriate Uses
Protecting your own device from shoulder surfing
Detecting potential unauthorized interaction with your own device
Testing mobile privacy concepts
Learning about on-device computer vision
Demonstrating privacy-preserving security architecture
Do Not Use SnoopGuard For
Covert surveillance of other people
Recording people without appropriate consent where required by law
Monitoring devices you do not own or have permission to manage
Circumventing legitimate security or management systems
Activities that violate applicable privacy or surveillance laws

Camera-based functionality should always be used responsibly and in accordance with applicable law.

📄 License

SnoopGuard is distributed under the MIT License.

See LICENSE for the complete license text.

⚠️ Disclaimer

SnoopGuard is a security and privacy project intended for educational and personal protection purposes.

It should be treated as an additional privacy layer, not a replacement for:

Android's native security mechanisms
Strong device authentication
Encryption
Secure application practices
Established endpoint security controls

Detection accuracy can vary depending on:

Lighting conditions
Camera quality
Device hardware
Viewing angle
Environmental conditions
Software configuration

Privacy, recording, biometric-data and surveillance laws vary by jurisdiction. Users are responsible for using the application lawfully and obtaining any consent required by applicable law.

<div align="center">

🛡️ SnoopGuard
Your screen is for your eyes only.

<br>

Privacy Protection
On-Device Processing
Responsible Security

<br>

Built with ❤️ for privacy-conscious users.

<br>

<a href="https://github.com/EthYusuf/secretOS"> <img src="https://img.shields.io/badge/GitHub-EthYusuf-111111?style=for-the-badge&logo=github&logoColor=white"> </a>

</div>
