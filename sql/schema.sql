DROP DATABASE IF EXISTS bda_p01_ticketwizard;

CREATE DATABASE bda_p01_ticketwizard;

USE bda_p01_ticketwizard;

CREATE TABLE eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
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
    saldo DECIMAL(10, 2) NOT NULL,
    edad INT NOT NULL
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
    num_transaccion INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora_adquisicion DATETIME NOT NULL,
    tipo_adquisicion VARCHAR(20) NOT NULL,
    evento_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (evento_id) REFERENCES eventos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
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

DELIMITER //
DROP TRIGGER IF EXISTS derivar_edad_insert//
CREATE TRIGGER derivar_edad_insert BEFORE INSERT ON usuarios
FOR EACH ROW
BEGIN
    SET NEW.edad = TIMESTAMPDIFF(YEAR, NEW.fecha_nacimiento, CURDATE());
END //

DROP TRIGGER IF EXISTS derivar_edad_update//
CREATE TRIGGER derivar_edad_update BEFORE UPDATE ON usuarios
FOR EACH ROW
BEGIN
    SET NEW.edad = TIMESTAMPDIFF(YEAR, NEW.fecha_nacimiento, CURDATE());
END //
DELIMITER ;
