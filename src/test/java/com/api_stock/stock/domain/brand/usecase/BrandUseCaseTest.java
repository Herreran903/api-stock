package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

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
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
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
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Brand brand = new Brand(1L, null, "High quality clothing");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Brand brand = new Brand(1L, "", "High quality clothing");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Brand brand = new Brand(1L, "Nike", null);

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "Nike", "");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandAlreadyExists() {
        Brand brand = new Brand(1L, "ExistingBrand", "High quality clothing");

        when(brandPersistencePort.isBrandPresentByName("ExistingBrand")).thenReturn(true);

        BrandAlreadyExistException exception = assertThrows(
                BrandAlreadyExistException.class, () -> brandUseCase.createBrand(brand)
        );

        verify(brandPersistencePort, times(1)).isBrandPresentByName("ExistingBrand");
        assertEquals(BrandExceptionMessage.ALREADY_EXIST_BRAND, exception.getMessage());
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        Brand brand = new Brand(1L, "Nike", "High quality clothing");

        assertDoesNotThrow(() -> brandUseCase.createBrand(brand));

        verify(brandPersistencePort).createBrand(brand);
    }

    @Test
    void getBrandById_ShouldReturnBrand_WhenBrandExists() {
        Long brandId = 1L;
        Brand expectedBrand = new Brand(brandId, "Nike", "High quality clothing");
        when(brandPersistencePort.getBrandById(brandId)).thenReturn(Optional.of(expectedBrand));

        Brand actualBrand = brandUseCase.getBrandById(brandId);

        assertEquals(expectedBrand, actualBrand);
    }

    @Test
    void getBrandByIdShouldThrowBrandNotFoundByIdExceptionWhenBrandDoesNotExist() {

        Long brandId = 1L;
        when(brandPersistencePort.getBrandById(brandId)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundByIdException.class, () -> brandUseCase.getBrandById(brandId));
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(0, 10, "INVALID"),
                BrandExceptionMessage.INVALID_SORT_DIRECTION
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(-1, 10, "ASC"),
                BrandExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(0, 0, "ASC"),
                BrandExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(0, -1, "ASC"),
                BrandExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ASC", "DESC"})
    void shouldReturnBrandsPage(String sortDirection) {
        Integer page = 0;
        Integer size = 10;

        PageData<Brand> expectedBrandPage = new PageData<>(
                List.of(new Brand(1L, "Nike", "High quality clothing")),
                page,
                1,
                true,
                true,
                false,
                false
        );

        when(brandPersistencePort.getBrandsByPage(page, size, sortDirection)).thenReturn(expectedBrandPage);

        PageData<Brand> result = brandUseCase.getBrandsByPage(page, size, sortDirection);

        assertEquals(expectedBrandPage, result);
        verify(brandPersistencePort, times(1)).getBrandsByPage(page, size, sortDirection);
    }
}