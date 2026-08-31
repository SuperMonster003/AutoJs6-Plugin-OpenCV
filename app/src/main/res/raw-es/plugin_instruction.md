El plugin OpenCV (OpenCV Plugin) proporciona el entorno de ejecución nativo de OpenCV 4.8.0 que impulsa el procesamiento de imágenes en AutoJs6. Las API de imágenes como la búsqueda de imágenes, la detección de colores y la coincidencia de plantillas dependen todas de OpenCV para sus cálculos; una vez instalado este plugin, un host AutoJs6 compatible con OpenCV en forma de plugin puede usar estas funciones de imagen con normalidad, sin ninguna configuración adicional en los scripts.

### Cómo se usa

1. Descargue desde la página [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) el APK del plugin que corresponda al dispositivo e instálelo en el dispositivo que ejecuta AutoJs6; en caso de duda, elija el paquete `universal` o consulte `Cómo elegir un APK` más abajo.
2. Abra el centro de plugins de AutoJs6 y confirme que el plugin `OpenCV` está reconocido y habilitado.
3. Escriba y ejecute sus scripts como de costumbre: cuando un script usa las API de imágenes, AutoJs6 carga automáticamente la biblioteca nativa de OpenCV proporcionada por el plugin; el código de los scripts no necesita ningún cambio.
4. Después de actualizar o reinstalar el plugin, salga por completo de AutoJs6 y reinícielo antes de volver a ejecutar scripts de imágenes, para que la nueva biblioteca nativa surta efecto.

Si el plugin no aparece en el centro de plugins, actualice primero AutoJs6 a una versión reciente (compilación interna 5237 o superior). El propio plugin es compatible con dispositivos con Android 7.0 (API 24) o superior.

### Cómo elegir un APK

Cada versión publicada incluye 5 APK que solo se diferencian en las arquitecturas de biblioteca nativa que incorporan:

| Paquete | Recomendado para |
|---|---|
| `arm64-v8a` | La gran mayoría de los teléfonos y tabletas Android modernos (ARM de 64 bits); la primera opción |
| `armeabi-v7a` | Dispositivos ARM de 32 bits más antiguos |
| `x86_64` | Emuladores x86 de 64 bits y unos pocos dispositivos x86 |
| `x86` | Emuladores x86 de 32 bits y unos pocos dispositivos x86 |
| `universal` | Incorpora las 4 arquitecturas y es el más grande; funciona en cualquier dispositivo y es la opción segura en caso de duda |

Instale el paquete `universal` del plugin cuando use el APK Builder de AutoJs6 para empaquetar aplicaciones destinadas a varias arquitecturas: un plugin de una sola ABI solo puede proporcionar OpenCV para su propia arquitectura. Si por error se instaló un paquete de una sola ABI que no corresponde al dispositivo, el plugin no podrá ofrecer una biblioteca nativa utilizable; cambiar al paquete `universal` lo resuelve.

### Autocomprobación rápida

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
