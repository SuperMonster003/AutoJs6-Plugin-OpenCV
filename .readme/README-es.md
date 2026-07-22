<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>Plugin del entorno de ejecución nativo de OpenCV 4.8.0 para AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
    <br>
    <a href="https://developer.android.com/studio/archive"><img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-2023.3+-B64FC8"/></a>
    <a href="https://www.jetbrains.com/idea/download/other.html"><img alt="IntelliJ IDEA" src="https://img.shields.io/badge/IntelliJ%20IDEA-2023.3+-EE4677"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-OpenCV?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Idiomas (Languages)

******

El README.md actual admite los siguientes idiomas:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- Español [es] # actual
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### Introducción

******

El plugin AutoJs6 OpenCV proporciona el entorno de ejecución nativo de OpenCV 4.8.0 que utilizan las API de imágenes del host. En este diseño centrado en la compatibilidad, AutoJs6 conserva la API Java de OpenCV, mientras que el plugin proporciona `libopencv_java4.so` para cada ABI compatible.

******

### Contrato del plugin

******

- ID de la aplicación: `io.github.supermonster003.autojs6.plugin.opencv`
- ID del plugin: `opencv`
- Motor: `opencv`
- Variante: `4.8.0`
- Versión del contrato: `2`
- Código de versión requerido del host: `5237`
- Biblioteca nativa: `opencv_java4`
- Versión del NDK nativo: `26.1.10909125`
- SHA-256 de la API Java: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

El mismo servicio `OpenCvPluginInfoService` responde a `org.autojs.plugin.INFO` y a `org.autojs.plugin.OPENCV`.

Ambas acciones utilizan la categoría `opencv-runtime`. Su interfaz Binder es `IPluginInfoProvider`, proporcionada por `common-plugin-api`.

******

### ABI

******

El proyecto genera las siguientes variantes de APK:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` se obtiene de las entradas de la biblioteca nativa de OpenCV en el APK instalado. Un APK de una sola ABI informa únicamente de su propia ABI, mientras que el APK `universal` informa de las cuatro.

Instale el APK `universal` del plugin cuando APK Builder deba producir aplicaciones para varias ABI. Un plugin de una sola ABI solo puede proporcionar OpenCV para esa ABI.

******

### Compilación y verificación

******

Ejecute las pruebas unitarias y verifique los APK debug y release:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

Para compilar únicamente los APK debug:

```powershell
.\gradlew.bat :app:assembleDebug
```

Antes de publicar, configure una identidad de firma de confianza en el archivo `sign.properties`, ignorado por Git, y ejecute:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Sin `sign.properties`, las cargas útiles aún pueden compilarse y verificarse, pero los APK de publicación generados no están firmados y no deben publicarse.

******

### Comportamiento en tiempo de ejecución

******

El plugin no dispone de una interfaz de usuario independiente. Una vez instalado y habilitado, AutoJs6 detecta su servicio y carga la biblioteca nativa que coincide con la API Java conservada. Una biblioteca nativa cargada normalmente comparte el ciclo de vida del proceso host, por lo que debe reiniciar AutoJs6 después de actualizar el plugin.

El plugin contiene únicamente `libopencv_java4.so` y no incluye `libc++_shared.so`. Un host AutoJs6 compatible debe proporcionar y cargar previamente esta dependencia para todo el proceso, de modo que un proceso utilice un único entorno de ejecución de C++.

Instale únicamente compilaciones de confianza. Antes de cargar la biblioteca nativa, el host verifica la firma del plugin, la versión de OpenCV, la versión del contrato y el SHA-256 de la API Java. Las versiones oficiales deben utilizar una identidad de firma reconocida por el host.

******

### Historial de versiones

******

# v1.0.0

###### 2026/07/22

* `Función` Se publicó el plugin del entorno de ejecución nativo de OpenCV 4.8.0 para AutoJs6: el host conserva la API Java de OpenCV mientras el plugin proporciona `libopencv_java4.so`
* `Función` Se añadió detección mediante `org.autojs.plugin.INFO` y `org.autojs.plugin.OPENCV` con la categoría `opencv-runtime`, junto con los metadatos de compatibilidad requeridos por el host
* `Función` Se añadieron los APK `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` y `universal`, con información dinámica de las ABI realmente incluidas
* `Función` Se localizaron los metadatos del plugin, las instrucciones, el README y el CHANGELOG en español, francés, ruso, árabe, japonés, coreano, inglés, chino simplificado, chino tradicional de Hong Kong y chino tradicional de Taiwán
* `Función` Se añadieron comprobaciones de integridad debug y release para el inventario nativo, la arquitectura ELF, los hashes de las cargas útiles, la huella de la API Java, las clases Java de OpenCV duplicadas y los archivos de licencia; la verificación publicable también exige una firma configurada
* `Función` Cada APK incluye los textos completos de las licencias de OpenCV 4.8.0 y de los componentes de terceros estáticos; el plugin no incluye `libc++_shared.so`, que un host AutoJs6 compatible debe proporcionar y cargar previamente
* `Corrección` Se reconstruyeron las bibliotecas nativas de OpenCV 4.8.0 con Android NDK 26 y API 24 para impedir que las excepciones crucen los límites de entornos de ejecución C++ incompatibles y provoquen el cierre inesperado del host AutoJs6

##### Para ver más historial de versiones

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-es.md)

******

### Estructura de recursos

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` contiene las descripciones localizadas del plugin, y `plugin_instruction.md` contiene las instrucciones que muestra el host. README y CHANGELOG se generan a partir de fuentes JSON mediante `.python/generate_markdown.py`; los changelogs localizados completos se incluyen bajo `app/src/main/assets/doc`.

******

### Licencia

******

El código del proyecto se distribuye bajo la [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). Consulte [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) para conocer las licencias de OpenCV 4.8.0 y sus componentes de terceros enlazados estáticamente. Los textos completos de las licencias se incluyen en cada APK bajo `assets/licenses/opencv-4.8.0/`.

******

### Enlaces

******

- Documentación de AutoJs6: https://docs.autojs6.com/
- Sitio web oficial de OpenCV: https://opencv.org/
- Código fuente de OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
