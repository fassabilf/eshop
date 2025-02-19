package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId("12345");
        product.setProductName("Shampoo Original");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Update data
        Product updatedProduct = new Product();
        updatedProduct.setProductId("12345");
        updatedProduct.setProductName("Shampoo Premium");
        updatedProduct.setProductQuantity(20);
        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("12345", result.getProductId());
        assertEquals("Shampoo Premium", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("99999");
        product.setProductName("Sabun Mewah");
        product.setProductQuantity(5);
        productRepository.create(product);

        // Pastikan produk ada sebelum dihapus
        assertNotNull(productRepository.findById("99999"));

        // Hapus produk
        productRepository.delete("99999");

        // Pastikan produk sudah tidak ada
        assertNull(productRepository.findById("99999"));
    }

    @Test
    void testFindByIdNotFound() {
        Product result = productRepository.findById("non-existing-id");
        assertNull(result, "Produk dengan ID yang tidak ada seharusnya mengembalikan null.");
    }

    @Test
    void testUpdateProductNotFound() {
        Product product = new Product();
        product.setProductId("not-exist");
        product.setProductName("Non Existing Product");
        product.setProductQuantity(99);

        Product result = productRepository.update(product);

        assertNotNull(result);
        assertEquals("not-exist", result.getProductId());
        assertEquals("Non Existing Product", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }
    @Test
    void testFindByIdNotFoundLoopCompletes() {
        // Tambahkan beberapa produk, tapi tanpa ID yang dicari
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Cari produk yang tidak ada dalam list
        Product result = productRepository.findById("non-existing-id");

        // Pastikan loop berjalan sampai akhir dan return null
        assertNull(result, "Produk dengan ID yang tidak ada seharusnya mengembalikan null.");
    }

    @Test
    void testUpdateProductNotFoundLoopCompletes() {
        // Tambahkan beberapa produk, tapi tidak ada yang cocok dengan ID yang dicari
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Produk yang akan di-update (ID tidak ada dalam list)
        Product productToUpdate = new Product();
        productToUpdate.setProductId("non-existing-id");
        productToUpdate.setProductName("New Product");
        productToUpdate.setProductQuantity(99);

        // Lakukan update
        Product result = productRepository.update(productToUpdate);

        // Pastikan produk yang dikembalikan adalah yang baru tanpa perubahan
        assertNotNull(result);
        assertEquals("non-existing-id", result.getProductId());
        assertEquals("New Product", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }

}
