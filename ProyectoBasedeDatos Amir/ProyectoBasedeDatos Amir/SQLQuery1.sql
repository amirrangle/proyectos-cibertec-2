-- CREAR BASE DE DATOS DESDE CERO
IF DB_ID('LIBROEXPRESS') IS NOT NULL
BEGIN
    ALTER DATABASE LIBROEXPRESS SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE LIBROEXPRESS;
END;
GO
CREATE DATABASE LIBROEXPRESS;
GO
USE LIBROEXPRESS;
GO

-- TABLAS DE NORMALIZACIÓN
-- Tablas para países y ciudades
CREATE TABLE Pais(
    Cod_Pais INT PRIMARY KEY,
    Nombre_Pais NVARCHAR(100) NOT NULL
);
CREATE TABLE Ciudad(
    Cod_Ciudad INT PRIMARY KEY,
    Nombre_Ciudad NVARCHAR(100) NOT NULL,
    Cod_Pais INT,
    FOREIGN KEY (Cod_Pais) REFERENCES Pais(Cod_Pais)
);

-- Tablas para empleados
CREATE TABLE Area(
    Cod_Area INT PRIMARY KEY,
    Nombre_Area NVARCHAR(100) NOT NULL
);
CREATE TABLE Cargo(
    Cod_Cargo INT PRIMARY KEY,
    Nombre_Cargo NVARCHAR(100) NOT NULL
);
CREATE TABLE Empleado(
    Cod_Empleado INT PRIMARY KEY,
    Nom_Empleado NVARCHAR(100) NOT NULL,
    Cod_Area INT,
    Cod_Cargo INT,
    FOREIGN KEY (Cod_Area) REFERENCES Area(Cod_Area),
    FOREIGN KEY (Cod_Cargo) REFERENCES Cargo(Cod_Cargo)
);

-- Tablas para clientes
CREATE TABLE Cliente(
    Doc_Cliente NVARCHAR(20) PRIMARY KEY,
    Nom_Cliente NVARCHAR(100) NOT NULL,
    Direc_Cliente NVARCHAR(150),
    Telef_Cliente NVARCHAR(20),
    Cod_Ciudad INT,
    FOREIGN KEY (Cod_Ciudad) REFERENCES Ciudad(Cod_Ciudad)
);

-- Tablas para proveedores
CREATE TABLE Proveedor(
    Doc_Proveedor NVARCHAR(20) PRIMARY KEY,
    Nom_Proveedor NVARCHAR(100) NOT NULL,
    Direccion NVARCHAR(150),
    Telefono NVARCHAR(20),
    Email NVARCHAR(100),
    Cod_Ciudad INT,
    FOREIGN KEY (Cod_Ciudad) REFERENCES Ciudad(Cod_Ciudad)
);

-- Tablas para categorías de productos
CREATE TABLE Categoria(
    Cod_Categoria INT PRIMARY KEY,
    Nombre_Categoria NVARCHAR(100) NOT NULL,
    Descripcion NVARCHAR(255)
);

-- Tablas para unidades de medida
CREATE TABLE Unidad_Medida(
    Cod_UnidadMedida NVARCHAR(10) PRIMARY KEY,
    Nombre_Unidad NVARCHAR(50) NOT NULL,
    Abreviatura NVARCHAR(10)
);

-- Tablas para productos/libros
CREATE TABLE Libro(
    Cod_Libro NVARCHAR(20) PRIMARY KEY,
    Titulo NVARCHAR(150) NOT NULL,
    Descripcion NVARCHAR(255),
    Autor NVARCHAR(100) NOT NULL,
    Cod_Categoria INT,
    Cod_UnidadMedida NVARCHAR(10),
    Precio DECIMAL(10,2) NOT NULL,
    Stock INT,
    FOREIGN KEY (Cod_UnidadMedida) REFERENCES Unidad_Medida(Cod_UnidadMedida),
    FOREIGN KEY (Cod_Categoria) REFERENCES Categoria(Cod_Categoria)
);

-- Tabla para relacionar proveedores con libros (un proveedor puede tener varios libros)
CREATE TABLE Proveedor_Libro(
    Doc_Proveedor NVARCHAR(20),
    Cod_Libro NVARCHAR(20),
    PRIMARY KEY (Doc_Proveedor, Cod_Libro),
    FOREIGN KEY (Doc_Proveedor) REFERENCES Proveedor(Doc_Proveedor),
    FOREIGN KEY (Cod_Libro) REFERENCES Libro(Cod_Libro)
);

