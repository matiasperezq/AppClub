package com.club.util;

import com.club.entidades.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Acceso {

    public static Usuario usuario(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion == null) {
            return null;
        }
        return (Usuario) sesion.getAttribute("usuario");
    }

    public static boolean esAdmin(HttpServletRequest request) {
        Usuario u = usuario(request);
        return u != null && "ADMIN".equals(u.getRol());
    }

    public static boolean esSocio(HttpServletRequest request) {
        Usuario u = usuario(request);
        return u != null && "SOCIO".equals(u.getRol());
    }
}