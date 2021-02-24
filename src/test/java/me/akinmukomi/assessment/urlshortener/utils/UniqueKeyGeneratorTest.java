package me.akinmukomi.assessment.urlshortener.utils;

import me.akinmukomi.assessment.urlshortener.utils.UniqueKeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UniqueKeyGeneratorTest {

    UniqueKeyGenerator uniqueKeyGenerator = null;

    @BeforeEach
    void setUp() {

    }

    @Test
    void generateMultipleUniqueKeys() {

        uniqueKeyGenerator = new UniqueKeyGenerator(7);

        int size = 4;
        Set<String> uniqueKeys = new HashSet<>();

        for(int i = 1; i <= 4 ; i++){
            uniqueKeys.add(uniqueKeyGenerator.generateUniqueKey());
        }

        assertEquals(size , uniqueKeys.size());

    }

    @Test
    void generateUniqueKeysOfRightLength() {

        uniqueKeyGenerator = new UniqueKeyGenerator(7);
        String uniqueKey1 = uniqueKeyGenerator.generateUniqueKey();
        assertEquals(uniqueKey1.length(), 7);

        uniqueKeyGenerator = new UniqueKeyGenerator(8);
        String uniqueKey2 = uniqueKeyGenerator.generateUniqueKey();
        assertEquals(uniqueKey2.length(), 8);

        uniqueKeyGenerator = new UniqueKeyGenerator(9);
        String uniqueKey3 = uniqueKeyGenerator.generateUniqueKey();
        assertEquals(uniqueKey3.length(), 9);

        uniqueKeyGenerator = new UniqueKeyGenerator(10);
        String uniqueKey4 = uniqueKeyGenerator.generateUniqueKey();
        assertEquals(uniqueKey4.length(), 10);

    }
}