-- Tablas para almacenes
CREATE TABLE Almacen(
    Cod_Almacen INT PRIMARY KEY,
    Nombre NVARCHAR(100) NOT NULL,
    Direccion NVARCHAR(150),
    Cod_Ciudad INT,
    FOREIGN KEY (Cod_Ciudad) REFERENCES Ciudad(Cod_Ciudad)
);

-- Tablas para pedidos
CREATE TABLE Pedido(
    Cod_Pedido INT PRIMARY KEY,
    Fecha DATE,
    Doc_Cliente NVARCHAR(20),
    Cod_Empleado INT,
    FOREIGN KEY (Doc_Cliente) REFERENCES Cliente(Doc_Cliente),
    FOREIGN KEY (Cod_Empleado) REFERENCES Empleado(Cod_Empleado)
);
CREATE TABLE Detalle_Pedido(
    Cod_Pedido INT,
    Cod_Libro NVARCHAR(20),
    Cantidad INT,
    Precio_Unitario DECIMAL(10,2),
    PRIMARY KEY (Cod_Pedido, Cod_Libro),
    FOREIGN KEY (Cod_Pedido) REFERENCES Pedido(Cod_Pedido),
    FOREIGN KEY (Cod_Libro) REFERENCES Libro(Cod_Libro)
);

-- Tablas para facturación
CREATE TABLE Factura(
    Cod_Fact INT PRIMARY KEY,
    Fecha_Emision DATE,
    Subtotal DECIMAL(10,2),
    Valor_Venta DECIMAL(10,2),
    IGV DECIMAL(10,2),
    Importe_Total DECIMAL(10,2),
    Orden_Compra NVARCHAR(20),
    Doc_Cliente NVARCHAR(20),
    Cod_Empleado INT,
    FOREIGN KEY (Doc_Cliente) REFERENCES Cliente(Doc_Cliente),
    FOREIGN KEY (Cod_Empleado) REFERENCES Empleado(Cod_Empleado)
);
CREATE TABLE Detalle_Fact(
    Cod_Fact INT,
    Cod_Libro NVARCHAR(20),
    Cantidad INT,
    Precio_Unitario DECIMAL(10,2),
    PRIMARY KEY (Cod_Fact, Cod_Libro),
    FOREIGN KEY (Cod_Fact) REFERENCES Factura(Cod_Fact),
    FOREIGN KEY (Cod_Libro) REFERENCES Libro(Cod_Libro)
);

-- Tablas para reclamos
CREATE TABLE TipoReclamo(
    Cod_Reclamacion INT PRIMARY KEY,
    Nom_Reclamacion NVARCHAR(100) NOT NULL,
    Descripcion NVARCHAR(255)
);
CREATE TABLE Reclamo(
    Codigo_HReclamo INT PRIMARY KEY,
    Fecha_HReclamo DATE,
    Doc_Proveedor NVARCHAR(20),
    Doc_Reclamante NVARCHAR(20),
    CodProdOServi NVARCHAR(20),
    Monto DECIMAL(10,2),
    Detalle NVARCHAR(500),
    Cod_Reclamacion INT,
    Fecha_Respuesta DATE,
    FOREIGN KEY (Doc_Proveedor) REFERENCES Proveedor(Doc_Proveedor),
    FOREIGN KEY (Doc_Reclamante) REFERENCES Cliente(Doc_Cliente),
    FOREIGN KEY (CodProdOServi) REFERENCES Libro(Cod_Libro),
    FOREIGN KEY (Cod_Reclamacion) REFERENCES TipoReclamo(Cod_Reclamacion)
);

-- DATOS
-- Insertar datos de países y ciudades
INSERT INTO Pais VALUES
(1, 'Perú'),
(2, 'España'),
(3, 'Argentina');
INSERT INTO Ciudad VALUES
(1, 'Lima', 1),
(2, 'Cusco', 1),
(3, 'Arequipa', 1),
(4, 'Madrid', 2),
(5, 'Barcelona', 2),
(6, 'Buenos Aires', 3);

