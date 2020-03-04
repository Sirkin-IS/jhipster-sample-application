package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TransportPackageRepeatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportPackageRepeat.class);
        TransportPackageRepeat transportPackageRepeat1 = new TransportPackageRepeat();
        transportPackageRepeat1.setId(1L);
        TransportPackageRepeat transportPackageRepeat2 = new TransportPackageRepeat();
        transportPackageRepeat2.setId(transportPackageRepeat1.getId());
        assertThat(transportPackageRepeat1).isEqualTo(transportPackageRepeat2);
        transportPackageRepeat2.setId(2L);
        assertThat(transportPackageRepeat1).isNotEqualTo(transportPackageRepeat2);
        transportPackageRepeat1.setId(null);
        assertThat(transportPackageRepeat1).isNotEqualTo(transportPackageRepeat2);
    }
}
