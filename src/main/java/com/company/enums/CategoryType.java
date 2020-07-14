package com.company.enums;

import java.util.Random;

public enum CategoryType {
    Fantasy, Narrative_Nonfiction, Science_Fiction, Reference_Book, Metafiction, Biography_Autobiography;

    public static CategoryType getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
