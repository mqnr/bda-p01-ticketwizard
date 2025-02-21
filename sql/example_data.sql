INSERT INTO usuarios (email, nombre, apellido, direccion, fecha_nacimiento, saldo) VALUES
    ('maria.garcia@gmail.com', 'María', 'García', 'Av. Universidad 3000, Coyoacán, CDMX', '1995-03-15', 5000),
    ('juan.lopez@hotmail.com', 'Juan', 'López', 'Calz. México-Tacuba 1501, Miguel Hidalgo, CDMX', '1990-08-22', 2500),
    ('ana.martinez@outlook.com', 'Ana', 'Martínez', 'Prolongación Paseo de la Reforma 880, Santa Fe, CDMX', '1998-11-30', 7500),
    ('carlos.rodriguez@yahoo.com', 'Carlos', 'Rodríguez', 'Av. Chapultepec 540, Roma Norte, CDMX', '1993-05-07', 1000),
    ('sofia.hernandez@gmail.com', 'Sofía', 'Hernández', 'Av. de los Insurgentes Sur 1685, Guadalupe Inn, CDMX', '1997-01-25', 3500);

INSERT INTO eventos (nombre, fecha, recinto, ciudad, estado, descripcion) VALUES
('Concierto RockFest', '2025-06-15', 'Auditorio Nacional', 'Ciudad de México', 'CDMX', 'Festival de rock con bandas internacionales.'),
('Final Liga MX', '2025-05-20', 'Estadio Azteca', 'Ciudad de México', 'CDMX', 'Partido final de la Liga MX, temporada 2025.'),
('Musical Broadway', '2025-07-10', 'Teatro de la Ciudad', 'Guadalajara', 'Jalisco', 'Obra de teatro musical con elenco de Broadway.');


INSERT INTO boletos (precio, fila, asiento, id_asiento, usuario_id, evento_id) VALUES
(1500.00, 1, 'A1', 101, 1, 1),
(1500.00, 1, 'A2', 102, 1, 1),
(1400.00, 2, 'B1', 103, 2, 1),
(1400.00, 2, 'B2', 104, 2, 1),
(1300.00, 3, 'C1', 105, NULL, 1), 
(1300.00, 3, 'C2', 106, NULL, 1), 
(1200.00, 4, 'D1', 107, 3, 1),
(1200.00, 4, 'D2', 108, 3, 1),
(1100.00, 5, 'E1', 109, NULL, 1), 
(1100.00, 5, 'E2', 110, 4, 1),
(2500.00, 10, 'F1', 111, 5, 2),
(2500.00, 10, 'F2', 112, 5, 2),
(2300.00, 11, 'G1', 113, NULL, 2), 
(2300.00, 11, 'G2', 114, NULL, 2), 
(2200.00, 12, 'H1', 115, 1, 2),
(2200.00, 12, 'H2', 116, 1, 2),
(2100.00, 13, 'I1', 117, 2, 2),
(2100.00, 13, 'I2', 118, 2, 2),
(2000.00, 14, 'J1', 119, NULL, 2), 
(2000.00, 14, 'J2', 120, 3, 2),
(1800.00, 6, 'K1', 121, NULL, 3), 
(1800.00, 6, 'K2', 122, 4, 3),
(1700.00, 7, 'L1', 123, 4, 3),
(1700.00, 7, 'L2', 124, 5, 3),
(1600.00, 8, 'M1', 125, NULL, 3), 
(1600.00, 8, 'M2', 126, 1, 3),
(1500.00, 9, 'N1', 127, 2, 3),
(1500.00, 9, 'N2', 128, 3, 3),
(1400.00, 10, 'O1', 129, NULL, 3), 
(1400.00, 10, 'O2', 130, NULL, 3); 

