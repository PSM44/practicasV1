package com.ipss.practicas.web.auth;

import com.ipss.practicas.model.enums.RolUsuario;

public record SessionUser(Long userId, RolUsuario rol, String username) {}
