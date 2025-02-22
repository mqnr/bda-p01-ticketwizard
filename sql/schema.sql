DROP DATABASE IF EXISTS bda_p01_ticketwizard;

CREATE DATABASE bda_p01_ticketwizard;

USE bda_p01_ticketwizard;

CREATE TABLE eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    recinto VARCHAR(50) NOT NULL,
    ciudad VARCHAR(50) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL
);

CREATE TABLE boletos (
    numero_serie INT AUTO_INCREMENT PRIMARY KEY,
    precio DECIMAL(10, 2) NOT NULL,
    fila INT NOT NULL,
    asiento VARCHAR(30) NOT NULL,
    id_asiento INT NOT NULL,
    usuario_id INT,
    evento_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
);

CREATE TABLE transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    boleto_id INT NOT NULL,
    tipo_adquisicion ENUM('VENTA_INICIAL', 'REVENTA') NOT NULL,
    vendedor_id INT,
    comprador_id INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    comision DECIMAL(10, 2),
    fecha DATETIME NOT NULL,
    FOREIGN KEY (boleto_id) REFERENCES boletos(numero_serie),
    FOREIGN KEY (vendedor_id) REFERENCES usuarios(id),
    FOREIGN KEY (comprador_id) REFERENCES usuarios(id),
    CONSTRAINT chk_venta_inicial_sin_vendedor
        CHECK (NOT (tipo_adquisicion = 'VENTA_INICIAL' AND vendedor_id IS NOT NULL)),
    CONSTRAINT chk_reventa_require_vendedor
        CHECK (NOT (tipo_adquisicion = 'REVENTA' AND vendedor_id IS NULL)),
    CONSTRAINT chk_venta_inicial_sin_comision
        CHECK (NOT (tipo_adquisicion = 'VENTA_INICIAL' AND comision IS NOT NULL)),
    CONSTRAINT chk_reventa_requiere_comision
        CHECK (NOT (tipo_adquisicion = 'REVENTA' AND comision IS NULL))
);

CREATE TABLE compras_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    apartado BOOLEAN DEFAULT FALSE,
    fecha_limite DATETIME,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE historiales_cv (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_evento VARCHAR(50) NOT NULL,
    rango_fecha DATE NOT NULL
);
