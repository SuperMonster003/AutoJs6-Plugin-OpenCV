<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>Plugin d'environnement d'exécution natif OpenCV 4.8.0 pour AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-OpenCV?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Langues (Languages)

******

Le README.md actuel prend en charge les langues suivantes:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- Français [fr] # actuel
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### Introduction

******

Le plugin OpenCV (OpenCV Plugin) fournit l'environnement d'exécution natif OpenCV 4.8.0 qui alimente le traitement d'image dans AutoJs6. Les API d'image telles que la recherche d'image, la détection de couleur et la correspondance de modèles s'appuient toutes sur OpenCV pour leurs calculs; une fois ce plugin installé, un hôte AutoJs6 prenant en charge OpenCV sous forme de plugin peut utiliser normalement ces fonctions d'image, sans aucune configuration supplémentaire dans les scripts.

Le plugin suit une répartition des rôles privilégiant la compatibilité: l'hôte AutoJs6 conserve l'API Java d'OpenCV appelée directement par les scripts, tandis que le plugin embarque la bibliothèque native `libopencv_java4.so` qui lui correspond exactement. Le paquet de l'hôte reste ainsi léger, chaque appareil n'installe que le paquet de plugin correspondant à son architecture de processeur (ABI), et l'environnement d'exécution OpenCV peut être mis à jour indépendamment de l'hôte.

******

### Points forts

******

- Prêt à l'emploi: aucune configuration requise; AutoJs6 découvre automatiquement le plugin et charge l'environnement d'exécution OpenCV à la demande lors de l'exécution des scripts d'image.
- Cinq variantes d'APK: quatre paquets à ABI unique (`arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`) plus un paquet `universal` tout-en-un, afin que chaque appareil n'installe que le nécessaire.
- Compilation depuis les sources officielles: les bibliothèques natives sont reconstruites à partir du code source officiel OpenCV 4.8.0 non modifié (NDK r26b, API 24), avec les entrées de compilation et les hachages par ABI consignés dans un manifeste de provenance, permettant une reproduction indépendante.
- Vérification avant chargement: l'hôte contrôle la signature du plugin, la version d'OpenCV, la version du contrat et l'empreinte de l'API Java, et refuse les environnements d'exécution discordants ou altérés.
- Un seul environnement d'exécution C++ par processus: le plugin ne duplique pas `libc++_shared.so` et partage la dépendance à l'échelle du processus fournie par l'hôte, ce qui évite les plantages causés par la coexistence de plusieurs environnements d'exécution C++.
- Licences transparentes: chaque APK embarque les textes complets des licences d'OpenCV et de ses composants tiers liés statiquement, récapitulés dans THIRD_PARTY_NOTICES.md.
- Multilingue: métadonnées du plugin, instructions, README et changelog disponibles en 10 langues.

******

### Mode d'emploi

******

1. Téléchargez depuis la page [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) l'APK du plugin correspondant à l'appareil et installez-le sur l'appareil exécutant AutoJs6; en cas de doute, choisissez le paquet `universal` ou consultez `Choisir un APK` ci-dessous.
2. Ouvrez le centre de plugins d'AutoJs6 et vérifiez que le plugin `OpenCV` est reconnu et activé.
3. Écrivez et exécutez vos scripts comme d'habitude: dès qu'un script utilise les API d'image, AutoJs6 charge automatiquement la bibliothèque native OpenCV fournie par le plugin; le code des scripts ne nécessite aucune modification.
4. Après une mise à jour ou une réinstallation du plugin, quittez complètement puis redémarrez AutoJs6 avant de relancer des scripts d'image, afin que la nouvelle bibliothèque native prenne effet.

> Si le plugin n'apparaît pas dans le centre de plugins, mettez d'abord AutoJs6 à jour vers une version récente (build interne 5237 ou ultérieur). Le plugin lui-même prend en charge les appareils sous Android 7.0 (API 24) et versions ultérieures.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="Le centre de plugins d'AutoJs6 reconnaît OpenCV 1.1.0 et l'affiche comme activé." width="480" />
</p>
<p align="center"><sub>Le centre de plugins d'AutoJs6 reconnaît OpenCV 1.1.0 et l'affiche comme activé.</sub></p>

