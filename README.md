# 📚 QuantumReads — Aplikasi Manajemen Perpustakaan Android

Aplikasi manajemen perpustakaan berbasis **Android** yang memudahkan pengelolaan buku, anggota, dan transaksi peminjaman secara lokal. Dibangun menggunakan **Kotlin** dengan penyimpanan data menggunakan **SQLite** native.

---

## ✨ Fitur Utama

### 👤 Member
- Registrasi & login akun dengan validasi duplikat username
- Edit profil (nama, email, telepon, alamat)
- Lupa password — reset via verifikasi username & email
- Lihat katalog buku lengkap beserta detail (judul, penulis, jenis, genre, cover)
- Pinjam buku dengan pemilihan tanggal kembali via DatePicker
- Lihat riwayat peminjaman aktif
- Kembalikan buku — status otomatis berubah jadi `dikembalikan` atau `telat dikembalikan`

### 🔐 Admin
- Login admin dengan kredensial khusus
- CRUD Buku — tambah, edit, hapus buku beserta foto cover
- Lihat & kelola seluruh data member
- Hapus member
- Lihat semua transaksi peminjaman dari semua member

---

## 🗄️ Database Schema (SQLite)

```
member
├── username    (PK, text)
├── name        (text)
├── gmail       (text)
├── telepon     (text)
├── alamat      (text)
└── password    (text)

admin
├── username    (PK, text)
└── password    (text)

buku
├── id_buku     (PK, integer)
├── image_buku  (blob)
├── judul       (text)
├── penulis     (text)
├── jenis       (text)
└── genre       (text)

kartu_pinjam
├── id_kartu    (PK, autoincrement)
├── id_buku     (FK → buku)
├── username    (FK → member)
├── status      (text) — "di pinjam" | "dikembalikan" | "telat dikembalikan"
├── tgl_pinjam  (date)
└── tgl_kembali (date)
```

---

## 🛠️ Tech Stack

| Komponen | Teknologi |
|---|---|
| **Bahasa** | Kotlin |
| **Platform** | Android |
| **Database** | SQLite (SQLiteOpenHelper) |
| **UI Components** | RecyclerView, MaterialAlertDialog, DatePickerDialog |
| **Image Handling** | Bitmap / ByteArray (BLOB di SQLite) |
| **Networking** | Volley 1.2.1 |
| **Build System** | Gradle (Kotlin DSL) |

---

## 📁 Struktur Kode

```
app/src/main/java/com/example/projectuas_perpus/
│
├── DBHelper.kt                    ← SQLite database helper (4 tabel)
│
├── MainActivity.kt                ← Login Member
├── loginAdmin.kt                  ← Login Admin
├── Register.kt                    ← Registrasi member baru
├── forgetPass.kt                  ← Reset password
│
├── memberPage.kt                  ← Dashboard member
├── adminPage.kt                   ← Dashboard admin
│
├── displayBukuMember.kt           ← Katalog buku (member)
├── displayBukuAdmin.kt            ← Manajemen buku (admin)
├── displayMemberAdmin.kt          ← Manajemen member (admin)
├── displayBookingMember.kt        ← Riwayat pinjam (member)
├── displayBookingAdmin.kt         ← Semua transaksi (admin)
│
├── InsertBuku.kt                  ← Tambah / edit buku
├── bookings.kt                    ← Form peminjaman buku
├── infoBukuMember.kt              ← Detail buku (dari katalog)
├── infoBukuBookingMember.kt       ← Detail buku (dari riwayat pinjam)
├── editProfil.kt                  ← Edit profil member
│
├── ModelBuku.kt                   ← Data class buku
├── ModelBukuMember.kt             ← Data class buku (view member)
├── ModelMember.kt                 ← Data class member
├── ModelBookingAdmin.kt           ← Data class transaksi (admin)
├── ModelBookingMember.kt          ← Data class transaksi (member)
│
├── MyAdapterBookAdmin.kt          ← RecyclerView adapter buku (admin)
├── MyAdapterBookMember.kt         ← RecyclerView adapter buku (member)
├── MyAdapterMemberAdmin.kt        ← RecyclerView adapter member
├── MyAdapterBookingAdmin.kt       ← RecyclerView adapter transaksi (admin)
└── MyAdapterBookingMember.kt      ← RecyclerView adapter transaksi (member)
```

---

## ⚙️ Cara Menjalankan

### Prerequisites
- **Android Studio** Hedgehog (2023.1.1) atau lebih baru
- **Android SDK** terinstall
- **JDK 8** atau lebih baru

### Langkah
1. Clone repositori:
```bash
git clone https://github.com/KuzuChis/quantumreads-android.git
```

2. Buka **Android Studio** → **File → Open** → pilih folder project

3. Tunggu Gradle sync selesai (pastikan koneksi internet aktif saat pertama kali)

4. Jalankan di emulator atau device fisik:
   - Klik **Run ▶** atau tekan `Shift + F10`

---

## 📱 Requirements

| Spesifikasi | Nilai |
|---|---|
| Min SDK | API 26 (Android 8.0 Oreo) |
| Target SDK | API 33 (Android 13) |
| Compile SDK | API 34 (Android 14) |

---

## 📦 Dependencies

```kotlin
implementation("androidx.core:core-ktx:1.9.0")
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.10.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("com.android.volley:volley:1.2.1")
```

---

## ⚠️ Catatan

> Kredensial admin pada aplikasi ini bersifat statis dan hanya untuk keperluan demo akademis. Pada implementasi production, autentikasi sebaiknya menggunakan mekanisme yang lebih aman.

---

## 👤 Author

**Antonius, S.Kom.**
- 🔗 GitHub: [github.com/KuzuChis](https://github.com/KuzuChis)

---

## 📄 Lisensi

Proyek ini dibuat untuk keperluan akademis (Tugas Akhir / Skripsi).
