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
* `Correctif` Reconstruction des bibliothèques natives OpenCV 4.8.0 avec Android NDK 26 et l'API 24 afin d'empêcher les exceptions de franchir les limites d'environnements d'exécution C++ incompatibles et de provoquer le plantage de l'hôte AutoJs6