******

### Choisir un APK

******

Chaque version publiée comprend 5 APK qui ne diffèrent que par les architectures de bibliothèque native embarquées:

| Paquet | Recommandé pour |
|---|---|
| `arm64-v8a` | La grande majorité des téléphones et tablettes Android modernes (ARM 64 bits); premier choix |
| `armeabi-v7a` | Appareils ARM 32 bits plus anciens |
| `x86_64` | Émulateurs x86 64 bits et quelques appareils x86 |
| `x86` | Émulateurs x86 32 bits et quelques appareils x86 |
| `universal` | Regroupe les 4 architectures et est le plus volumineux; fonctionne sur tout appareil et reste le choix sûr en cas de doute |

Installez le paquet de plugin `universal` lorsque vous utilisez l'APK Builder d'AutoJs6 pour empaqueter des applications visant plusieurs architectures: un plugin à ABI unique ne peut fournir OpenCV que pour sa propre architecture. Si un paquet à ABI unique ne correspondant pas à l'appareil a été installé par erreur, le plugin ne peut fournir aucune bibliothèque native utilisable; passer au paquet `universal` résout le problème.

******

### Autodiagnostic rapide

******

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

******

### Questions fréquentes

******

#### Comment confirmer que le plugin fonctionne?

Ouvrez le centre de plugins d'AutoJs6; si le plugin `OpenCV` y figure, l'hôte l'a reconnu. Exécutez ensuite n'importe quel script utilisant les API d'image; si les résultats reviennent normalement, la bibliothèque native s'est chargée avec succès.

#### Pourquoi n'y a-t-il pas d'icône du plugin dans la liste des applications?

C'est normal. Le plugin n'a pas d'interface autonome et ne crée aucune icône de lancement; après l'installation, il est découvert et piloté entièrement par AutoJs6 en arrière-plan, et toutes les interactions ont lieu dans AutoJs6.

#### Les fonctions d'image se comportent anormalement après une mise à jour du plugin, ou l'ancienne version semble encore active?

Une fois chargée, une bibliothèque native subsiste aussi longtemps que le processus hôte; mettre à jour le plugin ne remplace donc pas la bibliothèque déjà en cours d'utilisation. Quittez complètement puis redémarrez AutoJs6 pour que la nouvelle bibliothèque native prenne effet.

#### Que faire si l'hôte est signalé comme trop ancien ou incompatible?

Le plugin exige un build interne d'AutoJs6 égal ou supérieur à 5237; mettez donc d'abord AutoJs6 à jour. Avant le chargement, l'hôte vérifie la version du contrat et l'empreinte de l'API Java, et refuse de charger à la moindre discordance plutôt que de fonctionner avec des risques cachés.

#### Le plugin est installé mais les fonctions d'image échouent toujours. Quelle peut en être la cause?

La cause la plus fréquente est un APK ne correspondant pas à l'architecture de l'appareil: un paquet à ABI unique ne fonctionne que sur sa propre architecture. Passez au paquet `universal` pour écarter cette cause; si le problème persiste, vérifiez que la version d'AutoJs6 répond à l'exigence et réessayez après avoir redémarré AutoJs6.

#### Le plugin accède-t-il au réseau ou demande-t-il des autorisations sensibles?

Non. Son manifeste ne contient aucune autorisation réseau, de stockage, d'appareil photo ni aucune autre autorisation système sensible; il déclare uniquement l'autorisation de plugin servant à communiquer avec AutoJs6. Son seul rôle est de remettre la bibliothèque native OpenCV à l'hôte.

#### Pourquoi OpenCV 4.8.0 plutôt qu'une version plus récente?

La bibliothèque native doit correspondre exactement à l'API Java d'OpenCV conservée par l'hôte (vérification par empreinte SHA-256); la version d'OpenCV est donc verrouillée par le contrat entre l'hôte et le plugin. Des versions plus récentes d'OpenCV suivront sous forme de nouvelles variantes une fois prises en charge par l'hôte; consultez [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md) pour suivre l'avancement.

