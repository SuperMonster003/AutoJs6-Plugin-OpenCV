<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>إضافة وقت تشغيل OpenCV 4.8.0 الأصلية لتطبيق AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

توفر إضافة OpenCV (OpenCV Plugin) بيئة تشغيل OpenCV 4.8.0 الأصلية التي تقوم عليها معالجة الصور في AutoJs6. فواجهات برمجة تطبيقات الصور, مثل البحث عن الصور واكتشاف الألوان ومطابقة القوالب, تعتمد كلها على OpenCV في حساباتها; وبعد تثبيت هذه الإضافة يمكن لمضيف AutoJs6 الداعم لإضافة OpenCV استخدام ميزات الصور هذه بشكل طبيعي من دون أي إعداد إضافي في البرامج النصية.

تعتمد الإضافة تقسيما للمهام يقدم التوافق أولا: يحتفظ مضيف AutoJs6 بواجهة OpenCV Java API التي تستدعيها البرامج النصية مباشرة, بينما تحمل الإضافة المكتبة الأصلية المطابقة لها تماما `libopencv_java4.so`. وبذلك تبقى حزمة المضيف صغيرة الحجم, ولا يثبت كل جهاز إلا حزمة الإضافة المطابقة لمعمارية معالجه (ABI), ويمكن تحديث بيئة تشغيل OpenCV بشكل مستقل عن المضيف.

******

### أبرز الميزات

******

- تعمل فور التثبيت: لا حاجة إلى أي إعداد; يكتشف AutoJs6 الإضافة تلقائيا ويحمل بيئة تشغيل OpenCV عند الحاجة أثناء تشغيل البرامج النصية التي تتعامل مع الصور.
- خمس حزم APK: أربع حزم أحادية ABI (`arm64-v8a` و`armeabi-v7a` و`x86` و`x86_64`) إضافة إلى حزمة `universal` الشاملة, بحيث لا يثبت كل جهاز إلا ما يحتاج إليه.
- بناء من المصادر الرسمية: أعيد بناء المكتبات الأصلية من شجرة مصادر OpenCV 4.8.0 الرسمية من دون تعديل (NDK r26b وAPI 24), مع تسجيل مدخلات البناء وتجزئات كل ABI في بيان provenance بما يتيح إعادة البناء والمقارنة بشكل مستقل.
- تحقق قبل التحميل: يفحص المضيف توقيع الإضافة وإصدار OpenCV وإصدار العقد وبصمة Java API, ويرفض بيئات التشغيل غير المطابقة أو التي جرى العبث بها.
- بيئة تشغيل C++ واحدة لكل عملية: لا تكرر الإضافة `libc++_shared.so` بل تشارك التبعية على مستوى العملية التي يوفرها المضيف, مما يمنع الأعطال الناتجة عن تعايش عدة بيئات تشغيل C++.
- شفافية الترخيص: تتضمن كل حزمة APK النصوص الكاملة لتراخيص OpenCV ومكوناته الخارجية المرتبطة ربطا ثابتا, مع ملخص في THIRD_PARTY_NOTICES.md.
- تعدد اللغات: بيانات الإضافة والتعليمات وREADME وسجل التغييرات متوفرة بعشر لغات.

******

### طريقة الاستخدام

******

1. نزل من صفحة [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) ملف APK للإضافة المطابق للجهاز وثبته على الجهاز الذي يعمل عليه AutoJs6; وعند التردد اختر حزمة `universal` مباشرة أو راجع `كيفية اختيار حزمة APK` أدناه.
2. افتح مركز الإضافات في AutoJs6 وتأكد من أن إضافة `OpenCV` قد تم التعرف عليها وأنها مفعلة.
3. اكتب البرامج النصية وشغلها كالمعتاد: عندما يستخدم برنامج نصي واجهات برمجة تطبيقات الصور, يحمل AutoJs6 مكتبة OpenCV الأصلية من الإضافة تلقائيا, ولا يحتاج كود البرنامج النصي إلى أي تعديل.
4. بعد تحديث الإضافة أو إعادة تثبيتها, أغلق AutoJs6 تماما ثم أعد تشغيله قبل تشغيل البرامج النصية الخاصة بالصور مجددا لكي تسري المكتبة الأصلية الجديدة.

