# 📱 TodoList - Android App

Una aplicación de lista de tareas desarrollada en Android Studio con Kotlin que te permite gestionar tus tareas diarias de manera eficiente.

## 🚀 Características

- ✅ **Gestión de Tareas**: Crear, editar y eliminar tareas
- 📅 **Fechas y Recordatorios**: Establecer fechas específicas para las tareas
- 🔔 **Sistema de Alarmas**: Configurar alertas personalizadas
- 🔄 **Opciones de Repetición**: 
  - Nunca
  - Una vez
  - Diario
  - Cada 3 días
  - Semanalmente
  - Quincenalmente
  - Mensualmente
- 📱 **Interfaz Intuitiva**: Diseño moderno y fácil de usar
- 🎨 **Tema Personalizable**: Soporte para modo claro y oscuro

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **IDE**: Android Studio
- **SDK Mínimo**: API 24 (Android 7.0)
- **SDK Objetivo**: API 33 (Android 13)
- **Arquitectura**: ViewBinding
- **Dependencias Principales**:
  - `androidx.core:core-ktx:1.9.0`
  - `androidx.appcompat:appcompat:1.7.0`
  - `com.google.android.material:material:1.12.0`
  - `androidx.constraintlayout:constraintlayout:2.1.4`

## 📋 Requisitos Previos

- Android Studio Arctic Fox o superior
- JDK 8 o superior
- Android SDK API 24+
- Dispositivo Android con API 24+ o emulador

## 🔧 Instalación

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/jorgeMDK/TodoList
   cd TodoList
   ```

2. **Abre el proyecto en Android Studio**:
   - Abre Android Studio
   - Selecciona "Open an existing Android Studio project"
   - Navega hasta la carpeta del proyecto y selecciónala

3. **Sincroniza el proyecto**:
   - Android Studio descargará automáticamente las dependencias de Gradle
   - Espera a que se complete la sincronización

4. **Ejecuta la aplicación**:
   - Conecta un dispositivo Android o inicia un emulador
   - Presiona el botón "Run" (▶️) en Android Studio
   - Selecciona tu dispositivo/emulador y presiona "OK"

## 📖 Uso de la Aplicación

### Crear una Nueva Tarea
1. Toca el botón flotante "+" en la pantalla principal
2. Completa el título y descripción de la tarea
3. Configura las opciones de repetición si es necesario
4. Establece una fecha usando el calendario
5. Activa la alarma si deseas recibir recordatorios
6. Presiona "Agregar" para guardar la tarea

### Editar una Tarea
1. Mantén presionada la tarea que deseas editar
2. Modifica los campos necesarios
3. Presiona "Guardar" para aplicar los cambios

### Eliminar Tareas
1. Selecciona las tareas que deseas eliminar
2. Presiona el botón de eliminar (🗑️)
3. Confirma la eliminación

## 🏗️ Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/todolist/
│   │   ├── adapter/
│   │   │   ├── ListsAdapter.kt
│   │   │   └── ListViewHolder.kt
│   │   ├── MainActivity.kt
│   │   ├── AddTask.kt
│   │   ├── Task.kt
│   │   └── TaskProvider.kt
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── add_task.xml
│   │   │   ├── edit_task.xml
│   │   │   └── item_task.xml
│   │   ├── values/
│   │   │   ├── strings.xml
│   │   │   ├── colors.xml
│   │   │   └── themes.xml
│   │   └── drawable/
│   └── AndroidManifest.xml
└── build.gradle.kts
```


## 👨‍💻 Autor

**Jorge Medel**
- GitHub: [@jorgeMDK](https://github.com/jorgeMDK)

---

⭐ Si te gusta este proyecto, ¡dale una estrella en GitHub! 