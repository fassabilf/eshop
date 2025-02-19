package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("123");
        sampleProduct.setProductName("Sample Product");
        sampleProduct.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct.getProductId(), "Produk harus memiliki UUID setelah dibuat.");
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size(), "Jumlah produk yang dikembalikan harus sesuai.");
        assertEquals("123", result.get(0).getProductId(), "Produk yang dikembalikan harus sesuai.");
    }

    @Test
    void testFindById_ProductFound() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        Product result = productService.findById("123");

        assertNotNull(result, "Produk harus ditemukan.");
        assertEquals("123", result.getProductId(), "Produk yang ditemukan harus memiliki ID yang sesuai.");
    }

    @Test
    void testFindById_ProductNotFound() {
        List<Product> productList = new ArrayList<>();
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        Product result = productService.findById("999");

        assertNull(result, "Produk yang tidak ditemukan harus mengembalikan null.");
    }

    @Test
    void testUpdateProduct_ProductFound() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("123");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(20);

        Product result = productService.update(updatedProduct);

        assertNotNull(result, "Produk hasil update tidak boleh null.");
        assertEquals("123", result.getProductId(), "ID produk harus tetap sama.");
        assertEquals("Updated Product", result.getProductName(), "Nama produk harus terupdate.");
        assertEquals(20, result.getProductQuantity(), "Kuantitas produk harus terupdate.");
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        List<Product> productList = new ArrayList<>();
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        Product newProduct = new Product();
        newProduct.setProductId("999");
        newProduct.setProductName("New Product");
        newProduct.setProductQuantity(5);

        Product result = productService.update(newProduct);

        assertNotNull(result, "Produk harus dikembalikan walaupun tidak ditemukan.");
        assertEquals("999", result.getProductId(), "Produk yang tidak ditemukan harus tetap memiliki ID yang sama.");
        assertEquals("New Product", result.getProductName(), "Nama produk harus tetap sama.");
        assertEquals(5, result.getProductQuantity(), "Kuantitas produk harus tetap sama.");
    }

    @Test
    void testDeleteProduct() {
        productService.delete("123");

        verify(productRepository, times(1)).delete("123");
    }

    @Test
    void testFindById_LoopCompletesWithoutFinding() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("111");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productList.add(product1);

        Iterator<Product> productIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        Product result = productService.findById("999"); // ID yang tidak ada

        assertNull(result, "Jika produk tidak ditemukan, harus mengembalikan null.");
    }

    @Test
    void testUpdateProduct_LoopCompletesWithoutFinding() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("111");
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productList.add(product1);

        Iterator<Product> productIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        Product productToUpdate = new Product();
        productToUpdate.setProductId("999"); // ID yang tidak ada dalam daftar
        productToUpdate.setProductName("Non Existing Product");
        productToUpdate.setProductQuantity(5);

        Product result = productService.update(productToUpdate);

        assertNotNull(result, "Produk harus tetap dikembalikan walaupun tidak ditemukan.");
        assertEquals("999", result.getProductId(), "Produk harus tetap memiliki ID yang sama.");
        assertEquals("Non Existing Product", result.getProductName(), "Nama produk harus tetap sama.");
        assertEquals(5, result.getProductQuantity(), "Kuantitas produk harus tetap sama.");
    }

}