> إذا لم تظهر الإضافة في مركز الإضافات, فحدث AutoJs6 أولا إلى إصدار حديث (البنية الداخلية 5237 أو أعلى). وتدعم الإضافة نفسها الأجهزة العاملة بنظام Android 7.0 (API 24) وما فوق.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="يتعرّف مركز إضافات AutoJs6 على OpenCV 1.1.0 ويعرضه في حالة التمكين." width="480" />
</p>
<p align="center"><sub>يتعرّف مركز إضافات AutoJs6 على OpenCV 1.1.0 ويعرضه في حالة التمكين.</sub></p>

******

### كيفية اختيار حزمة APK

******

يتضمن كل إصدار 5 ملفات APK لا تختلف إلا في معماريات المكتبات الأصلية المضمنة فيها:

| الحزمة | الفئة المستهدفة |
|---|---|
| `arm64-v8a` | الغالبية العظمى من هواتف Android وأجهزتها اللوحية الحديثة (ARM بمعمارية 64 بت); وهي الخيار الأول |
| `armeabi-v7a` | أجهزة ARM الأقدم بمعمارية 32 بت |
| `x86_64` | محاكيات x86 بمعمارية 64 بت وعدد قليل من أجهزة x86 |
| `x86` | محاكيات x86 بمعمارية 32 بت وعدد قليل من أجهزة x86 |
| `universal` | تضم المعماريات الأربع كلها وهي الأكبر حجما; تعمل على أي جهاز وهي الخيار الآمن عند التردد |

ثبت حزمة الإضافة `universal` عند استخدام APK Builder في AutoJs6 لإنتاج تطبيقات موجهة لعدة معماريات: فالإضافة أحادية ABI لا توفر OpenCV إلا لمعماريتها فقط. وإذا ثبتت عن طريق الخطأ حزمة أحادية ABI لا تطابق الجهاز, فلن تستطيع الإضافة توفير مكتبة أصلية صالحة, والتحول إلى حزمة `universal` يحل المشكلة.

******

### فحص ذاتي سريع

******

بعد التأكد من ظهور OpenCV كمفعل في مركز إضافات AutoJs6 وإعادة تشغيل AutoJs6, شغل النص التالي. يستدعي `images.initOpenCvIfNeeded()` فعليا اكتشاف الإضافة وفحوص التوافق وتحميل المكتبة الأصلية:

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

عند النجاح يطبع النص `OpenCV version: 4.8.0` وبنية العملية مثل `arm64-v8a`. إذا فشل التحميل, جرب بالترتيب: ثبت حزمة `universal`, حدث AutoJs6 إلى الإصدار الداخلي 5237 أو أحدث, ثم اخرج من AutoJs6 بالكامل وأعد تشغيله.

******

### الأسئلة الشائعة

******

#### كيف أتأكد من أن الإضافة تعمل?

افتح مركز الإضافات في AutoJs6; فرؤية إضافة `OpenCV` هناك تعني أن المضيف قد تعرف عليها. ثم شغل أي برنامج نصي يستخدم واجهات برمجة تطبيقات الصور; فعودة النتائج بشكل طبيعي تعني أن المكتبة الأصلية حملت بنجاح.

#### لماذا لا توجد أيقونة للإضافة في قائمة التطبيقات?

هذا متوقع. فليس للإضافة واجهة مستقلة ولا تنشئ أيقونة تشغيل; وبعد التثبيت يتولى AutoJs6 اكتشافها وتشغيلها بالكامل في الخلفية, ويجري كل تفاعل داخل AutoJs6.

