-- AppClub - Script de creacion de la base de datos
-- ATENCION: si ya existe el esquema bdclub, esta linea lo BORRA con todos sus datos.
DROP SCHEMA IF EXISTS bdclub;
CREATE SCHEMA bdclub;
USE bdclub;

CREATE TABLE usuario (
    idusuario INT NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(45) NOT NULL,
    correo    VARCHAR(60) NOT NULL,
    clave     VARCHAR(100) NOT NULL,                 -- hash BCrypt
    rol       VARCHAR(10) NOT NULL DEFAULT 'SOCIO',
    PRIMARY KEY (idusuario),
    UNIQUE KEY uq_usuario_correo (correo),
    CONSTRAINT chk_usuario_rol CHECK (rol IN ('ADMIN','SOCIO'))
) ENGINE=InnoDB;

CREATE TABLE deporte (
    iddeporte INT NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(30) NOT NULL,
    PRIMARY KEY (iddeporte),
    UNIQUE KEY uq_deporte_nombre (nombre)
) ENGINE=InnoDB;

CREATE TABLE cancha (
    idcancha          INT NOT NULL AUTO_INCREMENT,
    nombre            VARCHAR(45) NOT NULL,
    deporte_iddeporte INT NOT NULL,
    PRIMARY KEY (idcancha),
    CONSTRAINT fk_cancha_deporte FOREIGN KEY (deporte_iddeporte)
        REFERENCES deporte (iddeporte) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE reserva (
    idreserva         INT NOT NULL AUTO_INCREMENT,
    usuario_idusuario INT NOT NULL,
    cancha_idcancha   INT NOT NULL,
    fecha             DATE NOT NULL,
    hora_inicio       TIME NOT NULL,
    hora_fin          TIME NOT NULL,
    PRIMARY KEY (idreserva),
    CONSTRAINT fk_reserva_usuario FOREIGN KEY (usuario_idusuario)
        REFERENCES usuario (idusuario),
    CONSTRAINT fk_reserva_cancha FOREIGN KEY (cancha_idcancha)
        REFERENCES cancha (idcancha) ON DELETE CASCADE,
    -- Impide la doble reserva del mismo turno
    CONSTRAINT uq_reserva_turno UNIQUE (cancha_idcancha, fecha, hora_inicio)
) ENGINE=InnoDB;

-- Datos de prueba
INSERT INTO deporte (nombre) VALUES ('Futbol'), ('Basquet'), ('Padel');

INSERT INTO cancha (nombre, deporte_iddeporte) VALUES
    ('Futbol 5 A', 1),
    ('Futbol 5 B', 1),
    ('Basquet 1', 2),
    ('Padel 1', 3),
    ('Padel 2', 3);
