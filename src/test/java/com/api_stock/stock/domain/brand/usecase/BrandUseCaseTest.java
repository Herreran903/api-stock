package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.api_stock.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        brand.setName(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
        );


        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        brand.setDescription(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                        "Aenean commodo ligula eget dolor. Aenean massa. " +
                        "Cum sociis natoque penatibus et magnis dis parturient montes, " +
                        "nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu"
        );

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        brand.setName(null);

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        brand.setName("");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        brand.setDescription(null);

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        brand.setDescription("");

        BrandNotValidFieldException exception = assertThrows(
                BrandNotValidFieldException.class, () -> brandUseCase.createBrand(brand)
        );

        assertEquals(BrandExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandAlreadyExists() {
        when(brandPersistencePort.isBrandPresentByName(VALID_BRAND_NAME)).thenReturn(true);

        BrandAlreadyExistException exception = assertThrows(
                BrandAlreadyExistException.class, () -> brandUseCase.createBrand(brand)
        );

        verify(brandPersistencePort, times(1)).isBrandPresentByName(VALID_BRAND_NAME);
        assertEquals(BrandExceptionMessage.ALREADY_EXIST_BRAND, exception.getMessage());
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        assertDoesNotThrow(() -> brandUseCase.createBrand(brand));

        verify(brandPersistencePort).createBrand(brand);
    }

    @Test
    void getBrandById_ShouldReturnBrand_WhenBrandExists() {
        when(brandPersistencePort.getBrandById(VALID_BRAND_ID)).thenReturn(Optional.of(brand));

        Brand actualBrand = brandUseCase.getBrandById(VALID_BRAND_ID);

        assertEquals(brand, actualBrand);
    }

    @Test
    void getBrandByIdShouldThrowBrandNotFoundByIdExceptionWhenBrandDoesNotExist() {
        when(brandPersistencePort.getBrandById(VALID_BRAND_ID)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundByIdException.class, () -> brandUseCase.getBrandById(VALID_BRAND_ID));
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        String invalidSortDirection = "INVALID";

        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        GlobalConstants.MIN_PAGE_SIZE,
                        invalidSortDirection),
                GlobalExceptionMessage.INVALID_ORDER
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(
                        -1,
                        GlobalConstants.MIN_PAGE_SIZE,
                        GlobalConstants.ASC),
                GlobalExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        0,
                        GlobalConstants.ASC),
                GlobalExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                BrandNotValidParameterException.class,
                () -> brandUseCase.getBrandsByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        -1,
                        GlobalConstants.ASC),
                GlobalExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ASC", "DESC"})
    void shouldReturnBrandsPage(String order) {
        PageData<Brand> expectedBrandPage = new PageData<>(
                List.of(brand),
                GlobalConstants.MIN_PAGE_NUMBER,
                1,
                true,
                true,
                false,
                false
        );

        when(brandPersistencePort.getBrandsByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                order)).thenReturn(expectedBrandPage);

        PageData<Brand> result = brandUseCase.getBrandsByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                order);

        assertEquals(expectedBrandPage, result);
        verify(brandPersistencePort, times(1)).getBrandsByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                order);
    }
}