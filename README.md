# 🛡️ GÖZCÜ (SnoopGuard)
> **Cihaz İçi Yerel Yapay Zeka Destekli Ekran Gizliliği ve Omuz Dikizleme (Shoulder Surfing) Kalkanı**

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-purple.svg?style=flat&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-green.svg?style=flat&logo=android)
![Privacy](https://img.shields.io/badge/Privacy-100%25%20On--Device-blue.svg?style=flat)
![Database](https://img.shields.io/badge/Storage-Room%20SQLite%20Offline-orange.svg?style=flat)
![Zero Cloud](https://img.shields.io/badge/Cloud%20Upload-ZERO-red.svg?style=flat)

---

## 📌 İnsanların Hangi Sorununu Çözüyor? (Problem & Çözüm)

Günümüzde akıllı telefonlar sadece iletişim aracı değil; **bankacılık işlemlerimizin, özel mesajlarımızın, iş e-postalarımızın ve kişisel fotoğraflarımızın** merkezidir. Ancak ekranlar büyüdükçe ve hayatımız kalabalıklaştıkça gizliliğimiz sürekli tehdit altındadır.

### 1. Omuz Üzerinden Gözetleme (Shoulder Surfing)
* **Gerçek Hayat Senaryosu:** Metroda, otobüste, uçakta, asansörde veya kafede otururken banka hesabınıza girdiğinizde, şifre yazdığınızda ya da sevgilinizle/arkadaşınızla özel mesajlaşırken hemen yanınızdaki veya arkanızdaki bir yabancının gözlerini ekrana dikmesi.
* **Yaşanan Problem:** İnsanların %70'inden fazlası toplu taşımada ekranını eliyle siper etmek zorunda kalır veya dikizleyen kişiyi fark edemez.
* **Gözcü Çözümü:** Ön kameranın geniş açısını kullanan yerel yapay zeka algoritması, ekrana yönelen yabancı yüzleri ve bakış açılarını anlık takip eder. Sahibinden farklı bir bakış ekrana odaklandığında anında sessiz titreşimle uyarır ve dikizleyen kişinin fotoğrafını kanıt olarak kaydeder.

---

### 2. Masada Bırakılan Telefonun Kurcalanması (Unattended Snooping)
* **Gerçek Hayat Senaryosu:** Ofiste toplantıya gittiğinizde, sınıfta sıranızdan kalktığınızda veya evde arkadaş ortamında masada bıraktığınız telefonun meraklı biri tarafından açılmaya çalışılması.
* **Yaşanan Problem:** Bildirim önizlemelerinin okunması, kilidin zorlanması veya telefon sahibinin arkasından gizlice mesajlara bakılması.
* **Gözcü Çözümü — Tuzak Kilit Ekranı (Decoy Trap):** Telefon sahte ama tamamen gerçekçi bir kilit ekranına geçer (saat, tarih ve "Kilidi açmak için kaydırın" animasyonu). Meraklı kişi ekrana dokunduğu anda ön kamera flaş patlatmadan, ses çıkarmadan saniyesinde fotoğrafını çeker ve günlüğe saat/dakika/saniye olarak işler.

---

### 3. Bulut Güvensizliği ve Biyometrik Veri Sızıntısı Korkusu
* **Gerçek Hayat Senaryosu:** Piyasada güvenlik vaat eden pek çok uygulama, yüz fotoğraflarını sunuculara göndermekte, veri sızıntılarına yol açmakta ve kullanıcıların gizliliğini ihlal etmektedir.
* **Gözcü Çözümü:** **%100 Yerel (On-Device Local AI).** Gözcü'de hiçbir fotoğraf, hiçbir yüz koordinatı veya hiçbir log kaydı internete çıkmaz. Cihazın kendi dahili Room SQLite veritabanında şifreli olarak barındırılır.

---

### 4. Hukuki ve Etik Güvenilirlik
* **Problem:** Kullanıcıların izinsiz çekim yapılması veya habersiz kayıt tutulması endişesi.
* **Gözcü Çözümü:** Uygulama açılışında zorunlu yasal sorumluluk reddi (Disclaimer & Consent) protokolü bulunur. Kamera izni tamamen opsiyoneldir; izin verilmediğinde dahi kullanıcı simülasyon ve test modlarıyla uygulamayı deneyimleyebilir.

---

## 📱 Ekran Görünümleri ve Mimari Akış

```
+-------------------------------------------------------------------------+
|                              GÖZCÜ (SnoopGuard)                         |
|                                                                         |
|  [🛡️ Güvenlik Kalkanı]              [📸 Olay Günlüğü]        [⚙️ Ayarlar]|
+-------------------------------------------------------------------------+
|                                                                         |
|  1. KALKAN EKRANI (Home Screen)                                          |
|  +-------------------------------------------------------------------+  |
|  |  [🔵 Master Switch: Kalkan Aktif / Beklemede]                      |  |
|  |  [💥 Nabız Atan Kalkan Rozeti: Güvenli (Yeşil) / Tehdit (Kırmızı)]  |  |
|  |  [👤 Sahip Biyometrisi: Yüz Tanıtıldı / Tanıtılmadı]               |  |
|  |  [📖 Gözcü Hangi Sorunu Çözer? (Görsel Problem-Çözüm Rehberi)]     |  |
|  |  [⚡ Koruma Modları: Canlı Kalkan & Sahte Tuzak Ekranı]            |  |
|  |  [🧪 Güvenli Test Simülatörü (Kamera Olmadan Test İmkânı)]         |  |
|  +-------------------------------------------------------------------+  |
|                                                                         |
|  2. TUZAK KİLİT EKRANI (Decoy Trap)                                     |
|  +-------------------------------------------------------------------+  |
|  |  Gerçekçi Saat: 14:45 | Cuma, 7 Eylül                             |  |
|  |  Ekrana meraklı parmak dokunduğu anda:                            |  |
|  |  -> Sessiz Flaşsız Ön Kamera Yakalama                              |  |
|  |  -> Korumalı PIN ile Sahibinin Çıkışı                             |  |
|  +-------------------------------------------------------------------+  |
|                                                                         |
|  3. OLAY KANIT GALERİSİ (Logs Gallery & Forensics)                     |
|  +-------------------------------------------------------------------+  |
|  |  Yakalanan Gözetleyiciler Listesi:                                 |  |
|  |  - Tarih & Saat Damgası                                           |  |
|  |  - Tehdit Skoru (%92 Şüpheli Bakış)                               |  |
|  |  - Tetikleme Nedeni (Omuz Dikizleme / Tuzak Kilit Kurcalama)      |  |
|  |  - Yüksek Çözünürlüklü Ön Kamera Fotoğrafı                         |  |
|  |  - Tek Tıkla Silme veya Tüm Verileri Temizleme                    |  |
|  +-------------------------------------------------------------------+  |
+-------------------------------------------------------------------------+
```

---

## ✨ Temel Özellikler

| Özellik | Açıklama |
| :--- | :--- |
| **Gerçek Zamanlı Yüz Kalkanı** | Ön kamera vizörü üzerinden ekrana bakan kişi sahibinden farklı mı anlık kıyaslar. |
| **Omuz Dikizleme Uyarısı** | Yanınızdan ekrana bakan bir yabancı saptandığında ekranda kırmızı uyarı başlığı ve titreşim üretir. |
| **Tuzak Kilit Ekranı** | Masada bırakıldığında sahte telefon kilit arayüzü sunar; dokunan kişiyi sessizce fotoğraflar. |
| **Sahip Biyometrik Kaydı** | Ön kamera ile kendi yüzünüzü sisteme tanıtabilir, referans alarak hatalı alarmları önleyebilirsiniz. |
| **Çözülen Sorunlar Rehberi** | Metro, ofis ve kamusal alan tehditlerini ekran resimli görsel slaytlarla anlatan dahili rehber. |
| **Sıfır Bulut Bağımlılığı** | Tüm veriler cihaz içinde Room SQLite ile saklanır. Asla internete veri göndermez. |
| **Hassasiyet & Titreşim Ayarı** | Algılama eşiği 0.1 - 1.0 arasında özelleştirilebilir, titreşim açılıp kapatılabilir. |

---

## 🛠️ Teknoloji Yığını

* **Programlama Dili:** Kotlin 2.0 (Modern DSL & Type Safety)
* **Kullanıcı Arayüzü:** Jetpack Compose (Material Design 3 - Dark Cyberpunk & Emerald Safe Paleti)
* **Mimari:** Clean Architecture & MVVM (StateFlow, MutableStateFlow, Coroutines)
* **Veritabanı:** Android Jetpack Room SQLite + KSP (Kotlin Symbol Processing)
* **Kamera Yönetimi:** Android CameraX / Accompanist Permissions Entegrasyonu
* **Görsel Tasarım:** Özel üretilmiş yüksek çözünürlüklü vektör ve konsept güvenlik grafikleri (`img_privacy_hero`, `img_shoulder_surfing`, `snoop_guard_icon`)

---

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
* Android 8.0 (API Level 26) veya daha yenisi
* Target SDK: 34
* Gradle 8.x + JDK 17

### Derleme (Build)
```bash
# Debug APK derleme
gradle assembleDebug

# Birim ve Robolectric testlerini çalıştırma
gradle :app:testDebugUnitTest
```

---

## 🔒 Gizlilik, Güvenlik ve Yasal Uyarı

1. **Yasal Amaç:** Bu uygulama kişisel cihaz güvenliğini ve omuz üzerinden ekran gizliliğini korumak amacıyla geliştirilmiştir.
2. **Kişisel Verilerin Korunması:** Uygulama tarafından alınan fotoğraflar yalnızca yerel cihaz belleğinde saklanır. Sunucu bağlantısı veya bulut yedekleme yoktur.
3. **Kullanıcı Onayı:** Uygulamayı ilk kez başlatan kullanıcıların sorumluluk ve gizlilik koşullarını açık rıza ile onaylaması zorunludur. Kullanıcı dilediği an ayarlar menüsünden onayını geri çekebilir ve tüm biyometrik verileri sıfırlayabilir.

---

<p align="center">
  <b>Gözcü (SnoopGuard)</b> • Özel Hayatınız Yalnızca Sizin Gözleriniz İçindir.
</p>
