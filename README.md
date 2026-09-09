🛡️ SnoopGuard

On-device privacy protection for shoulder surfing and unattended-device snooping.

SnoopGuard is an Android privacy and security application built around a simple idea: sensitive information on your phone should stay under your control.

It combines real-time visual threat detection, a decoy lockscreen, and a local forensic gallery into a privacy-first security experience designed to operate primarily on the device.

<p align="center">
  <img src="docs/screenshots/problem_guide_banner.jpg" alt="SnoopGuard privacy protection overview" width="100%">
</p>

<p align="center">
  <a href="https://github.com/EthYusuf/secretOS/releases/tag/v.0.0.1">
    <img src="https://img.shields.io/badge/Download-APK-111111?style=for-the-badge&logo=android&logoColor=white" alt="Download APK">
  </a>
  <a href="https://github.com/EthYusuf/secretOS">
    <img src="https://img.shields.io/badge/GitHub-Repository-111111?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Repository">
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin 2.0">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=android&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Android-API%2026%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Privacy-On--Device-00A86B?style=for-the-badge" alt="On-device privacy">
</p>

✨ What is SnoopGuard?

Modern phones contain banking information, private conversations, authentication codes, photographs, documents, and other sensitive data.

SnoopGuard focuses on two practical privacy threats:

Shoulder surfing — someone viewing your screen while you are using your phone.

Unattended-device snooping — someone interacting with your phone while you are away.

The application is organized around three core areas:

Module

Purpose

🛡️ Live Shield

Monitors the camera view for potential unauthorized observers

🎭 Decoy Trap

Presents a convincing lockscreen-style interface and records intrusion events

🧾 Forensic Gallery

Organizes captured incidents with timestamps and detection metadata

📸 Application Preview

<p align="center">
  <img src="docs/screenshots/home_shield.jpg" width="31%" alt="SnoopGuard Live Shield Dashboard">
  &nbsp;&nbsp;
  <img src="docs/screenshots/decoy_trap.jpg" width="31%" alt="SnoopGuard Decoy Trap">
  &nbsp;&nbsp;
  <img src="docs/screenshots/forensics_log.jpg" width="31%" alt="SnoopGuard Forensic Gallery">
</p>

<p align="center">
  <sub>
    <b>Live Shield</b> · <b>Decoy Trap</b> · <b>Forensic Gallery</b>
  </sub>
</p>

🛡️ Core Features

Live Shield

A real-time protection dashboard designed to help identify potential unauthorized observers.

Highlights

Real-time threat status

Owner biometric profile

Facial landmark and gaze analysis

Configurable detection sensitivity

Haptic, visual, and optional audio alerts

Local processing architecture

Built-in demonstration mode

Decoy Trap

A lockscreen-style protection mode intended for unattended-device scenarios.

Highlights

Lock interface with dynamically displayed time and date

Owner-only exit mechanism

Intrusion-triggered camera capture

Local incident storage

Timestamped evidence

Anti-tampering-oriented interaction flow

Forensic Gallery

A local incident history for reviewing detected events.

Highlights

Intruder snapshots

Incident timestamps

Detection confidence information

Incident classification

Individual incident deletion

Complete evidence purge

Optional evidence export workflow

🔐 Privacy by Design

Privacy is a core architectural principle of SnoopGuard.

The project is designed around local-first processing so that sensitive biometric and incident information does not need to be uploaded to a remote service.

Local Processing

SnoopGuard is designed to keep security-related processing on the device, including:

Facial analysis

Gaze-related processing

Threat assessment

Image processing

Incident storage

No Cloud Dependency

The intended architecture does not require a cloud backend for the core protection workflow.

This means the application can be evaluated without relying on an external security server or analytics platform.

User Control

The application includes privacy-oriented controls for:

Camera permission management

Biometric profile management

Incident review

Individual evidence deletion

Complete local data deletion

Demonstration mode without camera access

Important: Privacy and security claims should always be validated against the actual source code, Android permissions, dependencies, release configuration, and device behavior.

⚖️ Responsible Use

