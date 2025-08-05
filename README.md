# Descripción

Esta repositorio es una práctica de la asignatura programación orientada a objetos 2 en java. Consiste en una aplicación de escritorio desarrollada para gestionar una biblioteca de imágenes. Permite crear carpetas y archivos de imagen con formas aleatorias, insertar y visualizar metadatos de manera automática, también filtra imágenes según ciertos criterios, todo ello a través de una interfaz gráfica sencilla.

### Requisitos Previos

- Tener Java instalado (JRE o JDK 8 o superior).
- Descargar el archivo ejecutable `VisorImagenes.jar`.
  
  ```bash
  git clone https://github.com/jrgim/Biblioteca-de-imagenes-con-metadatos.git
  ```

### Ejecución

#### 1. Usando el archivo `VisorImagenes.jar` (más sencillo):

1. Coloca el archivo `VisorImagenes.jar` en la carpeta que desees.
2. Abre una terminal o consola de comandos en esa carpeta.
3. Ejecuta el siguiente comando:

```bash
java -jar VisorImagenes.jar
```


4. La aplicación arrancará y podrás comenzar a gestionar tu biblioteca de imágenes.

#### 2. Compilando desde código fuente (opcional):

1. Asegúrate de tener Maven instalado.
2. Sitúate en la raíz del proyecto (donde está el archivo `pom.xml`).
3. Ejecuta:

```bash
mvn clean package
```

Esto generará en la carpeta `target` un archivo `.jar` ejecutable (por ejemplo, `app.jar`).
4. Para ejecutarlo:

```bash
java -jar target/app.jar
```

*(Reemplaza `app.jar` por el nombre generado)*

### Instrucciones de Uso Básico

1. **Crear Carpeta de Imágenes:**
Usa el botón para crear la estructura de carpetas e imágenes. Responde a las preguntas para definir el número de imágenes.
2. **Seleccionar Carpeta:**
Puedes cambiar la ubicación de la biblioteca usando el botón "Seleccionar carpeta".
3. **Añadir Metadatos:**
Haz clic en "Añadir Metadatos" para generar metadatos aleatorios en las imágenes.
4. **Filtrado y Visualización:**
Filtra imágenes según los criterios disponibles y visualiza sólo las imágenes filtradas.

### Notas

- La ruta por defecto para las imágenes es `src/files`, pero se puede cambiar en cualquier momento.
- Los metadatos se generan aleatoriamente y pueden visualizarse al pulsar sobre cada imagen.

### 📋 Documentación
[📖 Descargar Manual de Uso](./Manual de uso.pdf)
