<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>Plugin del entorno de ejecución nativo de OpenCV 4.8.0 para AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

El plugin OpenCV (OpenCV Plugin) proporciona el entorno de ejecución nativo de OpenCV 4.8.0 que impulsa el procesamiento de imágenes en AutoJs6. Las API de imágenes como la búsqueda de imágenes, la detección de colores y la coincidencia de plantillas dependen todas de OpenCV para sus cálculos; una vez instalado este plugin, un host AutoJs6 compatible con OpenCV en forma de plugin puede usar estas funciones de imagen con normalidad, sin ninguna configuración adicional en los scripts.

El plugin sigue un reparto de tareas que prioriza la compatibilidad: el host AutoJs6 conserva la API Java de OpenCV que los scripts llaman directamente, mientras que el plugin incorpora la biblioteca nativa `libopencv_java4.so` que coincide exactamente con ella. Así el paquete del host se mantiene ligero, cada dispositivo instala solo el paquete del plugin que corresponde a la arquitectura de su procesador (ABI) y el entorno de ejecución de OpenCV puede actualizarse con independencia del host.

******

### Funciones destacadas

******

- Funciona desde el primer momento: no requiere configuración; AutoJs6 descubre el plugin automáticamente y carga el entorno de ejecución de OpenCV bajo demanda cuando se ejecutan scripts de imágenes.
- Cinco variantes de APK: cuatro paquetes de una sola ABI (`arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`) más un paquete `universal` todo en uno, de modo que cada dispositivo instala solo lo que necesita.
- Compilado desde las fuentes oficiales: las bibliotecas nativas se reconstruyen a partir del código fuente oficial de OpenCV 4.8.0 sin modificar (NDK r26b, API 24), con las entradas de compilación y los hashes por ABI registrados en un manifiesto de provenance que permite una reproducción independiente.
- Verificado antes de cargar: el host comprueba la firma del plugin, la versión de OpenCV, la versión del contrato y la huella de la API Java, y rechaza los entornos de ejecución discordantes o manipulados.
- Un único entorno de ejecución de C++ por proceso: el plugin no duplica `libc++_shared.so` y comparte la dependencia de todo el proceso proporcionada por el host, lo que evita cierres inesperados causados por la coexistencia de varios entornos de ejecución de C++.
- Licencias transparentes: cada APK incluye los textos completos de las licencias de OpenCV y de sus componentes de terceros enlazados estáticamente, resumidos en THIRD_PARTY_NOTICES.md.
- Multilingüe: metadatos del plugin, instrucciones, README y changelog disponibles en 10 idiomas.

******

### Cómo se usa

******

1. Descargue desde la página [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) el APK del plugin que corresponda al dispositivo e instálelo en el dispositivo que ejecuta AutoJs6; en caso de duda, elija el paquete `universal` o consulte `Cómo elegir un APK` más abajo.
2. Abra el centro de plugins de AutoJs6 y confirme que el plugin `OpenCV` está reconocido y habilitado.
3. Escriba y ejecute sus scripts como de costumbre: cuando un script usa las API de imágenes, AutoJs6 carga automáticamente la biblioteca nativa de OpenCV proporcionada por el plugin; el código de los scripts no necesita ningún cambio.
4. Después de actualizar o reinstalar el plugin, salga por completo de AutoJs6 y reinícielo antes de volver a ejecutar scripts de imágenes, para que la nueva biblioteca nativa surta efecto.

> Si el plugin no aparece en el centro de plugins, actualice primero AutoJs6 a una versión reciente (compilación interna 5237 o superior). El propio plugin es compatible con dispositivos con Android 7.0 (API 24) o superior.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="El centro de complementos de AutoJs6 reconoce OpenCV 1.1.0 y lo muestra habilitado." width="480" />
</p>
<p align="center"><sub>El centro de complementos de AutoJs6 reconoce OpenCV 1.1.0 y lo muestra habilitado.</sub></p>

******

### Cómo elegir un APK

******

Cada versión publicada incluye 5 APK que solo se diferencian en las arquitecturas de biblioteca nativa que incorporan:

| Paquete | Recomendado para |
|---|---|
| `arm64-v8a` | La gran mayoría de los teléfonos y tabletas Android modernos (ARM de 64 bits); la primera opción |
| `armeabi-v7a` | Dispositivos ARM de 32 bits más antiguos |
| `x86_64` | Emuladores x86 de 64 bits y unos pocos dispositivos x86 |
| `x86` | Emuladores x86 de 32 bits y unos pocos dispositivos x86 |
| `universal` | Incorpora las 4 arquitecturas y es el más grande; funciona en cualquier dispositivo y es la opción segura en caso de duda |

Instale el paquete `universal` del plugin cuando use el APK Builder de AutoJs6 para empaquetar aplicaciones destinadas a varias arquitecturas: un plugin de una sola ABI solo puede proporcionar OpenCV para su propia arquitectura. Si por error se instaló un paquete de una sola ABI que no corresponde al dispositivo, el plugin no podrá ofrecer una biblioteca nativa utilizable; cambiar al paquete `universal` lo resuelve.

******

### Autocomprobación rápida

******

Después de confirmar que OpenCV aparece habilitado en el centro de plugins de AutoJs6 y reiniciar AutoJs6, ejecute este script. `images.initOpenCvIfNeeded()` activa realmente la detección del plugin, las comprobaciones de compatibilidad y la carga nativa:

```javascript
images.initOpenCvIfNeeded();

const Build = android.os.Build;
const Process = android.os.Process;
const Core = org.opencv.core.Core;
const processAbis = Process.is64Bit()
    ? Build.SUPPORTED_64_BIT_ABIS
    : Build.SUPPORTED_32_BIT_ABIS;
const processAbi = processAbis.length > 0 ? processAbis[0] : "unknown";

console.log("OpenCV version: " + Core.getVersionString());
console.log("Process ABI: " + processAbi);
```

Si todo funciona, se muestran `OpenCV version: 4.8.0` y una ABI de proceso como `arm64-v8a`. Si la carga falla, compruebe en este orden: instale el paquete `universal`, actualice AutoJs6 a la compilación interna 5237 o posterior y, por último, cierre completamente y reinicie AutoJs6.

******

### Preguntas frecuentes

******

#### ¿Cómo puedo confirmar que el plugin funciona?

Abra el centro de plugins de AutoJs6; ver allí el plugin `OpenCV` significa que el host lo ha reconocido. Después ejecute cualquier script que use las API de imágenes; si los resultados llegan con normalidad, la biblioteca nativa se cargó correctamente.

#### ¿Por qué no hay un icono del plugin en la lista de aplicaciones?

Es lo esperado. El plugin no tiene interfaz propia ni crea un icono de inicio; tras la instalación, AutoJs6 lo descubre y lo controla por completo en segundo plano, y toda la interacción ocurre dentro de AutoJs6.

#### ¿Las funciones de imagen fallan tras actualizar el plugin, o parece que sigue activa la versión antigua?

Una vez cargada, una biblioteca nativa permanece viva mientras dure el proceso del host, por lo que actualizar el plugin no sustituye la biblioteca ya en uso. Salga por completo de AutoJs6 y reinícielo para que la nueva biblioteca nativa surta efecto.

#### ¿Qué hago si se indica que el host es demasiado antiguo o incompatible?

El plugin requiere una compilación interna de AutoJs6 igual o superior a 5237, así que actualice primero AutoJs6. Antes de cargar, el host verifica la versión del contrato y la huella de la API Java, y ante cualquier discrepancia se niega a cargar en lugar de funcionar con riesgos ocultos.

#### El plugin está instalado pero las funciones de imagen siguen fallando. ¿Cuál puede ser la causa?

La causa más común es un APK que no corresponde a la arquitectura del dispositivo: un paquete de una sola ABI solo funciona en su propia arquitectura. Cambie al paquete `universal` para descartarlo; si sigue fallando, confirme que la versión de AutoJs6 cumple el requisito y vuelva a intentarlo tras reiniciar AutoJs6.

#### ¿El plugin accede a la red o solicita permisos sensibles?

No. Su manifiesto no contiene permisos de red, almacenamiento, cámara ni otros permisos sensibles del sistema; solo declara el permiso de plugin usado para comunicarse con AutoJs6. Su única tarea es entregar la biblioteca nativa de OpenCV al host.

#### ¿Por qué OpenCV 4.8.0 y no una versión más reciente?