-- Insertar datos de áreas y cargos
INSERT INTO Area VALUES
(1, 'Ventas'),
(2, 'Logística'),
(3, 'Atención al Cliente'),
(4, 'Administración');
INSERT INTO Cargo VALUES
(1, 'Vendedor'),
(2, 'Almacenero'),
(3, 'Soporte'),
(4, 'Gerente'),
(5, 'Asistente');
INSERT INTO Empleado VALUES
(1, 'Carlos Pérez', 1, 1),
(2, 'María Gómez', 2, 2),
(3, 'Juan Torres', 3, 3),
(4, 'Amir Andre Moran Rangle', 1, 4);

-- Insertar categorías
INSERT INTO Categoria VALUES
(1, 'Ciencia', 'Libros de ciencia y tecnología'),
(2, 'Literatura', 'Obras literarias y novelas'),
(3, 'Educación', 'Material educativo y académico'),
(4, 'Referencia', 'Diccionarios y enciclopedias');

-- Insertar unidades de medida
INSERT INTO Unidad_Medida VALUES
('UND', 'Unidad', 'UND'),
('PQT', 'Paquete', 'PQT'),
('CJA', 'Caja', 'CJA'),
('PAR', 'Par', 'PAR'),
('KIT', 'Kit', 'KIT');

-- Insertar clientes
INSERT INTO Cliente VALUES
('20260100808', 'Editorial San Marcos E.I.R.L', 'Jr. Davalos Lisson Nro. 135 (Alt. Cdra 9 de Wilson)', '(01) 650-7821', 1),
('20100055555', 'Librería Internacional', 'Av. Arequipa 1234', '(01) 444-5678', 1),
('20444433333', 'Distribuidora Andina', 'Calle Mercaderes 222', '(084) 245-789', 2);

-- Insertar proveedores
INSERT INTO Proveedor VALUES
('20666677777', 'Editorial Planeta', 'Av. Universitaria 555', '014444555', 'planeta@email.com', 1),
('20555544444', 'Santillana S.A.', 'Av. Arequipa 321', '015555666', 'santillana@email.com', 1),
('20777788888', 'Penguin Random House', 'Av. Larco 888', '016666777', 'penguin@email.com', 1);

-- Insertar libros con descripción y todos los campos requeridos
INSERT INTO Libro VALUES
('TXM2568', 'Libro uniciencia Sto. Secundaria', 'Libro de ciencias para educación secundaria con contenido actualizado', 'Varios Autores', 3, 'UND', 60.00, 50),
('L001', 'Cien años de soledad', 'Novela clásica del realismo mágico latinoamericano', 'Gabriel García Márquez', 2, 'UND', 59.90, 50),
('L002', 'La ciudad y los perros', 'Novela que retrata la vida en un colegio militar limeño', 'Mario Vargas Llosa', 2, 'UND', 49.50, 40),
('L003', 'Rayuela', 'Obra innovadora que puede leerse de múltiples formas', 'Julio Cortázar', 2, 'UND', 65.00, 30),
('L004', 'Don Quijote de la Mancha', 'Clásico de la literatura universal', 'Miguel de Cervantes', 2, 'UND', 70.00, 20),
('SCI001', 'Física Moderna', 'Libro de texto para estudiantes de física avanzada', 'Carlos Sánchez', 1, 'UND', 85.00, 25),
('EDU002', 'Matemáticas Avanzadas', 'Texto educativo para nivel universitario', 'María Rodríguez', 3, 'UND', 75.50, 35);

-- Relacionar proveedores con libros
INSERT INTO Proveedor_Libro VALUES
('20666677777', 'TXM2568'),
('20666677777', 'L001'),
('20666677777', 'L002'),
('20555544444', 'L003'),
('20555544444', 'L004'),
('20777788888', 'SCI001'),
('20777788888', 'EDU002');

-- Insertar almacenes
INSERT INTO Almacen VALUES
(1, 'Central', 'Av. Javier Prado 2456, San Borja', 1),
(2, 'Sucursal Norte', 'Av. Túpac Amaru 200', 1),
(3, 'Sucursal Cusco', 'Calle Mantas 123', 2);

-- Insertar pedidos
INSERT INTO Pedido VALUES
(1001, '2025-08-01', '20260100808', 1),
(1002, '2025-08-05', '20100055555', 2),
(1003, '2025-08-08', '20444433333', 3);
INSERT INTO Detalle_Pedido VALUES
(1001, 'TXM2568', 2, 60.00),
(1001, 'L002', 1, 49.50),
(1002, 'L003', 3, 65.00),
(1003, 'L004', 1, 70.00);

