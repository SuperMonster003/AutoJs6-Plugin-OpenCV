<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>AutoJs6 用 OpenCV 4.8.0 ネイティブランタイムプラグイン</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

OpenCV プラグイン (OpenCV Plugin) は, AutoJs6 の画像処理に必要な OpenCV 4.8.0 ネイティブランタイムを提供します. AutoJs6 の画像検索, 色検出, テンプレートマッチングなどの画像 API はいずれも OpenCV による演算に依存しています. 本プラグインをインストールすると, プラグイン化された OpenCV に対応する AutoJs6 はこれらの画像機能をそのまま利用でき, スクリプト側の追加設定は一切不要です.

プラグインは互換性を優先した役割分担の設計を採用しています: AutoJs6 ホストにはスクリプトが直接呼び出す OpenCV Java API を残し, プラグインはそれと厳密に一致するネイティブライブラリ `libopencv_java4.so` を同梱します. これによりホストのインストールパッケージは軽量に保たれ, 端末は自身のプロセッサアーキテクチャ (ABI) に合ったプラグインパッケージだけをインストールすればよく, OpenCV ランタイムをホストとは独立して更新することもできます.

******

### 主な機能

******

- すぐに使える: インストール後の設定は一切不要で, AutoJs6 がプラグインを自動的に検出し, 画像スクリプトの実行時に必要に応じて OpenCV ランタイムをロードします.
- 5 種類のインストールパッケージ: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` の 4 種類の単一アーキテクチャパッケージと, 全アーキテクチャを含む `universal` パッケージを提供し, 端末ごとに必要なものだけを選べるためサイズを抑えられます.
- 公式ソースからビルド: ネイティブライブラリは改変されていない OpenCV 4.8.0 公式ソースから固定条件で再ビルドされ (NDK r26b, API 24), ビルド入力と ABI ごとのハッシュが provenance マニフェストに完全に記録されるため, 再現も照合も可能です.
- ロード前の多重検証: ホストはまずプラグインの署名, OpenCV バージョン, 契約バージョン, Java API フィンガープリントを検証し, すべて通過した場合のみネイティブライブラリをロードして, 一致しないランタイムや改ざんされたランタイムを拒否します.
- プロセス内で単一の C++ ランタイム: プラグインは `libc++_shared.so` を重複してパッケージ化せず, ホストが提供するプロセス全体の依存関係を共有することで, 複数の C++ ランタイムの共存によるクラッシュを防ぎます.
- 透明なライセンス: 各インストールパッケージに OpenCV とその静的リンクされたサードパーティコンポーネントの完全なライセンス原文を同梱し, 要約をまとめた THIRD_PARTY_NOTICES.md を併せて収録します.
- 多言語対応: プラグイン情報, 使用説明, README, CHANGELOG を 10 言語で提供します.

******

### 使い方

******

1. [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) ページから端末に合ったプラグイン APK をダウンロードし, AutoJs6 が動作する端末にインストールします. どれを選ぶか迷う場合は `universal` パッケージを選ぶか, 下記の `インストールパッケージの選び方` を参照してください.
2. AutoJs6 のプラグインセンターを開き, `OpenCV` プラグインが認識され有効になっていることを確認します.
3. 普段どおりにスクリプトを作成して実行します: スクリプトが画像 API を使用すると, AutoJs6 がプラグインの提供する OpenCV ネイティブライブラリを自動的にロードするため, スクリプトコードの変更は一切不要です.
4. プラグインの更新や再インストールの後は, まず AutoJs6 を完全に終了して再起動してから画像関連のスクリプトを実行し, 新しいバージョンのネイティブライブラリを確実に有効にしてください.

> プラグインセンターにこのプラグインが表示されない場合は, まず AutoJs6 を新しいバージョン (内部バージョンコード 5237 以上) に更新してください. プラグイン自体は Android 7.0 (API 24) 以上の端末に対応しています.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="AutoJs6 のプラグインセンターが OpenCV 1.1.0 を認識し, 有効状態で表示しています." width="480" />
</p>
<p align="center"><sub>AutoJs6 のプラグインセンターが OpenCV 1.1.0 を認識し, 有効状態で表示しています.</sub></p>

******

### インストールパッケージの選び方

******

各リリースには 5 つの APK が含まれ, 違いは同梱されるネイティブライブラリのアーキテクチャだけです:

| パッケージ | 適用対象 |
|---|---|
| `arm64-v8a` | 現代の Android スマートフォンとタブレットの大多数 (64 ビット ARM). 第一の選択肢 |
| `armeabi-v7a` | 比較的古い 32 ビット ARM 端末 |
| `x86_64` | 64 ビット x86 エミュレーターと一部の x86 端末 |
| `x86` | 32 ビット x86 エミュレーターと一部の x86 端末 |
| `universal` | 4 種類すべてのアーキテクチャを同梱しサイズは最大. あらゆる端末で動作し, アーキテクチャが分からないときの無難な選択肢 |

AutoJs6 の APK Builder で複数アーキテクチャ向けのアプリをパッケージ化する場合は, `universal` プラグインパッケージのインストールが必須です: 単一アーキテクチャのプラグインは自身のアーキテクチャ用の OpenCV しか提供できません. 端末のアーキテクチャに合わない単一アーキテクチャパッケージを誤ってインストールした場合, プラグインは利用可能なネイティブライブラリを提供できませんが, `universal` パッケージに入れ替えれば解決します.

******

### クイックセルフチェック

******

AutoJs6 のプラグインセンターで OpenCV が有効と表示されることを確認して AutoJs6 を再起動した後, 次のスクリプトを実行してください. `images.initOpenCvIfNeeded()` はメタデータの確認だけでなく, プラグイン検出, 互換性検証, ネイティブ読み込みを実際に行います:

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

成功すると `OpenCV version: 4.8.0` と `arm64-v8a` などのプロセス ABI が表示されます. 読み込みに失敗した場合は, `universal` パッケージのインストール, AutoJs6 の内部ビルド 5237 以降への更新, AutoJs6 の完全終了と再起動の順で確認してください.

******

### よくある質問

******

#### プラグインが有効になったことを確認するには?

AutoJs6 のプラグインセンターを開き, `OpenCV` プラグインが表示されていればホストに認識されています. 続いて画像 API を使用する任意のスクリプトを実行し, 正常に結果が返ってくればネイティブライブラリは正しくロードされています.

#### アプリ一覧にプラグインのアイコンがないのはなぜですか?

これは正常な動作です. プラグインには独立したインターフェースがなく, ホーム画面に起動アイコンも作成しません. インストール後は AutoJs6 がバックグラウンドで自動的に検出して呼び出し, すべての操作は AutoJs6 内で完結します.

#### プラグインの更新後に画像機能が不調になったり, まだ古いバージョンが使われている気がしたりするのですが?

ネイティブライブラリは一度ロードされるとホストプロセスと共に存続するため, プラグインを更新しても実行中の古いライブラリは置き換えられません. AutoJs6 を完全に終了して再起動すれば, 新しいバージョンのネイティブライブラリが有効になります.

#### ホストのバージョンが古すぎる, またはプラグインと互換性がないと表示されたらどうすればよいですか?

本プラグインは AutoJs6 の内部バージョンコード 5237 以上を必要とするため, まず AutoJs6 を更新してください. ホストはロード前に契約バージョンと Java API フィンガープリントを検証し, 両者が一致しない場合はリスクを抱えたまま動作するのではなく, ロード自体を拒否します.

#### プラグインをインストールしたのに画像機能が使えません. 原因は何でしょうか?

最も多い原因はインストールパッケージと端末アーキテクチャの不一致です: 単一アーキテクチャパッケージは自身のアーキテクチャでのみ機能します. まず `universal` パッケージに入れ替えてアーキテクチャ要因を除外してください. それでも解決しない場合は, AutoJs6 のバージョンが要件を満たしていることを確認し, AutoJs6 を再起動してから再試行してください.

#### プラグインはネットワークに接続したり機微な権限を要求したりしますか?

しません. プラグインのマニフェストにはネットワーク, ストレージ, カメラなどの機微なシステム権限は一切含まれず, AutoJs6 との通信に使うプラグイン権限のみを宣言します. その唯一の役割は, OpenCV ネイティブライブラリをホストに渡してロードさせることです.

#### なぜ提供されるのが OpenCV 4.8.0 で, より新しいバージョンではないのですか?

プラグインのネイティブライブラリはホストに残された OpenCV Java API と厳密に一致する必要があるため (SHA-256 フィンガープリントで検証), OpenCV のバージョンはホストとプラグインの契約によって固定されています. より新しい OpenCV バージョンはホストの対応後に新しいバリアントとして提供される予定で, 進捗は [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md) で確認できます.

******

### 権限とセキュリティ

******

ネイティブコードはホストと同じプロセスで動作するため, プラグインにはビルドからロードまで複数の防御線が設けられています:

- 検証可能な出所: ネイティブライブラリは OpenCV 公式ソースの固定コミットから再ビルドされ, ツールチェーンのバージョンと ABI ごとのハッシュが `libs/opencv-native-4.8.0.provenance.json` に記録されており, [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md) に従って独立に再現と照合ができます.
- ビルドゲート: すべてのビルドでネイティブライブラリの一覧, ELF アーキテクチャ, payload ハッシュ, Java API フィンガープリント, 重複する OpenCV Java クラス, ライセンスアセットを検証し, 公開用ビルドではさらに信頼できる署名 ID の設定を必須とします.
- ロード前の検証: ホストはプラグインの署名, OpenCV バージョン, 契約バージョン, Java API SHA-256 を順に検証し, いずれか 1 つでも一致しなければロードを拒否します.
- プロセス全体の C++ ランタイムはホストが一元的に提供して先にロードし, プラグインは `libc++_shared.so` を携えないため, 互換性のないランタイム境界に起因するクラッシュを防ぎます.
- 最小権限: ネットワーク権限を含むいかなる機微なシステム権限も要求せず, 独立したインターフェースを持たず, AutoJs6 プラグイン権限を通じてのみホストと通信します.

プラグインは必ず公式の [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) ページまたはその他の信頼できる入手先から取得してください. 公式リリースパッケージはホストが認識する署名 ID を使用しています. 出所不明のインストールパッケージは, たとえバージョン番号が同じでも, ホストの検証を通過できなかったりリスクが潜んでいたりする可能性があります.

******

### プラグインインターフェース

******

以下の情報は AutoJs6 ホストとプラグインの開発者向けです. ホストはこれらの識別子でプラグインを検出し, 互換性のネゴシエーションを行います:

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

同一の `OpenCvPluginInfoService` が `org.autojs.plugin.INFO` と `org.autojs.plugin.OPENCV` の 2 つの action に応答し, いずれも `opencv-runtime` category を使用します. Binder インターフェースは common-plugin-api の `IPluginInfoProvider` です.

`PluginInfo.supportedAbis` はインストール済み APK に実際に存在する OpenCV ネイティブライブラリのエントリから動的に算出されます: 単一アーキテクチャパッケージは自身のアーキテクチャのみを報告し, `universal` パッケージは 4 種類すべてのアーキテクチャを報告します.

******

### ロードマップ

******

プラグインの機能計画と進捗は ROADMAP.md でチェック可能なリストとして管理され, マイルストーンごとに整理されて受け入れ条件が付属します. 16 KB メモリページ対応, OpenCV バージョンの進化, 継続的インテグレーション, 診断体験などの方向を扱います. 未チェックの項目は現行バージョンの機能ではなく計画上の意向を示します. Issues での議論参加を歓迎します.

- [ROADMAP.md を見る](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### リリース履歴

******

#### v1.1.0

_2026/08/31_

- `機能` リリースのダウンロードに 5 種類のアーキテクチャ APK, `SHA256SUMS.txt`, ネイティブビルド provenance マニフェストをまとめ, 端末に合うパッケージの選択とファイルの独立検証を行えるようにしました
- `修正` 4 ABI のネイティブライブラリを 16 KB `PT_LOAD` アラインメントで再ビルドしリリースゲートを追加して, 4 KB 端末との互換性を保ちながら 16 KB page-size Android 端末で OpenCV をロードできるようにしました
- `改善` プラグインセンターの説明と 10 言語の README に実際の有効状態のスクリーンショットと, OpenCV バージョンおよびプロセス ABI を出力する実行可能なセルフチェックを追加しました
- `改善` 対応 ABI の検出を universal, 単一 ABI, split インストールに拡張し, APK パスの欠落や破損があっても走査を続けて展開済みネイティブライブラリへフォールバックできるようにしました

#### v1.0.0

_2026/07/22_

- `機能` 初の正式リリース: AutoJs6 の画像 API のために OpenCV 4.8.0 ネイティブランタイムを提供. ホストにはスクリプトが呼び出す OpenCV Java API を残し, プラグインはそれと厳密に一致する `libopencv_java4.so` を同梱
- `機能` AutoJs6 による自動検出と互換性ネゴシエーションに対応: `org.autojs.plugin.INFO` と `org.autojs.plugin.OPENCV` の action (`opencv-runtime` category) を通じて, バージョン, 契約, フィンガープリントなどのメタデータをホストに提供
- `機能` `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` の 4 種類の単一アーキテクチャパッケージと全アーキテクチャを含む `universal` パッケージを提供し, インストールパッケージの実際の内容に基づいて対応アーキテクチャを動的に報告
- `機能` プラグイン情報, 使用説明, README, CHANGELOG が 10 言語をカバー: 簡体字中国語, 香港繁体字, 台湾繁体字, 英語, フランス語, スペイン語, 日本語, 韓国語, ロシア語, アラビア語
- `機能` ビルドゲートを内蔵: ネイティブライブラリの一覧, ELF アーキテクチャ, payload ハッシュ, Java API フィンガープリント, 重複する OpenCV Java クラス, ライセンスアセットを検証し, 公開用ビルドではさらに信頼できる署名 ID の設定を必須化
- `機能` 各インストールパッケージに OpenCV 4.8.0 とその静的リンクされたサードパーティコンポーネントの完全なライセンス原文を同梱. `libc++_shared.so` は互換性のある AutoJs6 ホストが一元的に提供して先にロードし, プラグインでは重複してパッケージ化しない
- `修正` Android NDK 26 (API 24) を用いて公式ソースから OpenCV 4.8.0 ネイティブライブラリを再ビルドし, プラグインとホストが同一系統の C++ ランタイムを共用するようにして, 例外が互換性のないランタイム境界を越えて AutoJs6 をクラッシュさせる問題を修正

##### その他のリリース履歴

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ja.md)

******

### ビルドと検証

******

このセクションは, ソースからプラグインをビルドしたい開発者向けです.

debug APK をビルド:

```powershell
.\gradlew.bat :app:assembleDebug
```

単体テストを実行し, debug と release APK の完全性を検証:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

公開前にバージョン管理対象外の `sign.properties` で信頼できる署名 ID を設定し, 次を実行してください:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

署名済み APK 5 個, SHA-256 マニフェスト, GitHub Release 説明を生成 (完全な手順は [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md) を参照):

```bat
scripts\release\prepare-release.bat
```

`sign.properties` を設定していない場合も payload のビルドと検証は可能ですが, 生成される release APK は未署名のため公開できません.

通常のビルドはリポジトリ内のビルド済みネイティブライブラリ AAR をそのまま使用するため, OpenCV をローカルでコンパイルする必要はありません. 公式ソースから完全に再ビルドして provenance を照合したい場合は, [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md) を参照してください.

******

### ローカライズとドキュメント生成

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

`strings.xml` はローカライズされたプラグイン説明を提供し, `plugin_instruction.md` はホスト側に表示される使用説明を提供します. README と CHANGELOG は必ず `.readme/` と `.changelog/` 配下の JSON ソースファイルを編集してから `py .python/generate_markdown.py` を実行して再生成し, 生成物を手動で編集することはありません. `py .python/generate_markdown.py --check` を実行すると, ソースファイルと生成物が同期しているかを検証できます.

******

### ライセンス

******

プロジェクトコードは [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE) の下でライセンスされます. OpenCV 4.8.0 と静的リンクされたサードパーティコンポーネントのライセンスは [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) を参照してください. 完全なライセンステキストは各 APK の `assets/licenses/opencv-4.8.0/` に同梱されます.

******

### リンク

******

- AutoJs6 ドキュメント: https://docs.autojs6.com
- OpenCV 公式サイト: https://opencv.org
- OpenCV 4.8.0 ソースコード: https://github.com/opencv/opencv/tree/4.8.0
