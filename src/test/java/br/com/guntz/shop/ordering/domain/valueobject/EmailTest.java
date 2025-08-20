package br.com.guntz.shop.ordering.domain.valueobject;


import br.com.guntz.shop.ordering.domain.exception.CustomerDocumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void given_NewEmail_whenAddValidEmail_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Email("anonymous@anonymous.com.br"));
    }

    @Test
    void given_NewEmail_whenAddInvalidEmail_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("invalid"));
    }


}