-- Insertar facturas
INSERT INTO Factura VALUES
(1, '2025-08-01', 120.00, 98.40, 21.60, 120.00, '00265', '20260100808', 1),
(2, '2025-08-05', 195.00, 160.00, 35.00, 195.00, '00266', '20100055555', 2),
(3, '2025-08-08', 70.00, 57.38, 12.62, 70.00, '00267', '20444433333', 3);
INSERT INTO Detalle_Fact VALUES
(1, 'TXM2568', 2, 60.00),
(1, 'L002', 1, 49.50),
(2, 'L003', 3, 65.00),
(3, 'L004', 1, 70.00);

-- Insertar tipos de reclamo
INSERT INTO TipoReclamo VALUES
(1, 'Reclamo', 'Disconformidad relacionada a los productos o servicios'),
(2, 'Queja', 'Disconformidad no relacionada a los productos o servicios');
INSERT INTO Reclamo VALUES
(1, '2025-08-10', '20666677777', '20260100808', 'TXM2568', 120.00, 'Libro con páginas defectuosas', 1, '2025-08-12'),
(2, '2025-08-12', '20555544444', '20100055555', 'L003', 195.00, 'Demora en entrega del pedido', 1, '2025-08-14'),
(3, '2025-08-15', '20777788888', '20444433333', 'L004', 70.00, 'Falla en encuadernación', 1, '2025-08-18');

-- VISTAS COMPLETAS CON TODA LA INFORMACIÓN
-- Vista completa de información de clientes
CREATE VIEW vw_Clientes_Completos AS
SELECT
    c.Doc_Cliente,
    c.Nom_Cliente,
    c.Direc_Cliente,
    c.Telef_Cliente,
    ci.Nombre_Ciudad,
    p.Nombre_Pais,
    ci.Cod_Ciudad,
    p.Cod_Pais
FROM Cliente c
JOIN Ciudad ci ON c.Cod_Ciudad = ci.Cod_Ciudad
JOIN Pais p ON ci.Cod_Pais = p.Cod_Pais;
GO

-- Vista completa de información de empleados
CREATE VIEW vw_Empleados_Completos AS
SELECT
    e.Cod_Empleado,
    e.Nom_Empleado,
    a.Cod_Area,
    a.Nombre_Area,
    c.Cod_Cargo,
    c.Nombre_Cargo,
    a.Nombre_Area + ' - ' + c.Nombre_Cargo AS Area_Cargo
FROM Empleado e
JOIN Area a ON e.Cod_Area = a.Cod_Area
JOIN Cargo c ON e.Cod_Cargo = c.Cod_Cargo;
GO

-- Vista completa de información de proveedores
CREATE VIEW vw_Proveedores_Completos AS
SELECT
    pr.Doc_Proveedor,
    pr.Nom_Proveedor,
    pr.Direccion,
    pr.Telefono,
    pr.Email,
    ci.Nombre_Ciudad,
    p.Nombre_Pais,
    ci.Cod_Ciudad,
    p.Cod_Pais
FROM Proveedor pr
JOIN Ciudad ci ON pr.Cod_Ciudad = ci.Cod_Ciudad
JOIN Pais p ON ci.Cod_Pais = p.Cod_Pais;
GO

-- Vista completa de información de libros/productos
CREATE VIEW vw_Libros_Completos AS
SELECT
    l.Cod_Libro,
    l.Titulo,
    l.Descripcion,
    l.Autor,
    cat.Cod_Categoria,
    cat.Nombre_Categoria,
    um.Cod_UnidadMedida,
    um.Nombre_Unidad,
    um.Abreviatura,
    l.Precio,
    l.Stock,
    CASE
        WHEN l.Stock > 20 THEN 'Stock suficiente'
        WHEN l.Stock BETWEEN 10 AND 20 THEN 'Stock medio'
        ELSE 'Stock bajo'
    END AS Estado_Stock
FROM Libro l
JOIN Categoria cat ON l.Cod_Categoria = cat.Cod_Categoria
JOIN Unidad_Medida um ON l.Cod_UnidadMedida = um.Cod_UnidadMedida;
GO

