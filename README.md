<div align="center">

# 🛡️ SnoopGuard

### Ekranınız Yalnızca Sizin Gözleriniz İçin

*On-cihaz gizlilik koruması. Omuz sörfü ve cihaz gözetlemeye karşı akıllı koruma.*

---

**[🚀 Hızlı Başlangıç](#-hızlı-başlangıç)** • **[📖 Özellikler](#-temel-özellikler)** • **[🔧 Teknik](#-teknoloji-yığını)** • **[🤝 Katkıda Bulunun](#-katkıda-bulunun)**

![SnoopGuard Banner](docs/screenshots/problem_guide_banner.jpg)

</div>

---

## 🎯 SnoopGuard Nedir?

SnoopGuard, modern akıllı telefonların hassas bilgilerini koruyan, **gizlilik-odaklı** bir Android güvenlik uygulamasıdır.

📱 Özel sohbetler • 🔐 Kimlik doğrulama kodları • 📸 Fotoğraflar • 💰 Finansal uygulamalar • 🔑 Kişisel hesaplar

SnoopGuard iki yaygın tehdide karşı koruma sağlar:

| 👁️ Omuz Sörfü | 📱 Gözeticiliği |
|:---|:---|
| Birisi cihazınızı kullanırken ekranınızı görüyor | Birisi cihazınızdan ayrıldığınız sırada müdahale ediyor |

---

## 🚀 Hızlı Başlangıç

### ⬇️ İndir ve Kur

```bash
# APK dosyasını indir
# GitHub Releases → snoopguard v.0.0.1

# 1. APK dosyasını Android cihazına aktar
# 2. Uygulamayı yükle
# 3. İzinleri gözden geçir
# 4. Koruma özelliklerini etkinleştir
```

### 📥 Kaynak Kodundan Derle

```bash
# Repository'yi klonla
git clone https://github.com/EthYusuf/secretOS.git
cd secretOS

# Projeyi temizle
./gradlew clean

# Debug APK oluştur
./gradlew assembleDebug

# Testleri çalıştır
./gradlew :app:testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest

# Release APK oluştur
./gradlew assembleRelease
```

---

## 🛡️ Temel Özellikler

### 🎨 Üç Güçlü Modül

<div align="center">

| 🛡️ Live Shield | 🎭 Decoy Trap | 🧾 Forensic Gallery |
|:---:|:---:|:---:|
| **Gerçek Zamanlı Koruma** | **Kilit Ekranı Koruması** | **İncele ve Analiz Et** |
| Kamerayla gözetleme tespiti | Gözeticilere karşı tuzak | Olayların tarihçesi |
| Yüz tanıma analizi | Otomatik fotoğraf çekme | Kanıt depolama |
| Bakış yönü analizi | Sahip-sadece çıkış | Özgüvenlik düzeyi |

</div>

### ✨ Detaylı Özellik Listesi

#### 🛡️ Live Shield — Gerçek Zamanlı Gözlemci Tespiti

- ✅ Cihaz kamerası kullanarak gerçek zamanlı tehdit analizi
- ✅ Sahip biyometrik profili oluşturma
- ✅ Yüz landmark ve bakış yönü analizi
- ✅ Yapılandırılabilir duyarlılık seviyeleri
- ✅ Görsel uyarı ve titreşim geribildirim
- ✅ İsteğe bağlı ses uyarıları
- ✅ Tüm işlem cihazda kalır (bulut yok!)
- ✅ Gösteri modu (kamera olmadan keşfet)

#### 🎭 Decoy Trap — Atıl Cihaz Koruması

- ✅ Kilit ekranı tarzında koruma arayüzü
- ✅ Girişim anında otomatik fotoğraf çekme
- ✅ Sahip-sadece kimlik doğrulama ile çıkış
- ✅ Yerel olarak depolanan kanıt
- ✅ Zaman damgalı olay kaydı
- ✅ Zamanı ve tarihi dinamik gösterimi
- ✅ Manipülasyona karşı tasarlanmış akış

#### 🧾 Forensic Gallery — Yerel Olay Geçmişi

- ✅ Girişimcilerin anlık fotoğrafları
- ✅ Tam tarih ve saat bilgisi
- ✅ Tespit güvenlik yüzdesi
- ✅ Olayların sınıflandırılması
- ✅ Tek tek kanıt silme
- ✅ Tam veri temizliği seçeneği
- ✅ Kanıt dışa aktarma özellikleri

---

## 🔐 Gizlilik Tasarım İlkeleri

SnoopGuard, **gizlilik ilk** mimarisiyle inşa edilmiştir.

### 📡 Bulut Yok, Veri Güvenliği Var

| ✅ Cihazda İşleme | ❌ Buluta Gönderilmez |
|:---|:---|
| Yüz analizi | Bulut sunucuları |
| Bakış yönü işlemi | Uzak biyometrik işlem |
| Tehdit değerlendirmesi | Üçüncü taraf analitik |
| Görüntü işleme | Zorunlu hesap altyapısı |
| Olay depolama | Remote güvenlik arka ucu |
| Tespit metadata |  |

### 🎛️ Kullanıcı Kontrolü

- 📷 Kamera izinleri
- 🔑 Biyometrik profil yönetimi
- 🗑️ Kanıt silme seçenekleri
- 🔄 Tam yerel veri temizliği
- 📊 Gösteri modu

---

## 🧠 Nasıl Çalışır?

### 🔄 Tehdit Tespiti Akışı

```
Kamera Girişi
     ↓
   ┌─────────────────┐
   │  Yerel Görüntü  │
   │  Analizi        │
   └────────┬────────┘
            ↓
   ┌─────────────────┐
   │  Tehdit         │
   │  Değerlendirmesi│
   └────────┬────────┘
            ├──────────→ Tehdit Yok
            ↓
   ┌─────────────────┐
   │  Koruma         │
   │  Yanıtı         │
   └────────┬────────┘
            ├──→ Görsel Uyarı
            ├──→ Titreşim
            ├──→ Ses Uyarısı
            └──→ Olay Kaydı
                    ↓
            ┌────────────────┐
            │  Yerel Kanıt   │
            │  Depolama      │
            └────────────────┘
```

### 📋 Koruma İş Akışı

1. **Sahip Kurulumu** → Profil oluştur
2. **Korumayı Etkinleştir** → İstediğin özellikleri aç
3. **Yerel Analiz** → Cihazda akıllı işlem
4. **Tehdit Tespit** → Otomat yanıt (uyarı, titreşim, kaydı)
5. **Kanıt Depolama** → Tüm veriler cihazda kalır
6. **Adli İnceleme** → Forensic Gallery'de gözden geçir

---

## 🏗️ Uygulama Mimarisi

```
┌─────────────────────────────────────┐
│       UI / SUNUM KATMANI            │
│    Jetpack Compose + Material 3     │
├─────────────────────────────────────┤
│          VİEW MODELLERİ             │
│      StateFlow + UI Durum           │
├─────────────────────────────────────┤
│         DOMAIN (İŞ LOGICI)          │
│   Güvenlik • Tespit • Kullanım Durumları   │
├─────────────────────────────────────┤
│          VERİ KATMANI               │
│   Room • Yerel Depolama • Modeller  │
├─────────────────────────────────────┤
│      CİHAZ SERVİSLERİ               │
│  CameraX • Android APIs • Yerel ML  │
└─────────────────────────────────────┘
```

---

## ⚡ Teknik Özellikler

| 🎯 Özellik | 📝 Açıklama |
|:---|:---|
| **Live Shield** | Kamera tabanlı gözetleme izleme |
| **Gaze Analizi** | Yüz landmark ve bakış yönü analizi |
| **Decoy Trap** | Atıl cihaz kilit ekranı koruması |
| **Biyometrik Profil** | Sahip profilini yerel kalibrasyon |
| **Forensic Gallery** | Zaman damgalı olay geçmişi |
| **Duyarlılık Kontrolü** | Ayarlanabilir koruma seviyeleri |
| **Gösteri Modu** | Kamera olmadan keşfet |
| **Yerel Depolama** | Tüm kanıtlar cihazda kalır |
| **Gizlilik Kontrolleri** | Profil, izin ve veri yönetimi |

---

## 🧰 Teknoloji Yığını

```
🏛️ Mimari          Kotlin 2.0, Clean Architecture, MVVM
🎨 Arayüz          Jetpack Compose, Material Design 3
📱 Platform        Android 8.0+ (API 26+)
🔄 Durum Yönetimi  StateFlow, Kotlin Coroutines
💾 Veri            Room Database, Local Storage
📷 Kamera          CameraX (Hardware Abstraction)
🤖 Makine Öğrenmesi TensorFlow Lite, NNAPI
🔧 Derleme         Gradle 8.x+, JDK 17+
```

---

## 📱 Sistem Gereksinimleri

### Minimum Gereksinimler

| 📋 Gereksinim | 📌 Versiyon |
|:---|:---|
| **Android** | 8.0 veya daha yeni (API 26+) |
| **JDK** | 17 veya daha yeni |
| **Gradle** | 8.x veya daha yeni |
| **Kamera** | Ön kamera (bazı özellikler için) |

### Önerilen Geliştirme Ortamı

- **Android Studio**: Iguana, Jellyfish, Ladybug veya daha yeni
- **Cihaz**: Fiziksel Android cihaz (kamera özelikleri için)
- **Emülatör**: UI ve kamera olmayan testler için

---

## 🧪 Test Etme

### UI & Fonksiyonel Testler
```bash
# Gösteri modunu dene (kamera almadan!)
```

### Birim Testleri
```bash
./gradlew :app:testDebugUnitTest
```

### Entegre Testler (Emülatör/Cihaz)
```bash
./gradlew :app:connectedDebugAndroidTest
```

### Fiziksel Cihaz Testi
- Gerçek kamera işlevselliği için fiziksel cihaz kullan
- Farklı Android sürümlerinde test et
- Üreticiye özgü davranışları değerlendir

---

## 🔒 Güvenlik & Sorumluluk

### ⚠️ Sorumlu Kullanım

SnoopGuard **kişisel cihaz koruması ve gizlilik eğitimi** için tasarlanmıştır.

#### ✅ Uygun Kullanımlar
- 🔒 Kendi cihazınızı omuz sörfüne karşı koru
- 🚨 Yetkisiz cihaz erişimini tespit et
- 🧪 Mobil gizlilik kavramlarını test et
- 📚 On-cihaz bilgisayar vizyonu öğren
- 🎓 Gizlilik-koruyucu mimarı göster

#### ❌ Yasadışı Kullanımlar
- 🚫 Diğer insanları gizlice izlemek
- 🚫 İzin olmadan kayıt yapmak
- 🚫 Sahibi olmadığınız cihazları izlemek
- 🚫 Yasal güvenlik sistemlerini aşmak

### 🔍 Güvenlik Açığı Bildir

Güvenlik sorunu keşfettiysen, **halka açık olmayan** şekilde bildir:

📧 GitHub Issues (genel hatalar ve özellik istekleri için)

---

## 📊 Uygulamanın Ana Alanları

| 🎯 Bölüm | 📝 İçerik |
|:---|:---|
| **Shield Dashboard** | Ana koruma, durum, istatistikler |
| **Decoy Trap** | Kilit ekranı, müdahale tetiklemesi |
| **Forensics** | Olaylar, fotoğraflar, analiz |
| **Settings** | Duyarlılık, sesler, gizlilik |

---

## 🗺️ Yol Haritası

### 🔬 Tespit Geliştirmesi
- [ ] Gözlem tespitini geliştir
- [ ] Çevre uyarlamasını iyileştir
- [ ] Cihaz uyumluluğunu genişlet

### 🔐 Gizlilik & Güvenlik
- [ ] Çok kullanıcılı güvenilir profiller
- [ ] Ek gizlilik kontrolleri
- [ ] İsteğe bağlı şifreli yedekleme

### 🧰 İngineering
- [ ] Otomatik test kapsamını genişlet
- [ ] Erişilebilirliği iyileştir
- [ ] Belgelendirmeyi geliştir

---

## 🤝 Katkıda Bulunun

Katkılar, fikirler ve geri bildirim **çok hoşlanmaktadır**!

### 📝 Önerilen İş Akışı

```
Fork
  ↓
Özellik Dalı Oluştur
  ↓
Değişiklikleri Uygula
  ↓
Testleri Çalıştır
  ↓
Commit Et
  ↓
Pull Request Aç
```

### 📌 Dikkat Edilecekler

- Güvenlik değişikliklerini belgele
- Gereksiz veri toplama ekleme
- Ağ bağımlılıkları minimize et

---

## 🐛 Sorun Bildir

Bug, özellik isteği veya tartışma için **GitHub Issues** kullan.

### 📋 Bug Raporu Şablonu

```
Android Sürümü: [ör. Android 14]
Cihaz Modeli: [ör. Samsung Galaxy S24]
SnoopGuard Versiyonu: v.0.0.1
Adımlar: [1. ... 2. ... 3. ...]
Beklenen Davranış: [ne olması gerekiyordu]
Gerçek Davranış: [ne oldu]
Ekler: [screenshot/log - emin olursan]
```

### ⚠️ Sakla Sakın

Halka açık sorunlarda **asla** sakla:
- 📸 Özel fotoğraflar
- 🔐 Biometrik veriler
- 🔑 Şifreler / Token'lar
- 👤 Kişisel kimlik bilgileri

---

## 📄 Lisans

SnoopGuard **MIT Lisansı** altında dağıtılır.

[Lisans Metni](LICENSE) için repo'ya göz at.

---

## ⚠️ Feragatname

SnoopGuard eğitim ve kişisel koruma amaçlı bir güvenlik ve gizlilik projesidir.

### Bu, Yerine Geçmez:
- ❌ Android's yerel güvenlik mekanizmaları
- ❌ Güçlü cihaz kimlik doğrulaması
- ❌ Şifreleme protokolleri
- ❌ Kuruluş güvenliği

### Doğruluk Değişir:
- 🌤️ Aydınlatma koşulları
- 📷 Kamera kalitesi
- 📱 Cihaz donanımı
- 👁️ Bakış açısı
- 🎛️ Yazılım yapılandırması

### 📜 Yasal Uyarı:
Gizlilik, kayıt ve gözetleme yasaları **yargı yetkisine göre farklıdır**. Uygulamayı yasal şekilde ve gerekli tüm rızaları alarak kullan.

---

<div align="center">

## 🛡️ SnoopGuard

### Ekranınız Yalnızca Sizin Gözleriniz İçin

<br>

**On-Cihaz Gizlilik** • **Akıllı Tespit** • **Sorumlu Güvenlik**

<br>

💜 Gizlilik-Bilinci Kullanıcılar İçin Yapılmıştır

<br>

[![GitHub](https://img.shields.io/badge/GitHub-EthYusuf-111111?style=for-the-badge&logo=github&logoColor=white)](https://github.com/EthYusuf/snoopguard)

</div>