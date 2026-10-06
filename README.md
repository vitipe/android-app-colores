# Wada Sanzo Colors - A Dictionary of Color Combinations

Aplicación nativa para Android desarrollada en **Kotlin** con **Jetpack Compose** y **Material 3**, basada en la histórica obra de **Sanzo Wada** (1883–1967): *"A Dictionary of Color Combinations"* (inspirada en la experiencia interactiva de [sanzo-wada.dmbk.io](https://sanzo-wada.dmbk.io)).

---

## ✨ Características Principales

- **Catálogo Completo de Colores (159 colores)**:
  - Visualización en **cuadrícula** o **lista** con muestras de color de alta fidelidad.
  - Búsqueda en tiempo real por nombre de color o código hexadecimal (`#f9c1ce`, `Burnt Sienna`, etc.).
  - Filtro por tomo/capítulo del libro original (*Swatch Books 1 al 6*).
  - Indicador de cantidad de combinaciones históricas asociadas a cada color.

- **Ficha de Detalle de Color**:
  - Muestra ampliada (*Hero swatch*) con cálculo automático de contraste para el texto.
  - Códigos de color completos: **HEX**, **RGB**, **CMYK** y **LAB**.
  - **Copiado al portapapeles en un toque** con confirmación instantánea en pantalla.
  - **Lista de todas las combinaciones que usan ese color**, con navegación rápida a cualquiera de los colores acompañantes.

- **Explorador de Combinaciones (348 combinaciones)**:
  - Las 348 paletas curadas por Sanzo Wada: **120 de 2 colores**, **120 de 3 colores** y **108 de 4 colores**.
  - Filtro por número de colores (Dúos, Tríos y Cuartetos).
  - Buscador por número de combinación (`#176`) o color integrante.
  - Botón para **copiar todos los códigos HEX de la paleta** en un solo toque.

- **Favoritos / Guardados**:
  - Guarda tus colores y combinaciones preferidas en pestañas separadas, con persistencia local mediante Jetpack DataStore.

- **Diseño Editorial Japonés Minimalista**:
  - Tipografía clara y diseño limpio con soporte para Tema Claro y Oscuro.

---

## 🛠️ Tecnologías y Arquitectura

- **Lenguaje**: Kotlin 2.0
- **UI Toolkit**: Jetpack Compose + Material 3
- **Navegación**: Jetpack Navigation Compose
- **Persistencia**: Jetpack DataStore Preferences
- **Build System**: Gradle 8.7 con Kotlin DSL (`build.gradle.kts`) y Version Catalog (`libs.versions.toml`)
- **Compatibilidad**: Android 7.0 (API 24 / Nougat) hasta Android 15 (API 35)

---

## 📂 Estructura del Proyecto

```
app-colores/
├── build.gradle.kts                      # Configuración de build raíz
├── settings.gradle.kts                   # Módulos y repositorios
├── gradle.properties                     # Configuración de la JVM de Gradle
├── gradlew / gradlew.bat                 # Wrapper de Gradle
├── gradle/
│   ├── libs.versions.toml                # Catálogo de versiones y dependencias
│   └── wrapper/
│       ├── gradle-wrapper.properties
│       └── gradle-wrapper.jar
├── README.md                             # Esta documentación
└── app/
    ├── build.gradle.kts                  # Configuración del módulo de la app
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml           # Manifiesto de Android
        ├── assets/
        │   └── sanzo_colors.json         # Dataset completo en JSON
        ├── res/
        │   ├── values/                   # Strings, colores, temas
        │   ├── drawable/                 # Iconos vectoriales de la app
        │   └── mipmap-anydpi-v26/        # Icono adaptativo
        └── java/com/wadasanzo/colors/
            ├── MainActivity.kt           # Activity principal y navegación inferior
            ├── data/
            │   ├── SanzoData.kt          # Dataset Kotlin precompilado (159 colores / 348 combinaciones)
            │   ├── model/
            │   │   ├── SanzoColor.kt     # Modelo de Color (HEX, RGB, CMYK, LAB, contrastes)
            │   │   └── ColorCombination.kt # Modelo de Combinación de colores
            │   └── repository/
            │       └── ColorRepository.kt # Búsqueda, filtros y favoritos
            └── ui/
                ├── theme/                # Color, Theme, Typography
                ├── navigation/           # Screen.kt, NavGraph.kt
                ├── components/           # ColorSwatchCard, CombinationCard, ColorValuesCard, FilterChips, SearchBar
                └── screens/              # ColorsScreen, ColorDetailScreen, CombinationsScreen, CombinationDetailScreen, FavoritesScreen
```

---

## 🚀 Cómo abrir, compilar y generar el APK

### 1. Abrir en Android Studio
1. Inicia **Android Studio**.
2. Selecciona **Open** (o ve a `File > Open...`).
3. Selecciona la carpeta del proyecto:
   ```
   /Users/vhpenas/Desktop/codigo/app-colores
   ```
4. Espera a que Gradle termine de descargar las dependencias y sincronizar el proyecto (*Gradle Sync*).

### 2. Ejecutar directamente en tu teléfono
1. Conecta tu teléfono Android a la computadora mediante cable USB (o vía Wi-Fi en *Pair Devices Using Wi-Fi*).
2. Asegúrate de tener activada la opción **Depuración por USB** (*USB Debugging*) en los *Ajustes de desarrollador* de tu teléfono.
3. En la barra superior de Android Studio, selecciona tu dispositivo móvil en la lista de dispositivos.
4. Presiona el botón verde de **Run (▶)** (o presiona `Control + R` en Mac / `Shift + F10` en Windows/Linux).
5. La aplicación se compilará, se instalará y se abrirá automáticamente en tu teléfono.

### 3. Generar el archivo APK para instalar o compartir
1. En la barra de menú superior de Android Studio, ve a:
   ```
   Build > Build Bundle(s) / APK(s) > Build APK(s)
   ```
2. Cuando finalice la compilación, aparecerá una notificación emergente en la esquina inferior derecha:
   `APK(s) generated successfully for 1 module`.
3. Haz clic en **locate** en esa notificación para abrir la carpeta que contiene el archivo:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```
4. Puedes enviar este archivo `.apk` a tu teléfono (por WhatsApp, Telegram, Google Drive, AirDrop, etc.) e instalarlo directamente activando "Instalar aplicaciones de fuentes desconocidas", o instalarlo por terminal con:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

---

## 📚 Créditos y Referencias

- **Sanzo Wada** (1883–1967): Pintor, diseñador de vestuario (ganador del Oscar en 1954) e investigador pionero de combinaciones de color en Japón.
- **A Dictionary of Color Combinations**: Publicado originalmente por Seigensha Art.
- **Dataset**: Basado en las recopilaciones y conversiones de color de la comunidad open source (*mattdesl* y *dblodorn*).
