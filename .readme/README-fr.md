<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>Plugin d'environnement d'exécution natif OpenCV 4.8.0 pour AutoJs6</p>

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

Le plugin AutoJs6 OpenCV fournit l'environnement d'exécution natif OpenCV 4.8.0 utilisé par les API de traitement d'image de l'hôte. Dans cette conception privilégiant la compatibilité, AutoJs6 conserve l'API Java d'OpenCV, tandis que le plugin fournit `libopencv_java4.so` pour chaque ABI prise en charge.

******

### Contrat du plugin

******

- ID de l'application: `io.github.supermonster003.autojs6.plugin.opencv`
- ID du plugin: `opencv`
- Moteur: `opencv`
- Variante: `4.8.0`
- Version du contrat: `1`
- Code de version requis de l'hôte: `5236`
- Bibliothèque native: `opencv_java4`
- SHA-256 de l'API Java: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

Le même service `OpenCvPluginInfoService` répond à `org.autojs.plugin.INFO` et à `org.autojs.plugin.OPENCV`.

Les deux actions utilisent la catégorie `opencv-runtime`. Leur interface Binder est `IPluginInfoProvider`, fournie par `common-plugin-api`.

******

### ABI

******

Le projet génère les variantes APK suivantes:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` est déterminé à partir des entrées de la bibliothèque native OpenCV dans l'APK installé. Un APK à ABI unique signale uniquement sa propre ABI, tandis que l'APK `universal` signale les quatre.

Installez l'APK `universal` du plugin lorsque APK Builder doit produire des applications pour plusieurs ABI. Un plugin à ABI unique ne peut fournir OpenCV que pour cette ABI.

******

### Compilation et vérification

******

Exécutez les tests unitaires et vérifiez les APK debug et release:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

Pour compiler uniquement les APK debug:

```powershell
.\gradlew.bat :app:assembleDebug
```

Avant la publication, configurez une identité de signature de confiance dans le fichier `sign.properties`, ignoré par Git, puis exécutez:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Sans `sign.properties`, les charges utiles peuvent toujours être compilées et vérifiées, mais les APK de publication générés ne sont pas signés et ne doivent pas être publiés.

******

### Comportement à l'exécution

******

Le plugin ne possède pas d'interface utilisateur autonome. Une fois installé et activé, AutoJs6 détecte son service et charge la bibliothèque native correspondant à l'API Java conservée. Une bibliothèque native chargée partage normalement la durée de vie du processus hôte, redémarrez donc AutoJs6 après la mise à jour du plugin.

Le plugin contient uniquement `libopencv_java4.so` et n'intègre pas `libc++_shared.so`. Un hôte AutoJs6 compatible doit fournir et précharger cette dépendance à l'échelle du processus afin qu'un même processus utilise un seul environnement d'exécution C++.

Installez uniquement des builds de confiance. Avant de charger la bibliothèque native, l'hôte vérifie la signature du plugin, la version d'OpenCV, la version du contrat et le SHA-256 de l'API Java. Les versions officielles doivent utiliser une identité de signature reconnue par l'hôte.

******

### Historique des versions

******

# v1.0.0

###### 2026/07/22

* `Fonctionnalité` Ajout du plugin d'environnement d'exécution natif OpenCV 4.8.0 pour AutoJs6: l'hôte conserve l'API Java d'OpenCV tandis que le plugin fournit `libopencv_java4.so`
* `Fonctionnalité` Ajout de la détection via `org.autojs.plugin.INFO` et `org.autojs.plugin.OPENCV` avec la catégorie `opencv-runtime`, ainsi que des métadonnées de compatibilité requises par l'hôte
* `Fonctionnalité` Ajout des APK `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` et `universal`, avec signalement dynamique des ABI réellement intégrées
* `Fonctionnalité` Localisation des métadonnées du plugin, des instructions, du README et du CHANGELOG en espagnol, français, russe, arabe, japonais, coréen, anglais, chinois simplifié, chinois traditionnel de Hong Kong et chinois traditionnel de Taïwan
* `Fonctionnalité` Ajout de contrôles d'intégrité debug et release pour l'inventaire natif, l'architecture ELF, les hachages des charges utiles, l'empreinte de l'API Java, les classes Java OpenCV en double et les fichiers de licence; la vérification publiable exige aussi une signature configurée
* `Fonctionnalité` Intégration dans chaque APK des textes complets des licences OpenCV 4.8.0 et des composants tiers statiques; le plugin n'intègre pas `libc++_shared.so`, qu'un hôte AutoJs6 compatible doit fournir et précharger

##### Pour plus d'historique des versions

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-fr.md)

******

### Structure des ressources

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` contient les descriptions localisées du plugin, et `plugin_instruction.md` contient les instructions affichées par l'hôte. README et CHANGELOG sont générés à partir des sources JSON par `.python/generate_markdown.py`; les changelogs localisés complets sont intégrés sous `app/src/main/assets/doc`.

******

### Licence

******

Le code du projet est distribué sous la [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). Consultez [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) pour les licences d'OpenCV 4.8.0 et de ses composants tiers liés statiquement. Les textes complets des licences sont intégrés à chaque APK sous `assets/licenses/opencv-4.8.0/`.

******

### Liens

******

- Documentation AutoJs6: https://docs.autojs6.com/
- Site officiel d'OpenCV: https://opencv.org/
- Code source d'OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
