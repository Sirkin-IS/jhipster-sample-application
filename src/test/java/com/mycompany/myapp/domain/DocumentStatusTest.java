package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DocumentStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStatus.class);
        DocumentStatus documentStatus1 = new DocumentStatus();
        documentStatus1.setId(1L);
        DocumentStatus documentStatus2 = new DocumentStatus();
        documentStatus2.setId(documentStatus1.getId());
        assertThat(documentStatus1).isEqualTo(documentStatus2);
        documentStatus2.setId(2L);
        assertThat(documentStatus1).isNotEqualTo(documentStatus2);
        documentStatus1.setId(null);
        assertThat(documentStatus1).isNotEqualTo(documentStatus2);
    }
}