#### اختلت ميزات الصور بعد تحديث الإضافة, أو يبدو أن الإصدار القديم ما زال نشطا?

بعد تحميل المكتبة الأصلية تبقى موجودة ما دامت عملية المضيف قائمة, لذلك لا يستبدل تحديث الإضافة المكتبة المستخدمة فعلا. أغلق AutoJs6 تماما ثم أعد تشغيله لتسري المكتبة الأصلية الجديدة.

#### ماذا أفعل إذا ظهر أن المضيف قديم جدا أو غير متوافق?

تتطلب الإضافة بنية AutoJs6 داخلية برقم 5237 أو أعلى, لذا حدث AutoJs6 أولا. وقبل التحميل يتحقق المضيف من إصدار العقد وبصمة Java API, ويرفض التحميل عند أي عدم تطابق بدلا من العمل بمخاطر خفية.

#### الإضافة مثبتة لكن ميزات الصور ما زالت لا تعمل, فما السبب المحتمل?

السبب الأكثر شيوعا هو ملف APK غير مطابق لمعمارية الجهاز: فالحزمة أحادية ABI لا تعمل إلا على معماريتها. جرب التحول إلى حزمة `universal` لاستبعاد هذا العامل; فإن استمرت المشكلة, فتأكد من أن إصدار AutoJs6 يفي بالمتطلب وأعد المحاولة بعد إعادة تشغيل AutoJs6.

#### هل تتصل الإضافة بالشبكة أو تطلب أذونات حساسة?

لا. فبيانها لا يتضمن أذونات الشبكة أو التخزين أو الكاميرا أو غيرها من أذونات النظام الحساسة; ولا تعلن إلا إذن الإضافات المستخدم للتواصل مع AutoJs6. ومهمتها الوحيدة تسليم مكتبة OpenCV الأصلية إلى المضيف.

#### لماذا OpenCV 4.8.0 وليس إصدارا أحدث?

يجب أن تطابق المكتبة الأصلية تماما واجهة OpenCV Java API التي يحتفظ بها المضيف (يتم التحقق عبر بصمة SHA-256), لذلك فإن إصدار OpenCV مقيد بالعقد بين المضيف والإضافة. وستتوفر إصدارات OpenCV الأحدث كمتغيرات جديدة بعد أن يدعمها المضيف; راجع [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md) لمتابعة التقدم.

******

### الأذونات والأمان

******

يعمل الكود الأصلي في العملية نفسها التي يعمل فيها المضيف, لذلك تطبق خطوط دفاع متعددة من البناء إلى التحميل:

- مصدر قابل للتدقيق: أعيد بناء المكتبات الأصلية من إيداع مثبت في شجرة مصادر OpenCV الرسمية, مع تسجيل إصدارات سلسلة الأدوات وتجزئات كل ABI في `libs/opencv-native-4.8.0.provenance.json`; ويمكن لأي شخص إعادة البناء والمقارنة باتباع [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).
- بوابات بناء: يتحقق كل بناء من قائمة المكتبات الأصلية ومعماريات ELF وتجزئات الحمولة وبصمة Java API وفئات OpenCV Java المكررة وملفات الترخيص; وتتطلب البنيات القابلة للنشر أيضا هوية توقيع موثوقة.
- تحقق قبل التحميل: يفحص المضيف بالترتيب توقيع الإضافة وإصدار OpenCV وإصدار العقد وبصمة Java API SHA-256, ويرفض التحميل عند أي عدم تطابق.
- بيئة تشغيل C++ على مستوى العملية يوفرها المضيف ويحملها مسبقا; ولا تحمل الإضافة `libc++_shared.so`, مما يجنبها الأعطال عند حدود بيئات التشغيل غير المتوافقة.
- أدنى الامتيازات: لا أذونات شبكة ولا أذونات نظام حساسة, ولا واجهة مستقلة, والتواصل مع المضيف لا يتم إلا عبر إذن إضافات AutoJs6.