-- Vista de proveedores con los libros que ofrecen
CREATE VIEW vw_Proveedores_Libros AS
SELECT
    p.Doc_Proveedor,
    p.Nom_Proveedor,
    p.Direccion,
    p.Telefono,
    p.Email,
    pl.Cod_Libro,
    l.Titulo,
    l.Autor,
    l.Precio
FROM Proveedor p
JOIN Proveedor_Libro pl ON p.Doc_Proveedor = pl.Doc_Proveedor
JOIN Libro l ON pl.Cod_Libro = l.Cod_Libro;
GO

-- Vista completa de facturas con toda la información
CREATE VIEW vw_Facturas_Completas AS
SELECT
    f.Cod_Fact,
    f.Fecha_Emision,
    f.Doc_Cliente,
    c.Nom_Cliente,
    c.Direc_Cliente,
    c.Telef_Cliente,
    ci.Nombre_Ciudad AS Ciudad_Cliente,
    p.Nombre_Pais AS Pais_Cliente,
    df.Cod_Libro,
    l.Titulo AS Titulo_Libro,
    l.Descripcion AS Descripcion_Libro,
    l.Autor AS Autor_Libro,
    cat.Nombre_Categoria AS Categoria_Libro,
    um.Nombre_Unidad AS Unidad_Medida,
    df.Cantidad,
    df.Precio_Unitario,
    (df.Cantidad * df.Precio_Unitario) AS Subtotal_Linea,
    f.Subtotal,
    f.Valor_Venta,
    f.IGV,
    f.Importe_Total,
    f.Orden_Compra,
    e.Cod_Empleado,
    e.Nom_Empleado AS Empleado_Responsable,
    a.Nombre_Area AS Area_Empleado
FROM Factura f
JOIN Cliente c ON f.Doc_Cliente = c.Doc_Cliente
JOIN Ciudad ci ON c.Cod_Ciudad = ci.Cod_Ciudad
JOIN Pais p ON ci.Cod_Pais = p.Cod_Pais
JOIN Detalle_Fact df ON f.Cod_Fact = df.Cod_Fact
JOIN Libro l ON df.Cod_Libro = l.Cod_Libro
JOIN Categoria cat ON l.Cod_Categoria = cat.Cod_Categoria
JOIN Unidad_Medida um ON l.Cod_UnidadMedida = um.Cod_UnidadMedida
LEFT JOIN Empleado e ON f.Cod_Empleado = e.Cod_Empleado
LEFT JOIN Area a ON e.Cod_Area = a.Cod_Area;
GO

-- Vista completa de pedidos con toda la información
CREATE VIEW vw_Pedidos_Completos AS
SELECT
    p.Cod_Pedido,
    p.Fecha,
    p.Doc_Cliente,
    c.Nom_Cliente,
    c.Direc_Cliente,
    c.Telef_Cliente,
    ci.Nombre_Ciudad AS Ciudad_Cliente,
    dp.Cod_Libro,
    l.Titulo AS Titulo_Libro,
    l.Descripcion AS Descripcion_Libro,
    l.Autor,
    cat.Nombre_Categoria,
    um.Nombre_Unidad,
    dp.Cantidad,
    dp.Precio_Unitario,
    (dp.Cantidad * dp.Precio_Unitario) AS Total_Linea,
    e.Cod_Empleado,
    e.Nom_Empleado AS Empleado_Responsable,
    a.Nombre_Area AS Area_Empleado
FROM Pedido p
JOIN Cliente c ON p.Doc_Cliente = c.Doc_Cliente
JOIN Ciudad ci ON c.Cod_Ciudad = ci.Cod_Ciudad
JOIN Detalle_Pedido dp ON p.Cod_Pedido = dp.Cod_Pedido
JOIN Libro l ON dp.Cod_Libro = l.Cod_Libro
JOIN Categoria cat ON l.Cod_Categoria = cat.Cod_Categoria
JOIN Unidad_Medida um ON l.Cod_UnidadMedida = um.Cod_UnidadMedida
LEFT JOIN Empleado e ON p.Cod_Empleado = e.Cod_Empleado
LEFT JOIN Area a ON e.Cod_Area = a.Cod_Area;
GO

