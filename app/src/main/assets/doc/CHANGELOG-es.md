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