La biblioteca nativa debe coincidir exactamente con la API Java de OpenCV conservada por el host (verificado mediante una huella SHA-256), por lo que la versión de OpenCV queda fijada por el contrato entre host y plugin. Las versiones más recientes de OpenCV llegarán como nuevas variantes cuando el host las admita; consulte [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md) para seguir el progreso.

******

### Permisos y seguridad

******

El código nativo se ejecuta en el mismo proceso que el host, por lo que se aplican varias líneas de defensa desde la compilación hasta la carga:

- Origen auditable: las bibliotecas nativas se reconstruyen a partir de un commit fijado del código fuente oficial de OpenCV, con las versiones de las herramientas y los hashes por ABI registrados en `libs/opencv-native-4.8.0.provenance.json`; cualquiera puede reproducirlas y compararlas siguiendo [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).
- Controles de compilación: cada compilación verifica el inventario de bibliotecas nativas, las arquitecturas ELF, los hashes de las cargas útiles, la huella de la API Java, las clases Java de OpenCV duplicadas y los recursos de licencia; las compilaciones publicables exigen además una identidad de firma de confianza.
- Verificación previa a la carga: el host comprueba sucesivamente la firma del plugin, la versión de OpenCV, la versión del contrato y el SHA-256 de la API Java, y rechaza la carga ante cualquier discrepancia.
- El entorno de ejecución de C++ de todo el proceso lo proporciona y precarga el host; el plugin no incluye `libc++_shared.so`, lo que evita cierres inesperados en fronteras de entornos de ejecución incompatibles.
- Huella mínima: sin permisos de red ni permisos sensibles del sistema, sin interfaz propia, y comunicación con el host únicamente a través del permiso de plugin de AutoJs6.

Instale el plugin únicamente desde la página oficial [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) u otros canales de confianza. Los paquetes oficiales usan una identidad de firma reconocida por el host; los paquetes de origen desconocido pueden no superar la verificación del host u ocultar riesgos aunque el número de versión parezca idéntico.

******

### Interfaz del plugin

******

La siguiente información está dirigida a los desarrolladores del host AutoJs6 y de plugins; el host usa estos identificadores para descubrir el plugin y negociar la compatibilidad:

```text
application id: io.github.supermonster003.autojs6.plugin.opencv
plugin id: opencv
engine: opencv
variant: 4.8.0
contract version: 2
minimum host build: 5237
native library: libopencv_java4.so
native ndk version: 26.1.10909125
java api sha-256: 340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f
```

Un mismo servicio `OpenCvPluginInfoService` responde a las acciones `org.autojs.plugin.INFO` y `org.autojs.plugin.OPENCV`, cada una con la categoría `opencv-runtime`; la interfaz Binder es `IPluginInfoProvider`, proporcionada por common-plugin-api.

`PluginInfo.supportedAbis` se calcula dinámicamente a partir de las entradas de biblioteca nativa de OpenCV realmente presentes en el APK instalado: un paquete de una sola ABI informa solo de su propia arquitectura, mientras que el paquete `universal` informa de las 4.

******

### Hoja de ruta

******

Las capacidades previstas del plugin y su grado de avance se mantienen como una lista marcable en ROADMAP.md, organizada por hitos con criterios de aceptación, y abarcan la compatibilidad con páginas de memoria de 16 KB, la evolución de las versiones de OpenCV, la integración continua y el diagnóstico. Los elementos sin marcar describen intenciones, no capacidades ya publicadas; la discusión mediante Issues es bienvenida.