-- Vista completa de reclamos con toda la información
CREATE VIEW vw_Reclamos_Completos AS
SELECT
    r.Codigo_HReclamo,
    r.Fecha_HReclamo,
    r.Doc_Proveedor,
    pr.Nom_Proveedor,
    pr.Direccion AS Direccion_Proveedor,
    pr.Telefono AS Telefono_Proveedor,
    pr.Email AS Email_Proveedor,
    r.Doc_Reclamante,
    c.Nom_Cliente,
    c.Direc_Cliente,
    c.Telef_Cliente,
    r.CodProdOServi,
    l.Titulo AS Producto_Servicio,
    l.Descripcion AS Descripcion_Producto,
    r.Monto,
    r.Detalle,
    tr.Cod_Reclamacion,
    tr.Nom_Reclamacion,
    tr.Descripcion AS Descripcion_Reclamo,
    r.Fecha_Respuesta
FROM Reclamo r
JOIN Proveedor pr ON r.Doc_Proveedor = pr.Doc_Proveedor
JOIN Cliente c ON r.Doc_Reclamante = c.Doc_Cliente
JOIN Libro l ON r.CodProdOServi = l.Cod_Libro
JOIN TipoReclamo tr ON r.Cod_Reclamacion = tr.Cod_Reclamacion;
GO

-- PROCEDIMIENTOS ALMACENADOS COMPLETOS
-- Procedimiento para obtener información completa de un cliente
CREATE PROCEDURE sp_GetClienteCompleto @Doc_Cliente NVARCHAR(20)
AS
BEGIN
    SELECT * FROM vw_Clientes_Completos WHERE Doc_Cliente = @Doc_Cliente;
END;
GO

-- Procedimiento para obtener información completa de un empleado
CREATE PROCEDURE sp_GetEmpleadoCompleto @Cod_Empleado INT
AS
BEGIN
    SELECT * FROM vw_Empleados_Completos WHERE Cod_Empleado = @Cod_Empleado;
END;
GO

-- Procedimiento para obtener información completa de un proveedor
CREATE PROCEDURE sp_GetProveedorCompleto @Doc_Proveedor NVARCHAR(20)
AS
BEGIN
    SELECT * FROM vw_Proveedores_Completos WHERE Doc_Proveedor = @Doc_Proveedor;
END;
GO

-- Procedimiento para obtener información completa de un libro
CREATE PROCEDURE sp_GetLibroCompleto @Cod_Libro NVARCHAR(20)
AS
BEGIN
    SELECT * FROM vw_Libros_Completos WHERE Cod_Libro = @Cod_Libro;
END;
GO

-- Procedimiento para obtener información completa de una factura
CREATE PROCEDURE sp_GetFacturaCompleta @Cod_Fact INT
AS
BEGIN
    SELECT * FROM vw_Facturas_Completas WHERE Cod_Fact = @Cod_Fact;
END;
GO

-- Procedimiento para obtener información completa de un pedido
CREATE PROCEDURE sp_GetPedidoCompleto @Cod_Pedido INT
AS
BEGIN
    SELECT * FROM vw_Pedidos_Completos WHERE Cod_Pedido = @Cod_Pedido;
END;
GO

-- Procedimiento para obtener información completa de un reclamo
CREATE PROCEDURE sp_GetReclamoCompleto @Codigo_HReclamo INT
AS
BEGIN
    SELECT * FROM vw_Reclamos_Completos WHERE Codigo_HReclamo = @Codigo_HReclamo;
END;
GO

-- Procedimiento para obtener todos los clientes
CREATE PROCEDURE sp_GetAllClientes
AS
BEGIN
    SELECT * FROM vw_Clientes_Completos ORDER BY Nom_Cliente;
END;
GO

-- Procedimiento para obtener todos los empleados
CREATE PROCEDURE sp_GetAllEmpleados
AS
BEGIN
    SELECT * FROM vw_Empleados_Completos ORDER BY Nom_Empleado;
END;
GO

-- Procedimiento para obtener todos los proveedores
CREATE PROCEDURE sp_GetAllProveedores
AS
BEGIN
    SELECT * FROM vw_Proveedores_Completos ORDER BY Nom_Proveedor;
END;
GO

-- Procedimiento para obtener todos los libros
CREATE PROCEDURE sp_GetAllLibros
AS
BEGIN
    SELECT * FROM vw_Libros_Completos ORDER BY Titulo;
END;
GO