لا تثبت الإضافة إلا من صفحة [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) الرسمية أو من قنوات موثوقة أخرى. تستخدم الحزم الرسمية هوية توقيع يتعرف عليها المضيف; أما الحزم مجهولة المصدر فقد لا تجتاز تحقق المضيف أو قد تخفي مخاطر حتى لو بدا رقم الإصدار مطابقا.

******

### واجهة الإضافة

******

المعلومات التالية موجهة لمطوري مضيف AutoJs6 ومطوري الإضافات; يستخدم المضيف هذه المعرفات لاكتشاف الإضافة والتفاوض على التوافق:

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

تستجيب خدمة `OpenCvPluginInfoService` واحدة للإجرائين `org.autojs.plugin.INFO` و`org.autojs.plugin.OPENCV`, وكلاهما مع الفئة `opencv-runtime`; وواجهة Binder هي `IPluginInfoProvider` من common-plugin-api.

يحسب `PluginInfo.supportedAbis` ديناميكيا من إدخالات مكتبة OpenCV الأصلية الموجودة فعليا في ملف APK المثبت: تبلغ الحزمة أحادية ABI عن معماريتها فقط, بينما تبلغ حزمة `universal` عن المعماريات الأربع كلها.

******

### خارطة الطريق

******

تدون القدرات المخططة للإضافة وحالة إنجازها في ROADMAP.md بوصفها قائمة قابلة للتأشير, منظمة حسب مراحل رئيسية مع شروط قبول, وتشمل دعم صفحات الذاكرة بحجم 16 KB وتطور إصدارات OpenCV والتكامل المستمر وأدوات التشخيص. البنود غير المؤشرة تعبر عن نوايا لا عن قدرات صادرة; ونرحب بالنقاش عبر Issues.

- [عرض ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### سجل الإصدارات

******

#### v1.1.0

_2026/08/31_

- `ميزة` تتضمن تنزيلات الإصدار الآن حزم APK الخمس للبنى المختلفة و`SHA256SUMS.txt` وبيان مصدر البناء الأصلي لاختيار الحزمة المناسبة والتحقق من الملفات بصورة مستقلة
- `إصلاح` أعيد بناء المكتبات الأصلية لكل معماريات ABI الأربع بمحاذاة `PT_LOAD` مقدارها 16 KB وأضيفت بوابات تحقق للإصدار كي يعمل OpenCV على أجهزة Android ذات صفحات 16 KB مع الحفاظ على التوافق مع أجهزة 4 KB
- `تحسين` أضيفت إلى تعليمات مركز الإضافات وREADME بعشر لغات لقطة حقيقية لحالة التمكين وفحص ذاتي قابل للتشغيل يعرض إصدار OpenCV ومعمارية ABI للعملية
- `تحسين` أصبح اكتشاف معماريات ABI المدعومة متيناً مع تثبيتات universal وأحادية ABI وsplit ويواصل الفحص عند فقد مسارات APK أو تلفها ويعود إلى المكتبة الأصلية المستخرجة عند الحاجة

#### v1.0.0

_2026/07/22_

- `ميزة` الإصدار الأول: يوفر بيئة تشغيل OpenCV 4.8.0 الأصلية التي تقوم عليها واجهات برمجة تطبيقات الصور في AutoJs6; يحتفظ المضيف بواجهة OpenCV Java API التي تستدعيها البرامج النصية, بينما تحمل الإضافة `libopencv_java4.so` المطابقة لها تماما
- `ميزة` اكتشاف تلقائي وتفاوض على التوافق مع AutoJs6 عبر الإجرائين `org.autojs.plugin.INFO` و`org.autojs.plugin.OPENCV` (الفئة `opencv-runtime`), مع تزويد المضيف ببيانات وصفية عن الإصدار والعقد والبصمة
- `ميزة` خمس حزم APK: `arm64-v8a` و`armeabi-v7a` و`x86` و`x86_64` إضافة إلى حزمة `universal` الشاملة, مع الإبلاغ الديناميكي عن المعماريات المدعومة وفق المحتوى الفعلي لملف APK
- `ميزة` بيانات الإضافة والتعليمات وREADME وسجل التغييرات متوفرة بعشر لغات: الصينية المبسطة والصينية التقليدية (هونغ كونغ وتايوان) والإنجليزية والفرنسية والإسبانية واليابانية والكورية والروسية والعربية
- `ميزة` بوابات بناء مدمجة تتحقق من قائمة المكتبات الأصلية ومعماريات ELF وتجزئات الحمولة وبصمة Java API وفئات OpenCV Java المكررة وملفات الترخيص; وتتطلب البنيات القابلة للنشر أيضا هوية توقيع موثوقة
- `ميزة` النصوص الكاملة لتراخيص OpenCV 4.8.0 ومكوناته الخارجية المرتبطة ربطا ثابتا مضمنة في كل ملف APK; ويوفر مضيف AutoJs6 المتوافق `libc++_shared.so` ويحمله مسبقا بدلا من تكراره في الإضافة
- `إصلاح` أعيد بناء مكتبات OpenCV 4.8.0 الأصلية من المصادر الرسمية باستخدام Android NDK 26 (API 24) لتتشارك الإضافة والمضيف عائلة بيئة تشغيل C++ نفسها, مما يصلح أعطال AutoJs6 الناتجة عن عبور الاستثناءات حدود بيئات تشغيل غير متوافقة

##### لمزيد من سجل الإصدارات

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ar.md)

