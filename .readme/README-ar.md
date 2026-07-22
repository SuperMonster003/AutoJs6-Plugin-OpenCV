<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>إضافة وقت تشغيل OpenCV 4.8.0 الأصلية لتطبيق AutoJs6</p>

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

### اللغات (Languages)

******

يدعم README.md الحالي اللغات التالية:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- العربية [ar] # الحالي

******

### مقدمة

******

توفر إضافة AutoJs6 OpenCV بيئة تشغيل OpenCV 4.8.0 الأصلية التي تستخدمها واجهات برمجة تطبيقات الصور في المضيف. في هذا التصميم الذي يركز على التوافق, يحتفظ AutoJs6 بواجهة Java API الخاصة بـ OpenCV, بينما توفر الإضافة الملف `libopencv_java4.so` لكل ABI مدعوم.

******

### عقد الإضافة

******

- معرف التطبيق: `io.github.supermonster003.autojs6.plugin.opencv`
- معرف الإضافة: `opencv`
- المحرك: `opencv`
- المتغير: `4.8.0`
- إصدار العقد: `2`
- رمز إصدار المضيف المطلوب: `5237`
- المكتبة الأصلية: `opencv_java4`
- إصدار NDK الأصلي: `26.1.10909125`
- بصمة Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

تستجيب خدمة `OpenCvPluginInfoService` نفسها للإجرائين `org.autojs.plugin.INFO` و`org.autojs.plugin.OPENCV`.

يستخدم الإجراءان الفئة `opencv-runtime`. واجهة Binder الخاصة بهما هي `IPluginInfoProvider` من `common-plugin-api`.

******

### معماريات ABI

******

ينتج المشروع متغيرات APK التالية:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

يتم اشتقاق `PluginInfo.supportedAbis` من إدخالات مكتبة OpenCV الأصلية في ملف APK المثبت. يبلغ ملف APK أحادي ABI عن ABI الخاص به فقط, بينما يبلغ ملف `universal` عن المعماريات الأربع كلها.

ثبت ملف APK من نوع `universal` عندما يحتاج APK Builder إلى إنتاج تطبيقات لعدة معماريات ABI. لا تستطيع إضافة أحادية ABI توفير OpenCV إلا لتلك ABI.

******

### البناء والتحقق

******

شغل اختبارات الوحدة وتحقق من ملفات APK من نوعي debug وrelease:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

لبناء ملفات APK من نوع debug فقط:

```powershell
.\gradlew.bat :app:assembleDebug
```

قبل النشر, اضبط هوية توقيع موثوقة في ملف `sign.properties` الذي يتجاهله Git, ثم شغل:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

من دون `sign.properties`, لا يزال من الممكن بناء الحمولات والتحقق منها, لكن ملفات APK الناتجة للإصدار غير موقعة ويجب عدم نشرها.

******

### السلوك أثناء التشغيل

******

لا تحتوي الإضافة على واجهة مستخدم مستقلة. بعد تثبيتها وتفعيلها, يكتشف AutoJs6 خدمتها ويحمل المكتبة الأصلية المطابقة لواجهة Java API التي يحتفظ بها. عادة ما تشارك المكتبة الأصلية المحملة دورة حياة عملية المضيف, لذلك أعد تشغيل AutoJs6 بعد تحديث الإضافة.

لا تحتوي الإضافة إلا على `libopencv_java4.so` ولا تتضمن `libc++_shared.so`. يجب على مضيف AutoJs6 متوافق توفير هذه التبعية على مستوى العملية وتحميلها مسبقا لكي تستخدم العملية بيئة تشغيل C++ واحدة.

ثبت البنيات الموثوقة فقط. قبل تحميل المكتبة الأصلية, يتحقق المضيف من توقيع الإضافة, وإصدار OpenCV, وإصدار العقد, وبصمة Java API SHA-256. يجب أن تستخدم الإصدارات الرسمية هوية توقيع يتعرف عليها المضيف.

******

### سجل الإصدارات

******

# v1.0.0

###### 2026/07/22

* `ميزة` تم إصدار إضافة بيئة تشغيل OpenCV 4.8.0 الأصلية لتطبيق AutoJs6: يحتفظ المضيف بواجهة OpenCV Java API بينما توفر الإضافة `libopencv_java4.so`
* `ميزة` تمت إضافة الاكتشاف عبر `org.autojs.plugin.INFO` و`org.autojs.plugin.OPENCV` مع الفئة `opencv-runtime`, إلى جانب بيانات التوافق التي يحتاجها المضيف
* `ميزة` تمت إضافة ملفات APK للمعماريات `arm64-v8a` و`armeabi-v7a` و`x86` و`x86_64` و`universal`, مع الإبلاغ الديناميكي عن معماريات ABI المضمنة فعليا
* `ميزة` تمت ترجمة بيانات الإضافة والتعليمات وREADME وCHANGELOG إلى الإسبانية والفرنسية والروسية والعربية واليابانية والكورية والإنجليزية والصينية المبسطة والصينية التقليدية في هونغ كونغ والصينية التقليدية في تايوان
* `ميزة` تمت إضافة فحوص سلامة debug وrelease لقائمة المكتبات الأصلية ومعمارية ELF وتجزئات الحمولة وبصمة Java API وفئات OpenCV Java المكررة وملفات الترخيص; ويتطلب التحقق من البناء القابل للنشر أيضا توقيعا مضبوطا
* `ميزة` يتضمن كل ملف APK النصوص الكاملة لتراخيص OpenCV 4.8.0 والمكونات الخارجية الثابتة; ولا تتضمن الإضافة `libc++_shared.so` الذي يجب على مضيف AutoJs6 متوافق توفيره وتحميله مسبقا
* `إصلاح` أعيد بناء مكتبات OpenCV 4.8.0 الأصلية باستخدام Android NDK 26 وAPI 24 لمنع الاستثناءات من عبور حدود بيئات تشغيل C++ غير المتوافقة والتسبب في تعطل مضيف AutoJs6

##### لمزيد من سجل الإصدارات

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ar.md)

******

### بنية الموارد

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

يحتوي `strings.xml` على أوصاف الإضافة المترجمة, ويحتوي `plugin_instruction.md` على التعليمات التي يعرضها المضيف. يتم إنشاء README وCHANGELOG من مصادر JSON بواسطة `.python/generate_markdown.py`; ويتم تضمين سجلات التغيير المترجمة الكاملة تحت `app/src/main/assets/doc`.

******

### الترخيص

******

يتم توزيع رمز المشروع بموجب [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). راجع [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) للاطلاع على تراخيص OpenCV 4.8.0 ومكوناته الخارجية المرتبطة ربطا ثابتا. يتم تضمين نصوص التراخيص الكاملة في كل ملف APK تحت `assets/licenses/opencv-4.8.0/`.

******

### الروابط

******

- وثائق AutoJs6: https://docs.autojs6.com/
- الموقع الرسمي لـ OpenCV: https://opencv.org/
- الشفرة المصدرية لـ OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
