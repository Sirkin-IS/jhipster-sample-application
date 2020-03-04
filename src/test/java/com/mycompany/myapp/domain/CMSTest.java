package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CMSTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CMS.class);
        CMS cMS1 = new CMS();
        cMS1.setId(1L);
        CMS cMS2 = new CMS();
        cMS2.setId(cMS1.getId());
        assertThat(cMS1).isEqualTo(cMS2);
        cMS2.setId(2L);
        assertThat(cMS1).isNotEqualTo(cMS2);
        cMS1.setId(null);
        assertThat(cMS1).isNotEqualTo(cMS2);
    }
}