******

### البناء والتحقق

******

هذا القسم موجه للمطورين الراغبين في بناء الإضافة من الشفرة المصدرية.

بناء ملفات APK من نوع debug:

```powershell
.\gradlew.bat :app:assembleDebug
```

شغل اختبارات الوحدة وتحقق من سلامة ملفات APK من نوعي debug وrelease:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

قبل النشر, اضبط هوية توقيع موثوقة في ملف `sign.properties` الذي يتجاهله Git, ثم شغل:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

أنشئ ملفات APK الخمسة الموقعة وبيان SHA-256 ووصف GitHub Release (راجع [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md) للاطلاع على العملية الكاملة):

```bat
scripts\release\prepare-release.bat
```

من دون `sign.properties`, لا يزال من الممكن بناء الحمولات والتحقق منها, لكن ملفات APK الناتجة للإصدار غير موقعة ويجب عدم نشرها.

تستخدم البنيات الاعتيادية ملف AAR الأصلي المبني مسبقا الموجود في المستودع, فلا حاجة إلى تجميع OpenCV محليا; ولإعادة البناء الكامل من المصادر الرسمية ومطابقة provenance, راجع [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### الترجمة وتوليد الوثائق

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

يحتوي `strings.xml` على وصف الإضافة المترجم, ويحتوي `plugin_instruction.md` على التعليمات التي يعرضها المضيف. لتعديل README وسجل التغييرات عدل دائما مصادر JSON في `.readme/` و`.changelog/` ثم شغل `py .python/generate_markdown.py` لإعادة التوليد; الملفات المولدة لا تحرر يدويا أبدا. شغل `py .python/generate_markdown.py --check` للتحقق من تزامن المصادر والملفات المولدة.

******

### الترخيص

******

يتم توزيع رمز المشروع بموجب [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). راجع [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) للاطلاع على تراخيص OpenCV 4.8.0 ومكوناته الخارجية المرتبطة ربطا ثابتا. يتم تضمين نصوص التراخيص الكاملة في كل ملف APK تحت `assets/licenses/opencv-4.8.0/`.

******

### الروابط

******

- وثائق AutoJs6: https://docs.autojs6.com
- الموقع الرسمي لـ OpenCV: https://opencv.org
- الشفرة المصدرية لـ OpenCV 4.8.0: https://github.com/opencv/opencv/tree/4.8.0
