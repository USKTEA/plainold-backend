package com.usktea.plainold.sessions;

import com.usktea.plainold.exceptions.TidNotFound;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class TidSessionTest {
    @Test
    void save() {
        TidSession tidSession = new TidSession();

        String tid = "1";

        Integer id = tidSession.save(tid);

        assertThat(id).isEqualTo(1);
    }

    @Test
    void saveTwoTids() {
        TidSession tidSession = new TidSession();

        String tid1 = "1";
        String tid2 = "2";

        Integer id1 = tidSession.save(tid1);
        Integer id2 = tidSession.save(tid2);

        assertThat(id1).isEqualTo(1);
        assertThat(id2).isEqualTo(2);
    }

    @Test
    void findTidSuccess() {
        TidSession tidSession = new TidSession();

        String tid = "1";

        Integer id = tidSession.save(tid);

        String found = tidSession.getTid(id);

        assertThat(found).isEqualTo(tid);
    }

    @Test
    void findTidFailed() {
        TidSession tidSession = new TidSession();

        Integer invalidId = 9_999_999;

        assertThrows(TidNotFound.class, () -> tidSession.getTid(invalidId));
    }
}
