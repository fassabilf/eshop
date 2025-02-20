package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // Memastikan konteks Spring Boot bisa dimuat tanpa error
    }

    @Test
    void testMainMethod() {
        // Menggunakan mock untuk SpringApplication agar bisa diuji
        SpringApplication mockSpringApp = mock(SpringApplication.class);

        // Simulasi menjalankan aplikasi
        EshopApplication.main(new String[]{});

        // Tidak perlu verify() karena kita hanya memeriksa apakah metode bisa dipanggil tanpa error
    }
}
