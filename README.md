# 🕹️ Akihabara Market Inventory System

Proyecto de prácticas realizado por **Ian Fernández Gamo** como parte del curso de Desarrollo de Aplicaciones Multiplataforma (DAM) en Campus FP Humanes de Madrid.

Este sistema moderniza la gestión de inventario y clientes de una tienda otaku ficticia, integrando Java, Swing para la GUI, MySQL para la persistencia de datos y un modelo de LLM para generar sugerencias y descripciones.

## 📁 Estructura del Proyecto

```
src/
├── modelo/
│   ├── DatabaseConnection.java
│   ├── ProductoOtaku.java
│   ├── ProductoDAO.java
│   ├── ClienteOtaku.java
│   ├── ClienteDAO.java
│   ├── SetupDatos.java
│   └── LlmService.java
├── vista/
│   ├── InterfazConsola.java
│   ├── PanelPrincipal.java
│   ├── PanelProductos.java
│   ├── PanelClientes.java
│   ├── MainGUI.java
│   └── Main.java
├── controlador/
│   ├── MainApp.java
│   ├── ClientesController.java
│   └── ProductosController.java
```

## 🚀 Características Principales

- CRUD completo de productos y clientes
- Dos interfaces: consola y gráfica (Swing)
- Conexión a base de datos MySQL
- Integración con un LLM para:
  - Generar descripciones automáticas de productos
  - Sugerencias de categoría
- Validaciones robustas y manejo de errores
- Carga automática de productos de ejemplo

## 🖥️ Tecnologías Utilizadas

- Java 17+
- Swing
- MySQL
- JDBC
- OpenAI LLM (a través de API)
- Eclipse IDE

## 🧪 Funcionalidades

### Productos
- Agregar, eliminar, actualizar y buscar productos
- Interfaz gráfica con tabla interactiva
- Validaciones de datos (precio, stock, ID)

### Clientes
- Agregar, eliminar, actualizar y buscar clientes
- Validación de fechas, email y teléfono
- CRUD con interfaz Swing

### IA (LLM)
- Descripción de productos automatizada por ID
- Sugerencias de categorías según el nombre del producto

## 🖼️ Interfaz Gráfica

- Vista principal con menú de navegación
- Formularios de entrada intuitivos
- Tabla visual no editable
- Confirmaciones y validaciones con `JOptionPane`

## ⚙️ Instalación y Ejecución

1. Clona este repositorio:

```bash
git clone https://github.com/Iann-frnndz/RetoPracticas_1eroDAM_IanFernandezGamo.git
```

2. Importa el proyecto en **Eclipse** como proyecto Java.
3. Configura tu conexión MySQL en `DatabaseConnection.java`.
4. Agrega tu `API_KEY` y el modelo LLM en `config.properties`.
5. Ejecuta desde:
   - `Main.java` para consola
   - `MainGUI.java` para GUI Swing

## 🧾 Pruebas y Validaciones

- Manejo de entradas inválidas (números negativos, strings no válidos)
- Errores de conexión o ID inexistente
- Confirmaciones al eliminar productos/clientes
- Comprobación de formato de fechas

## 📚 Bibliografía

- [Swing - JFrame](https://www.tutorialesprogramacionya.com/javaya/detalleconcepto.php?codigo=104)
- [W3Schools Java](https://www.w3schools.com/java/)
- [YouTube tutorial de referencia](https://youtu.be/ycY0W52eDUc?si=wQ3L1jFQvFdw9hz5)

---

**Fecha:** Junio 2025  
**Autor:** Ian Fernández Gamo  
📍 Campus FP Humanes de Madrid
