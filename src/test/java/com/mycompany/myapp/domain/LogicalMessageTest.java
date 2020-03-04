package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LogicalMessageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogicalMessage.class);
        LogicalMessage logicalMessage1 = new LogicalMessage();
        logicalMessage1.setId(1L);
        LogicalMessage logicalMessage2 = new LogicalMessage();
        logicalMessage2.setId(logicalMessage1.getId());
        assertThat(logicalMessage1).isEqualTo(logicalMessage2);
        logicalMessage2.setId(2L);
        assertThat(logicalMessage1).isNotEqualTo(logicalMessage2);
        logicalMessage1.setId(null);
        assertThat(logicalMessage1).isNotEqualTo(logicalMessage2);
    }
}
