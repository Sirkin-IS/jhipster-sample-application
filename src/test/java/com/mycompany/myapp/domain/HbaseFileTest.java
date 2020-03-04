package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class HbaseFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HbaseFile.class);
        HbaseFile hbaseFile1 = new HbaseFile();
        hbaseFile1.setId(1L);
        HbaseFile hbaseFile2 = new HbaseFile();
        hbaseFile2.setId(hbaseFile1.getId());
        assertThat(hbaseFile1).isEqualTo(hbaseFile2);
        hbaseFile2.setId(2L);
        assertThat(hbaseFile1).isNotEqualTo(hbaseFile2);
        hbaseFile1.setId(null);
        assertThat(hbaseFile1).isNotEqualTo(hbaseFile2);
    }
}
