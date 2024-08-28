package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BrandCreateUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandCreateUseCase brandCreateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        Brand brand = new Brand(
                1L,
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
                "High quality clothing");


        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        Brand brand = new Brand(
                1L,
                "Nike",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                        "Aenean commodo ligula eget dolor. Aenean massa. " +
                        "Cum sociis natoque penatibus et magnis dis parturient montes, " +
                        "nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Brand brand = new Brand(1L, null, "High quality clothing");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Brand brand = new Brand(1L, "", "High quality clothing");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Brand brand = new Brand(1L, "Nike", null);

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "Nike", "");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandAlreadyExists() {
        Brand brand = new Brand(1L, "ExistingBrand", "High quality clothing");

        when(brandPersistencePort.isBrandPresentByName("ExistingBrand")).thenReturn(true);

        BrandAlreadyExistException exception = assertThrows(
                BrandAlreadyExistException.class, () -> brandCreateUseCase.createBrand(brand)
        );

        verify(brandPersistencePort, times(1)).isBrandPresentByName("ExistingBrand");
        assertEquals(BrandExceptionMessage.ALREADY_EXIST_BRAND, exception.getMessage());
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        Brand brand = new Brand(1L, "Nike", "High quality clothing");

        assertDoesNotThrow(() -> brandCreateUseCase.createBrand(brand));

        verify(brandPersistencePort).createBrand(brand);
    }
}