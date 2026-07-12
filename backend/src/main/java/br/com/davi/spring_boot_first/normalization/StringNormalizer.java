package br.com.davi.spring_boot_first.normalization;


public class StringNormalizer {


    public static String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ");
    }


    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

}
