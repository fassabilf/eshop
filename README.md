# **Advance Programming Tutorial**

## **Nama**: Faiz Assabil Firdaus
## **NPM**: 2306224354
## **Kelas**: B

---

## **Reflection**
### **List of Modules**
1. **Module 1** - Coding Standards
---

## **Module 1**
### **Reflection 1.1**

Dalam mengembangkan aplikasi ini menggunakan **Spring Boot**, saya telah menerapkan beberapa prinsip **Clean Code** dan **Secure Coding Practices** untuk memastikan kode saya bersih, aman, dan mudah dikelola.

---

### **Clean Code Principles Applied**

1. **Meaningful Names**
    - Saya telah memberikan nama variabel, metode, dan kelas yang deskriptif agar mudah dipahami tanpa perlu banyak komentar tambahan.
    - Contoh: `ProductController`, `ProductService`, `findById(String id)`, yang semua namanya menjelaskan fungsinya dengan jelas.

2. **Single Responsibility Principle (SRP)**
    - Setiap kelas hanya memiliki satu tanggung jawab utama.
    - `ProductController` hanya mengatur alur request dan response.
    - `ProductServiceImpl` hanya berfokus pada business logic.
    - `ProductRepository` hanya bertanggung jawab terhadap penyimpanan data.

3. **DRY (Don't Repeat Yourself)**
    - Saya sudah menghindari duplikasi kode dengan memisahkan business logic ke dalam `ProductServiceImpl`, sehingga `ProductController` hanya menangani request dan response.

4. **Consistent Formatting**
    - Struktur kode yang rapi, konsisten dalam penggunaan konvensi pemrograman Java, seperti `{}` pada blok kode dan indentation yang seragam.

5. **Proper Use of HTTP Methods**
    - Endpoint menggunakan metode HTTP yang sesuai:
        - `GET` untuk mendapatkan data (`/product/list`, `/product/edit/{id}`)
        - `POST` untuk membuat dan memperbarui data (`/product/create`, `/product/edit`)
        - `GET` untuk menghapus data (`/product/delete/{id}`)

6. **Code Modularity**
    - Memisahkan berbagai bagian kode menjadi controller, service, repository, dan model agar lebih mudah dipahami dan diuji.

---

### **Secure Coding Practices Applied**

1. **Input Validation**
    - **Saat ini belum ada validasi input**, sehingga perlu ditambahkan validasi untuk memastikan data yang masuk valid, seperti:
        - Nama produk tidak boleh kosong.
        - Kuantitas harus berupa angka positif.
    - Bisa menggunakan **Spring Validator** atau anotasi `@Valid`.

2. **Proper Exception Handling**
    - Saat ini, jika produk tidak ditemukan pada `findById()`, aplikasi mengembalikan `null`, yang bisa menyebabkan error saat diproses.
    - **Perbaikan:**
        - Menggunakan `Optional<Product>` di repository agar lebih aman.
        - Jika produk tidak ditemukan, sebaiknya lemparkan `ProductNotFoundException` dengan pesan yang lebih informatif.

3. **Preventing CSRF Attacks**
    - **Saat ini belum ada proteksi terhadap CSRF**, sebaiknya Spring Security diaktifkan untuk mencegah serangan ini.

4. **Feedback to User**
    - Setelah operasi sukses seperti **create, edit, atau delete**, sebaiknya menggunakan **RedirectAttributes** untuk memberikan pesan sukses/gagal ke pengguna.

5. **Improved Error Messages**
    - Jika ada kesalahan seperti produk tidak ditemukan, sebaiknya tampilkan halaman error khusus yang lebih ramah pengguna daripada **Whitelabel Error Page** dari Spring Boot.

---

### **Areas for Improvement**

Berdasarkan evaluasi kode saya, beberapa hal yang perlu diperbaiki atau ditingkatkan ke depannya:

1. **Tambah Validasi Input**
    - Gunakan anotasi `@Valid` dan `@Size`, `@NotNull`, serta custom validation jika perlu.

2. **Gunakan Exception Handling yang Lebih Baik**
    - Buat custom exception seperti `ProductNotFoundException` agar kode lebih aman dan informatif.

3. **Perbaiki Error Page untuk User Experience yang Lebih Baik**
    - Saat ini, jika terjadi kesalahan, pengguna mendapatkan halaman putih dengan pesan error standar.
    - Solusi: Buat halaman error khusus menggunakan **Thymeleaf Error Handling**.

4. **Implementasikan Spring Security untuk Perlindungan Lebih Lanjut**
    - Aktifkan proteksi CSRF untuk menghindari serangan berbahaya.
    - Tambahkan autentikasi jika aplikasi berkembang lebih lanjut.