-- Procedimiento para obtener los libros de un proveedor específico
CREATE PROCEDURE sp_GetLibrosPorProveedor @Doc_Proveedor NVARCHAR(20)
AS
BEGIN
    SELECT * FROM vw_Proveedores_Libros WHERE Doc_Proveedor = @Doc_Proveedor ORDER BY Titulo;
END;
GO

-- CONSULTAS DE EJEMPLO CON TODA LA INFORMACIÓN
-- Consulta 1: Información completa de un cliente específico
EXEC sp_GetClienteCompleto '20260100808';
-- Consulta 2: Información completa de todos los empleados
EXEC sp_GetAllEmpleados;
-- Consulta 3: Información completa de un proveedor específico
EXEC sp_GetProveedorCompleto '20666677777';
-- Consulta 4: Información completa de un libro específico
EXEC sp_GetLibroCompleto 'TXM2568';
-- Consulta 5: Información completa de una factura
EXEC sp_GetFacturaCompleta 1;
-- Consulta 6: Información completa de un pedido
EXEC sp_GetPedidoCompleto 1001;
-- Consulta 7: Información completa de un reclamo
EXEC sp_GetReclamoCompleto 1;
-- Consulta 8: Todos los libros con información completa
EXEC sp_GetAllLibros;
-- Consulta 9: Libros por proveedor
EXEC sp_GetLibrosPorProveedor '20666677777';
-- Consulta 10: Vista general de todas las facturas
SELECT
    Cod_Fact,
    Fecha_Emision,
    Nom_Cliente,
    Ciudad_Cliente,
    COUNT(Cod_Libro) AS Cantidad_Productos,
    Importe_Total
FROM vw_Facturas_Completas
GROUP BY Cod_Fact, Fecha_Emision, Nom_Cliente, Ciudad_Cliente, Importe_Total
ORDER BY Fecha_Emision DESC;
-- Consulta 11: Resumen de pedidos por cliente
SELECT
    Doc_Cliente,
    Nom_Cliente,
    COUNT(DISTINCT Cod_Pedido) AS Cantidad_Pedidos,
    SUM(Total_Linea) AS Total_Comprado
FROM vw_Pedidos_Completos
GROUP BY Doc_Cliente, Nom_Cliente
ORDER BY Total_Comprado DESC;

-- CONSULTAS ADICIONALES PARA REPORTES
-- Top 5 libros más vendidos
SELECT TOP 5
    Cod_Libro,
    Titulo_Libro,
    SUM(Cantidad) AS Total_Vendido,
    SUM(Total_Linea) AS Ingresos_Generados
FROM vw_Pedidos_Completos
GROUP BY Cod_Libro, Titulo_Libro
ORDER BY Total_Vendido DESC;

-- Ventas por categoría de libro
SELECT
    Nombre_Categoria,
    COUNT(DISTINCT Cod_Pedido) AS Cantidad_Pedidos,
    SUM(Cantidad) AS Total_Libros,
    SUM(Total_Linea) AS Total_Ventas
FROM vw_Pedidos_Completos
GROUP BY Nombre_Categoria
ORDER BY Total_Ventas DESC;

-- Clientes por ciudad
SELECT
    Ciudad_Cliente,
    COUNT(DISTINCT Doc_Cliente) AS Cantidad_Clientes,
    COUNT(DISTINCT Cod_Pedido) AS Cantidad_Pedidos
FROM vw_Pedidos_Completos
GROUP BY Ciudad_Cliente
ORDER BY Cantidad_Clientes DESC;

-- Resumen de reclamos por tipo
SELECT
    Nom_Reclamacion,
    COUNT(*) AS Cantidad_Reclamos,
    AVG(Monto) AS Monto_Promedio
FROM vw_Reclamos_Completos
GROUP BY Nom_Reclamacion
ORDER BY Cantidad_Reclamos DESC;

-- Proveedores y la cantidad de libros que ofrecen
SELECT
    p.Doc_Proveedor,
    p.Nom_Proveedor,
    COUNT(pl.Cod_Libro) AS Cantidad_Libros
FROM Proveedor p
LEFT JOIN Proveedor_Libro pl ON p.Doc_Proveedor = pl.Doc_Proveedor
GROUP BY p.Doc_Proveedor, p.Nom_Proveedor
ORDER BY Cantidad_Libros DESC;

PRINT 'Base de datos LIBROEXPRESS creada exitosamente con todas las tablas, vistas y procedimientos.';