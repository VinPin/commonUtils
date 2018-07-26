package com.vinpin.commonutils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilsTest {

    @Test
    public void byte2FitMemorySize() {
        assertEquals("12.00B", FileUtils.byte2FitMemorySize(12, 2));
        assertNotEquals("12.0B", FileUtils.byte2FitMemorySize(12, 2));
        assertEquals("2.000KB", FileUtils.byte2FitMemorySize(2048, 3));
    }
}