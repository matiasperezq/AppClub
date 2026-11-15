package com.club.util;

import org.mindrot.jbcrypt.BCrypt;

public class Clave {

    public static String hash(String texto) {
        return BCrypt.hashpw(texto, BCrypt.gensalt());
    }

    public static boolean verificar(String texto, String hash) {
        return BCrypt.checkpw(texto, hash);
    }
}