******

### Autorisations et sécurité

******

Le code natif s'exécute dans le même processus que l'hôte; plusieurs lignes de défense s'appliquent donc de la compilation au chargement:

- Origine vérifiable: les bibliothèques natives sont reconstruites à partir d'un commit épinglé du code source officiel d'OpenCV, avec les versions de la chaîne d'outils et les hachages par ABI consignés dans `libs/opencv-native-4.8.0.provenance.json`; chacun peut reproduire et comparer en suivant [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).
- Contrôles de compilation: chaque compilation vérifie l'inventaire des bibliothèques natives, les architectures ELF, les hachages des charges utiles, l'empreinte de l'API Java, les classes Java OpenCV en double et les ressources de licence; les compilations publiables exigent en outre une identité de signature de confiance.
- Vérification avant chargement: l'hôte contrôle tour à tour la signature du plugin, la version d'OpenCV, la version du contrat et le SHA-256 de l'API Java, et refuse de charger à la moindre discordance.
- L'environnement d'exécution C++ à l'échelle du processus est fourni et préchargé par l'hôte; le plugin n'embarque pas `libc++_shared.so`, ce qui évite les plantages aux frontières d'environnements d'exécution incompatibles.
- Empreinte minimale: aucune autorisation réseau ni autorisation système sensible, aucune interface autonome, et communication avec l'hôte uniquement via l'autorisation de plugin AutoJs6.

Installez le plugin uniquement depuis la page officielle [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) ou d'autres canaux de confiance. Les paquets officiels utilisent une identité de signature reconnue par l'hôte; les paquets d'origine inconnue peuvent échouer à la vérification de l'hôte ou dissimuler des risques, même lorsque le numéro de version semble identique.

******

### Interface du plugin

******

Les informations suivantes s'adressent aux développeurs de l'hôte AutoJs6 et de plugins; l'hôte utilise ces identifiants pour découvrir le plugin et négocier la compatibilité:

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

Un même service `OpenCvPluginInfoService` répond aux actions `org.autojs.plugin.INFO` et `org.autojs.plugin.OPENCV`, chacune avec la catégorie `opencv-runtime`; l'interface Binder est `IPluginInfoProvider`, fournie par common-plugin-api.

`PluginInfo.supportedAbis` est calculé dynamiquement à partir des entrées de bibliothèque native OpenCV réellement présentes dans l'APK installé: un paquet à ABI unique ne signale que sa propre architecture, tandis que le paquet `universal` signale les 4.

******

### Feuille de route

******

Les capacités prévues du plugin et leur état d'avancement sont tenus à jour sous forme de liste cochable dans ROADMAP.md, organisée par jalons avec des critères d'acceptation, couvrant la prise en charge des pages mémoire de 16 KB, l'évolution des versions d'OpenCV, l'intégration continue et le diagnostic. Les éléments non cochés décrivent des intentions, pas des capacités déjà livrées; la discussion via Issues est la bienvenue.