SnoopGuard is intended for personal device protection and privacy awareness.

Appropriate use includes:

Protecting your own device from shoulder surfing

Detecting potential unauthorized interaction with your own device

Testing mobile privacy and security concepts

Learning about on-device computer vision

Demonstrating privacy-preserving security architecture

SnoopGuard should not be used for:

Covert surveillance of other people

Photographing people without appropriate consent where required by law

Monitoring devices that you do not own or have permission to manage

Circumventing legitimate security, parental-control, or enterprise-management systems

Any activity that violates applicable privacy or surveillance laws

Camera-based features should be used responsibly and in accordance with local law.

🧠 How It Works

1. Owner Setup

The user can establish an owner profile during application setup.

2. Active Protection

When protection is enabled, the application can analyze the camera view using local processing to identify potential observation events.

3. Threat Detection

A suspected event can trigger configured feedback such as:

Visual notification

Device vibration

Optional audio alert

Incident logging

4. Incident Recording

When an enabled protection mechanism records an incident, the relevant information is stored locally for later review.

5. Forensic Review

The Forensic Gallery provides a chronological view of recorded events and their available metadata.

🏗️ Application Architecture

SnoopGuard follows a modern Android architecture built around separation of concerns and reactive UI state.

┌──────────────────────────────────────────────────────────┐
│                       SNOOPGUARD                         │
├──────────────────────────────────────────────────────────┤
│                                                          │
│  🛡️ LIVE SHIELD     🎭 DECOY TRAP     🧾 FORENSICS      │
│                                                          │
├──────────────────────────────────────────────────────────┤
│                    PRESENTATION                          │
│               Jetpack Compose + M3                       │
├──────────────────────────────────────────────────────────┤
│                     VIEW MODELS                          │
│              StateFlow / UI State                         │
├──────────────────────────────────────────────────────────┤
│                    DOMAIN LAYER                          │
│             Security & Detection Logic                    │
├──────────────────────────────────────────────────────────┤
│                      DATA LAYER                          │
│             Room / Local Storage / Models                 │
├──────────────────────────────────────────────────────────┤
│                 DEVICE SERVICES                           │
│        CameraX · Android APIs · Local ML                 │
└──────────────────────────────────────────────────────────┘

Main Application Areas

Shield Dashboard

Master protection toggle

Threat status

Owner profile status

Detection controls

Problem-solver / educational scenarios

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

Deletion and purge controls

Settings

Sensitivity configuration

Alert preferences

Biometric profile management

Camera permission controls

Privacy controls

Application reset

⚡ Technical Capabilities

Capability

Description

Live Shield

Real-time camera-based observation monitoring

Gaze Analysis

Facial landmark and gaze-related threat analysis

Decoy Trap

Lock-style interface for unattended-device protection

Biometric Profile

Local owner-profile calibration

Forensic Gallery

Timestamped incident history

Sensitivity Control

Adjustable protection sensitivity

Demo Mode

Hardware-free feature demonstration

Local Storage

Device-resident incident persistence

Privacy Controls

Profile, permission, and data management

🧰 Technology Stack

Technology

Role

Kotlin 2.0

Primary development language

Jetpack Compose

Modern declarative UI

Material 3

UI design system

MVVM

Presentation architecture

Clean Architecture

Separation of application responsibilities

StateFlow

Reactive state management

Kotlin Coroutines

Asynchronous operations

Room

Local database abstraction

KSP

Compile-time code generation

CameraX

Camera lifecycle and hardware abstraction

TensorFlow Lite

On-device ML inference

NNAPI

Hardware-accelerated ML where supported

📱 Requirements

Minimum

Android: 8.0 / API 26+

JDK: 17

Gradle: 8.x+

Compatible front-facing camera for camera-dependent features

Recommended Development Environment

Android Studio Iguana, Jellyfish, Ladybug, or newer

Physical Android device for camera and hardware testing

Android Emulator for UI and non-camera testing

Some device-specific behavior may vary depending on Android version, camera hardware, manufacturer restrictions, and available hardware acceleration.

