<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>Плагин нативной среды выполнения OpenCV 4.8.0 для AutoJs6</p>

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

### Языки (Languages)

******

Текущий README.md поддерживает следующие языки:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- Русский [ru] # текущий
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### Введение

******

Плагин AutoJs6 OpenCV предоставляет нативную среду OpenCV 4.8.0, используемую графическими API хоста. В этой ориентированной на совместимость схеме Java API OpenCV остается в AutoJs6, а плагин предоставляет `libopencv_java4.so` для каждой поддерживаемой ABI.

******

### Контракт плагина

******

- Идентификатор приложения: `io.github.supermonster003.autojs6.plugin.opencv`
- Идентификатор плагина: `opencv`
- Движок: `opencv`
- Вариант: `4.8.0`
- Версия контракта: `1`
- Требуемый код версии хоста: `5236`
- Нативная библиотека: `opencv_java4`
- SHA-256 Java API: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

Одна служба `OpenCvPluginInfoService` отвечает на действия `org.autojs.plugin.INFO` и `org.autojs.plugin.OPENCV`.

Оба действия используют категорию `opencv-runtime`. Их интерфейс Binder представлен `IPluginInfoProvider` из `common-plugin-api`.

******

### ABI

******

Проект создает следующие варианты APK:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

Значение `PluginInfo.supportedAbis` определяется по записям нативной библиотеки OpenCV в установленном APK. APK для одной ABI сообщает только свою ABI, а APK `universal` сообщает все четыре.

Установите APK плагина `universal`, если APK Builder должен создавать приложения для нескольких ABI. Плагин для одной ABI может предоставить OpenCV только для этой ABI.

******

### Сборка и проверка

******

Запустите модульные тесты и проверьте APK debug и release:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

Чтобы собрать только APK debug:

```powershell
.\gradlew.bat :app:assembleDebug
```

Перед публикацией настройте доверенный идентификатор подписи в игнорируемом Git файле `sign.properties` и запустите:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Без `sign.properties` полезную нагрузку по-прежнему можно собрать и проверить, но созданные APK release не подписаны и не должны публиковаться.

******

### Поведение во время выполнения

******

У плагина нет отдельного пользовательского интерфейса. После установки и включения AutoJs6 обнаруживает его службу и загружает нативную библиотеку, соответствующую сохраненному Java API. Загруженная нативная библиотека обычно существует в течение всего времени жизни процесса хоста, поэтому после обновления плагина перезапустите AutoJs6.

Плагин содержит только `libopencv_java4.so` и не включает `libc++_shared.so`. Совместимый хост AutoJs6 должен предоставить и предварительно загрузить эту общую для процесса зависимость, чтобы один процесс использовал только одну среду выполнения C++.

Устанавливайте только доверенные сборки. Перед загрузкой нативной библиотеки хост проверяет подпись плагина, версию OpenCV, версию контракта и SHA-256 Java API. Официальные выпуски должны использовать идентификатор подписи, распознаваемый хостом.

******

### История выпусков

******

# v1.0.0

###### 2026/07/22

* `Функция` Выпущен плагин нативной среды OpenCV 4.8.0 для AutoJs6: хост сохраняет Java API OpenCV, а плагин предоставляет `libopencv_java4.so`
* `Функция` Добавлено обнаружение через `org.autojs.plugin.INFO` и `org.autojs.plugin.OPENCV` с категорией `opencv-runtime`, а также метаданные совместимости, необходимые хосту
* `Функция` Добавлены APK `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` и `universal` с динамическим указанием фактически включенных ABI
* `Функция` Метаданные плагина, инструкции, README и CHANGELOG локализованы на испанский, французский, русский, арабский, японский, корейский, английский, упрощенный китайский, гонконгский традиционный китайский и тайваньский традиционный китайский
* `Функция` Добавлены проверки целостности debug и release для состава нативных библиотек, архитектуры ELF, хешей полезной нагрузки, отпечатка Java API, дублирующихся классов OpenCV Java и файлов лицензий; проверка публикуемой сборки также требует настроенную подпись
* `Функция` В каждый APK включены полные тексты лицензий OpenCV 4.8.0 и статических сторонних компонентов; плагин не включает `libc++_shared.so`, которую должен предоставить и предварительно загрузить совместимый хост AutoJs6

##### Больше истории выпусков

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ru.md)

******

### Структура ресурсов

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` содержит локализованные описания плагина, а `plugin_instruction.md` содержит инструкции, отображаемые хостом. README и CHANGELOG генерируются из исходных данных JSON с помощью `.python/generate_markdown.py`; полные локализованные журналы включаются в `app/src/main/assets/doc`.

******

### Лицензия

******

Код проекта распространяется по лицензии [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). Лицензии OpenCV 4.8.0 и статически связанных сторонних компонентов приведены в [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md). Полные тексты лицензий включаются в каждый APK в каталоге `assets/licenses/opencv-4.8.0/`.

******

### Ссылки

******

- Документация AutoJs6: https://docs.autojs6.com/
- Официальный сайт OpenCV: https://opencv.org/
- Исходный код OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