- [Ver ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### Historial de versiones

******

#### v1.1.0

_2026/08/31_

- `Función` Las descargas de la versión ahora reúnen los 5 APK de arquitectura, `SHA256SUMS.txt` y el manifiesto de procedencia de la compilación nativa para elegir el paquete correcto y verificar los archivos de forma independiente
- `Corrección` Se reconstruyeron las bibliotecas nativas de los 4 ABI con alineación `PT_LOAD` de 16 KB y se añadieron controles de publicación para que OpenCV cargue en dispositivos Android con páginas de 16 KB sin perder compatibilidad con dispositivos de 4 KB
- `Mejora` Las instrucciones del centro de complementos y el README en 10 idiomas ahora incluyen una captura real del estado habilitado y un script de autocomprobación que muestra la versión de OpenCV y el ABI del proceso
- `Mejora` La detección de ABI compatibles ahora cubre instalaciones universal, de un solo ABI y split, continúa ante rutas APK ausentes o dañadas y recurre a la biblioteca nativa extraída

#### v1.0.0

_2026/07/22_

- `Función` Primera versión oficial: proporciona el entorno de ejecución nativo de OpenCV 4.8.0 que respalda las API de imágenes de AutoJs6; el host conserva la API Java de OpenCV que llaman los scripts, mientras que el plugin incorpora `libopencv_java4.so`, que coincide exactamente con ella
- `Función` Descubrimiento automático y negociación de compatibilidad con AutoJs6 mediante las acciones `org.autojs.plugin.INFO` y `org.autojs.plugin.OPENCV` (categoría `opencv-runtime`), exponiendo al host los metadatos de versión, contrato y huella
- `Función` Cinco variantes de APK: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, más un paquete `universal` todo en uno, con las arquitecturas compatibles informadas dinámicamente según el contenido real del APK
- `Función` Metadatos del plugin, instrucciones, README y changelog disponibles en 10 idiomas: chino simplificado, chino tradicional (Hong Kong y Taiwán), inglés, francés, español, japonés, coreano, ruso y árabe
- `Función` Controles de compilación integrados que verifican el inventario de bibliotecas nativas, las arquitecturas ELF, los hashes de las cargas útiles, la huella de la API Java, las clases Java de OpenCV duplicadas y los recursos de licencia; las compilaciones publicables exigen además una identidad de firma de confianza
- `Función` Textos completos de las licencias de OpenCV 4.8.0 y de sus componentes de terceros enlazados estáticamente incluidos en cada APK; `libc++_shared.so` lo proporciona y precarga un host AutoJs6 compatible en lugar de duplicarse en el plugin
- `Corrección` Se reconstruyeron las bibliotecas nativas de OpenCV 4.8.0 desde las fuentes oficiales con Android NDK 26 (API 24) para que el plugin y el host compartan la misma familia de entornos de ejecución de C++, corrigiendo los cierres inesperados de AutoJs6 causados por excepciones que cruzan fronteras de entornos de ejecución incompatibles

##### Para ver más historial de versiones

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-es.md)

******

### Compilación y verificación

******

Esta sección está dirigida a los desarrolladores que deseen compilar el plugin desde el código fuente.

Compilar los APK debug:

```powershell
.\gradlew.bat :app:assembleDebug
```

Ejecute las pruebas unitarias y verifique la integridad de los APK debug y release:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

Antes de publicar, configure una identidad de firma de confianza en el archivo `sign.properties`, ignorado por Git, y ejecute:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Genere los cinco APK firmados, el manifiesto SHA-256 y la descripción de GitHub Release (consulte [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md) para ver el proceso completo):

```bat
scripts\release\prepare-release.bat
```

Sin `sign.properties`, las cargas útiles aún pueden compilarse y verificarse, pero los APK release generados no están firmados y no deben publicarse.

Las compilaciones habituales usan el AAR nativo precompilado incluido en el repositorio, por lo que no es necesario compilar OpenCV localmente; para reconstruir todo desde las fuentes oficiales y cotejar la provenance, consulte [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### Localización y generación de documentos

******

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values-*/strings.xml
app/src/main/res/raw-*/plugin_instruction.md
```

`strings.xml` contiene la descripción localizada del plugin, y `plugin_instruction.md` contiene las instrucciones que muestra el host. Para el README y el changelog, edite siempre las fuentes JSON bajo `.readme/` y `.changelog/` y ejecute `py .python/generate_markdown.py` para regenerarlos; los archivos generados nunca se editan a mano. Ejecute `py .python/generate_markdown.py --check` para comprobar que las fuentes y los archivos generados están sincronizados.

******

### Licencia

******

El código del proyecto se distribuye bajo la [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). Consulte [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) para conocer las licencias de OpenCV 4.8.0 y de sus componentes de terceros enlazados estáticamente; los textos completos de las licencias se incluyen en cada APK bajo `assets/licenses/opencv-4.8.0/`.

******

### Enlaces

******

- Documentación de AutoJs6: https://docs.autojs6.com
- Sitio web oficial de OpenCV: https://opencv.org
- Código fuente de OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