📥 Download

A pre-built APK is available from the project's GitHub release page.

Current release referenced by this project: v.0.0.1

👉 Download SnoopGuard APK

👉 View GitHub Release

Installation

Download the APK from the release page.

Transfer it to your Android device if necessary.

Install the APK.

Review the requested permissions.

Enable only the features you want to use.

Use Demonstration Mode first if you want to test the interface without camera access.

Android may require you to allow installation from the relevant source before installing an APK obtained outside Google Play.

🔨 Build From Source

1. Clone the repository

git clone https://github.com/EthYusuf/secretOS.git
cd secretOS

2. Clean the project

./gradlew clean

3. Build a debug APK

./gradlew assembleDebug

4. Run unit tests

./gradlew :app:testDebugUnitTest

5. Run instrumented tests

./gradlew :app:connectedDebugAndroidTest

6. Build a release APK

./gradlew assembleRelease

The release build requires a properly configured Android signing setup.

🧪 Testing Strategy

SnoopGuard can be tested at multiple levels:

UI & Functional Testing

Validate:

Navigation

Protection state changes

Settings

Permission flows

Incident display

Data deletion

Demonstration mode

Unit Testing

./gradlew :app:testDebugUnitTest

Instrumented Testing

./gradlew :app:connectedDebugAndroidTest

Physical Device Testing

Camera-dependent functionality should be tested on real Android hardware because camera APIs, performance, background restrictions, and manufacturer behavior can differ significantly between devices.

🔒 Security Considerations

SnoopGuard handles potentially sensitive information. Security should therefore be treated as an ongoing engineering process.

Areas that should be reviewed before production distribution include:

Android permission declarations

Camera lifecycle behavior

Local database encryption

Android Keystore usage

Backup configuration

APK signing

Dependency vulnerabilities

Debug logging

Export functionality

Screenshot / screen-recording behavior

Data deletion guarantees

Security Disclosure

If you discover a security vulnerability, please avoid publicly posting sensitive exploit details before the issue can be responsibly assessed.

For general bugs and feature requests, use GitHub Issues.

🗺️ Roadmap

Planned areas of development include:

Multi-user trusted profiles

Improved environmental adaptation

More robust observation detection

Accessibility improvements

Expanded device compatibility

Additional privacy controls

Improved forensic metadata

More comprehensive automated testing

Optional encrypted synchronization

Optional encrypted backup workflows

Roadmap items are subject to change as the project evolves.

🤝 Contributing

Contributions, ideas, bug reports, and security feedback are welcome.

Suggested workflow

Fork the repository.

Create a feature branch.

Make your changes.

Test the application.

Commit your changes with a clear message.

Open a pull request.

Please keep security-sensitive changes documented and avoid introducing unnecessary data collection or network dependencies.

🐛 Issues & Feedback

For bugs, feature requests, and general project discussions:

👉 Open a GitHub Issue

When reporting a bug, include:

Android version

Device model

SnoopGuard version

Steps to reproduce

Expected behavior

Actual behavior

Relevant logs or screenshots, if safe to share

Do not include private photographs, biometric information, passwords, tokens, or other sensitive data in public issues.

📄 License

SnoopGuard is distributed under the MIT License.

See the LICENSE file for the complete license text.

⚠️ Disclaimer

SnoopGuard is a security and privacy project intended for educational and personal protection purposes.

The application should be treated as an additional privacy layer, not as a replacement for Android's native security mechanisms, strong device authentication, encryption, or other established security controls.

Detection accuracy can vary based on lighting, camera quality, device hardware, viewing angle, environmental conditions, and software configuration.

Privacy, recording, biometric-data, and surveillance laws vary by jurisdiction. Users are responsible for using the application lawfully and obtaining any consent required by applicable law.

<p align="center">
  <strong>SnoopGuard</strong><br>
  <em>Your screen is for your eyes only.</em>
</p>

<p align="center">
  Privacy Protection · On-Device Processing · Responsible Security
</p>

<p align="center">
  Built with ❤️ for privacy-conscious users.
</p>
