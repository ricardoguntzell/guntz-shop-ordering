package br.com.guntz.shop.ordering.domain.valueobject;


import br.com.guntz.shop.ordering.domain.exception.CustomerDocumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void given_NewDocument_whenAddValidDocument_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Document("806.571.170-72"));

        Assertions.assertThatNoException()
                .isThrownBy(() -> new Document("560.698.230-79"));
    }

    @Test
    void given_NewDocument_whenAddInvalidDocument_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(CustomerDocumentException.class)
                .isThrownBy(() -> new Document("806.571.170-00"));

        Assertions.assertThatExceptionOfType(CustomerDocumentException.class)
                .isThrownBy(() -> new Document("560.698.230-@@"));
    }

}