<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>AutoJs6 用 OpenCV 4.8.0 ネイティブランタイムプラグイン</p>

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

### 言語 (Languages)

******

現在の README.md は次の言語に対応しています:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- 日本語 [ja] # 現在
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### 概要

******

AutoJs6 OpenCV プラグインは AutoJs6 の画像 API が使用する OpenCV 4.8.0 ネイティブランタイムを提供します. 互換性を優先するこの設計では, ホストに OpenCV Java API を残し, プラグインから各 ABI に対応するネイティブライブラリを提供します.

******

### プラグイン契約

******

- アプリケーション ID: `io.github.supermonster003.autojs6.plugin.opencv`
- プラグイン ID: `opencv`
- エンジン: `opencv`
- バリアント: `4.8.0`
- 契約バージョン: `2`
- 必要なホストバージョンコード: `5237`
- ネイティブライブラリ: `opencv_java4`
- ネイティブ NDK バージョン: `26.1.10909125`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

同じ `OpenCvPluginInfoService` が `org.autojs.plugin.INFO` と `org.autojs.plugin.OPENCV` に応答します.

どちらのアクションも `opencv-runtime` カテゴリを使用します. Binder インターフェースは common-plugin-api の `IPluginInfoProvider` です.

******

### ABI

******

このプロジェクトは次の APK バリアントを生成します:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` はインストール済み APK 内の OpenCV ネイティブライブラリエントリから算出されます. 単一 ABI APK は自身の ABI のみを報告し, universal APK は 4 種類すべての ABI を報告します.

APK Builder で複数の ABI 向けアプリを生成する場合は, universal プラグイン APK をインストールしてください. 単一 ABI プラグインが提供できる OpenCV はその ABI 用のみです.

******

### ビルドと検証

******

単体テストを実行し, debug と release APK を検証:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

debug APK のみをビルドする場合:

```powershell
.\gradlew.bat :app:assembleDebug
```

公開前にバージョン管理対象外の `sign.properties` で信頼できる署名 ID を設定し, 次を実行してください:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

`sign.properties` を設定していない場合も payload のビルドと検証は可能ですが, 生成される release APK は未署名のため公開できません.

******

### 実行時の動作

******

プラグインには単独で操作するためのユーザーインターフェースはありません. インストールして有効化すると, AutoJs6 がサービスを検出し, ホスト側に残された Java API と一致するネイティブライブラリをロードします. ロードされたネイティブライブラリは通常ホストプロセスと同じライフサイクルを持つため, プラグインの更新後は AutoJs6 を再起動してください.

プラグインに含まれるのは `libopencv_java4.so` のみで, `libc++_shared.so` は同梱されません. 互換性のある AutoJs6 ホストはプロセス全体で使用するこの依存関係を提供して先にロードし, 1 つのプロセスで C++ ランタイムが 1 つだけ使われるようにする必要があります.

信頼できるビルドのみをインストールしてください. ホストはネイティブライブラリをロードする前に, プラグインの署名, OpenCV バージョン, 契約バージョン, Java API SHA-256 を検証します. 公式リリースではホストが認識する署名 ID を使用する必要があります.

******

### リリース履歴

******

# v1.0.0

###### 2026/07/22

* `機能` AutoJs6 向け OpenCV 4.8.0 ネイティブランタイムプラグインを初回リリース: ホスト側に OpenCV Java API を残し, プラグインから `libopencv_java4.so` を提供
* `機能` `org.autojs.plugin.INFO` と `org.autojs.plugin.OPENCV` および `opencv-runtime` カテゴリによる検出に対応し, ホストが必要とする互換性メタデータを追加
* `機能` `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, `universal` の各 APK バリアントを追加し, 実際にパッケージ化された ABI を動的に報告
* `機能` プラグイン情報, 使用説明, README, CHANGELOG をスペイン語, フランス語, ロシア語, アラビア語, 日本語, 韓国語, 英語, 簡体字中国語, 香港繁体字, 台湾繁体字にローカライズ
* `機能` debug と release の完全性検証でネイティブライブラリ構成, ELF アーキテクチャ, payload ハッシュ, Java API フィンガープリント, 重複する OpenCV Java クラス, ライセンスアセットを確認; 公開可能なビルドの検証では署名設定も必須
* `機能` OpenCV 4.8.0 と静的リンクされたサードパーティコンポーネントの完全なライセンステキストを各 APK に同梱; プラグインには `libc++_shared.so` を含めず, 互換性のある AutoJs6 ホストが提供して先にロードする必要あり
* `修正` OpenCV 4.8.0 ネイティブライブラリを Android NDK 26 と API 24 で再ビルドし, 例外が互換性のない C++ ランタイム境界を越えて AutoJs6 ホストをクラッシュさせる問題を回避

##### その他のリリース履歴

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ja.md)

******

### リソース構成

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` にはローカライズされたプラグイン説明が含まれ, `plugin_instruction.md` にはホストが表示する説明が含まれます. README と CHANGELOG は `.python/generate_markdown.py` により JSON ソースから生成されます. 各言語版の CHANGELOG 全文は `app/src/main/assets/doc` 以下にパッケージ化されます.

******

### ライセンス

******

プロジェクトコードは [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE) の下でライセンスされます. OpenCV 4.8.0 と静的リンクされたサードパーティコンポーネントのライセンスは [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) を参照してください. 完全なライセンステキストは各 APK の `assets/licenses/opencv-4.8.0/` に同梱されます.

******

### リンク

******

- AutoJs6 ドキュメント: https://docs.autojs6.com/
- OpenCV 公式サイト: https://opencv.org/
- OpenCV 4.8.0 ソースコード: https://github.com/opencv/opencv/tree/4.8.0
