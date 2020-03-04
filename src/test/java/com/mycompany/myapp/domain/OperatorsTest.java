package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class OperatorsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operators.class);
        Operators operators1 = new Operators();
        operators1.setId(1L);
        Operators operators2 = new Operators();
        operators2.setId(operators1.getId());
        assertThat(operators1).isEqualTo(operators2);
        operators2.setId(2L);
        assertThat(operators1).isNotEqualTo(operators2);
        operators1.setId(null);
        assertThat(operators1).isNotEqualTo(operators2);
    }
}
