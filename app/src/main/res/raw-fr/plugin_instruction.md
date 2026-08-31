Le plugin OpenCV (OpenCV Plugin) fournit l'environnement d'exécution natif OpenCV 4.8.0 qui alimente le traitement d'image dans AutoJs6. Les API d'image telles que la recherche d'image, la détection de couleur et la correspondance de modèles s'appuient toutes sur OpenCV pour leurs calculs; une fois ce plugin installé, un hôte AutoJs6 prenant en charge OpenCV sous forme de plugin peut utiliser normalement ces fonctions d'image, sans aucune configuration supplémentaire dans les scripts.

### Mode d'emploi

1. Téléchargez depuis la page [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) l'APK du plugin correspondant à l'appareil et installez-le sur l'appareil exécutant AutoJs6; en cas de doute, choisissez le paquet `universal` ou consultez `Choisir un APK` ci-dessous.
2. Ouvrez le centre de plugins d'AutoJs6 et vérifiez que le plugin `OpenCV` est reconnu et activé.
3. Écrivez et exécutez vos scripts comme d'habitude: dès qu'un script utilise les API d'image, AutoJs6 charge automatiquement la bibliothèque native OpenCV fournie par le plugin; le code des scripts ne nécessite aucune modification.
4. Après une mise à jour ou une réinstallation du plugin, quittez complètement puis redémarrez AutoJs6 avant de relancer des scripts d'image, afin que la nouvelle bibliothèque native prenne effet.

Si le plugin n'apparaît pas dans le centre de plugins, mettez d'abord AutoJs6 à jour vers une version récente (build interne 5237 ou ultérieur). Le plugin lui-même prend en charge les appareils sous Android 7.0 (API 24) et versions ultérieures.

### Choisir un APK

Chaque version publiée comprend 5 APK qui ne diffèrent que par les architectures de bibliothèque native embarquées:

| Paquet | Recommandé pour |
|---|---|
| `arm64-v8a` | La grande majorité des téléphones et tablettes Android modernes (ARM 64 bits); premier choix |
| `armeabi-v7a` | Appareils ARM 32 bits plus anciens |
| `x86_64` | Émulateurs x86 64 bits et quelques appareils x86 |
| `x86` | Émulateurs x86 32 bits et quelques appareils x86 |
| `universal` | Regroupe les 4 architectures et est le plus volumineux; fonctionne sur tout appareil et reste le choix sûr en cas de doute |

Installez le paquet de plugin `universal` lorsque vous utilisez l'APK Builder d'AutoJs6 pour empaqueter des applications visant plusieurs architectures: un plugin à ABI unique ne peut fournir OpenCV que pour sa propre architecture. Si un paquet à ABI unique ne correspondant pas à l'appareil a été installé par erreur, le plugin ne peut fournir aucune bibliothèque native utilisable; passer au paquet `universal` résout le problème.

### Autodiagnostic rapide

Après avoir vérifié qu'OpenCV est activé dans le centre de plugins d'AutoJs6 et redémarré AutoJs6, exécutez ce script. `images.initOpenCvIfNeeded()` déclenche réellement la découverte du plugin, les contrôles de compatibilité et le chargement natif:

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

En cas de succès, le script affiche `OpenCV version: 4.8.0` et une ABI de processus telle que `arm64-v8a`. Si le chargement échoue, vérifiez dans cet ordre: installez le paquet `universal`, mettez AutoJs6 à jour vers la build interne 5237 ou ultérieure, puis quittez complètement et redémarrez AutoJs6.