- [Voir ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### Historique des versions

******

#### v1.1.0

_2026/08/31_

- `Fonctionnalité` Les téléchargements de la version regroupent désormais les 5 APK d'architecture, `SHA256SUMS.txt` et le manifeste de provenance de la compilation native afin de choisir le bon paquet et de vérifier indépendamment les fichiers
- `Correctif` Les bibliothèques natives des 4 ABI ont été reconstruites avec des segments `PT_LOAD` alignés sur 16 Ko et protégées par un contrôle de publication afin qu'OpenCV se charge sur les appareils Android à pages de 16 Ko tout en restant compatible avec ceux à pages de 4 Ko
- `Amélioration` Les instructions du centre de plugins et le README en 10 langues incluent maintenant une capture réelle de l'état activé et un script d'auto-vérification exécutable affichant la version d'OpenCV et l'ABI du processus
- `Amélioration` La détection des ABI pris en charge couvre désormais les installations universal, mono-ABI et split, poursuit l'analyse malgré des APK manquants ou corrompus et utilise au besoin la bibliothèque native extraite

#### v1.0.0

_2026/07/22_

- `Fonctionnalité` Première version officielle: fournit l'environnement d'exécution natif OpenCV 4.8.0 derrière les API d'image d'AutoJs6; l'hôte conserve l'API Java d'OpenCV appelée par les scripts, tandis que le plugin embarque `libopencv_java4.so` qui lui correspond exactement
- `Fonctionnalité` Découverte automatique et négociation de compatibilité avec AutoJs6 via les actions `org.autojs.plugin.INFO` et `org.autojs.plugin.OPENCV` (catégorie `opencv-runtime`), en exposant à l'hôte les métadonnées de version, de contrat et d'empreinte
- `Fonctionnalité` Cinq variantes d'APK: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, plus un paquet `universal` tout-en-un, avec signalement dynamique des architectures prises en charge d'après le contenu réel de l'APK
- `Fonctionnalité` Métadonnées du plugin, instructions, README et changelog disponibles en 10 langues: chinois simplifié, chinois traditionnel (Hong Kong et Taïwan), anglais, français, espagnol, japonais, coréen, russe et arabe
- `Fonctionnalité` Contrôles de compilation intégrés vérifiant l'inventaire des bibliothèques natives, les architectures ELF, les hachages des charges utiles, l'empreinte de l'API Java, les classes Java OpenCV en double et les ressources de licence; les compilations publiables exigent en outre une identité de signature de confiance
- `Fonctionnalité` Textes complets des licences d'OpenCV 4.8.0 et de ses composants tiers liés statiquement intégrés à chaque APK; `libc++_shared.so` est fourni et préchargé par un hôte AutoJs6 compatible au lieu d'être dupliqué dans le plugin
- `Correctif` Reconstruction des bibliothèques natives OpenCV 4.8.0 depuis les sources officielles avec Android NDK 26 (API 24) afin que le plugin et l'hôte partagent la même famille d'environnements d'exécution C++, corrigeant les plantages d'AutoJs6 causés par des exceptions franchissant des frontières d'environnements d'exécution incompatibles

##### Pour plus d'historique des versions

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-fr.md)

******

### Compilation et vérification

******

Cette section s'adresse aux développeurs souhaitant compiler le plugin depuis les sources.

Compiler les APK debug:

```powershell
.\gradlew.bat :app:assembleDebug
```

Exécutez les tests unitaires et vérifiez l'intégrité des APK debug et release:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

Avant la publication, configurez une identité de signature de confiance dans le fichier `sign.properties`, ignoré par Git, puis exécutez:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Générez les cinq APK signés, le manifeste SHA-256 et la description GitHub Release (consultez [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md) pour la procédure complète):

```bat
scripts\release\prepare-release.bat
```

Sans `sign.properties`, les charges utiles peuvent toujours être compilées et vérifiées, mais les APK release générés ne sont pas signés et ne doivent pas être publiés.

Les compilations ordinaires utilisent l'AAR natif préconstruit présent dans le dépôt, il est donc inutile de compiler OpenCV localement; pour reconstruire entièrement depuis les sources officielles et recouper la provenance, consultez [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### Localisation et génération des documents

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

`strings.xml` contient la description localisée du plugin, et `plugin_instruction.md` contient les instructions affichées par l'hôte. Pour le README et le changelog, modifiez toujours les sources JSON sous `.readme/` et `.changelog/`, puis exécutez `py .python/generate_markdown.py` pour tout régénérer; les fichiers générés ne sont jamais modifiés à la main. Exécutez `py .python/generate_markdown.py --check` pour vérifier que sources et fichiers générés sont synchronisés.

******

### Licence

******

Le code du projet est distribué sous la [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). Consultez [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) pour les licences d'OpenCV 4.8.0 et de ses composants tiers liés statiquement; les textes complets des licences sont intégrés à chaque APK sous `assets/licenses/opencv-4.8.0/`.

******

### Liens

******

- Documentation AutoJs6: https://docs.autojs6.com
- Site officiel d'OpenCV: https://opencv.org
- Code source d'OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
