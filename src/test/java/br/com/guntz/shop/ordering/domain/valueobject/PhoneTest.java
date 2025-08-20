package br.com.guntz.shop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void given_NewPhone_whenAddPhoneNotBlank_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Phone("11 91234-5555"));
    }

    @Test
    void given_NewPhone_whenAddPhoneInBlank_shouldNotGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Phone(""));
    }


}