package com.coffee.export.fullstack;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @ProjectName: FullStack
 * @Author: Temesgen D.
 * @Date: 8/30/23
 */

@Testcontainers
public class TestPostgresContainers extends AbstractDaoBaseTestClass {

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }


}
