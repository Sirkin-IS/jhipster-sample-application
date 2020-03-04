package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TransportPackageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportPackage.class);
        TransportPackage transportPackage1 = new TransportPackage();
        transportPackage1.setId(1L);
        TransportPackage transportPackage2 = new TransportPackage();
        transportPackage2.setId(transportPackage1.getId());
        assertThat(transportPackage1).isEqualTo(transportPackage2);
        transportPackage2.setId(2L);
        assertThat(transportPackage1).isNotEqualTo(transportPackage2);
        transportPackage1.setId(null);
        assertThat(transportPackage1).isNotEqualTo(transportPackage2);
    }
}
