OpenCV プラグイン (OpenCV Plugin) は, AutoJs6 の画像処理に必要な OpenCV 4.8.0 ネイティブランタイムを提供します. AutoJs6 の画像検索, 色検出, テンプレートマッチングなどの画像 API はいずれも OpenCV による演算に依存しています. 本プラグインをインストールすると, プラグイン化された OpenCV に対応する AutoJs6 はこれらの画像機能をそのまま利用でき, スクリプト側の追加設定は一切不要です.

### 使い方

1. [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) ページから端末に合ったプラグイン APK をダウンロードし, AutoJs6 が動作する端末にインストールします. どれを選ぶか迷う場合は `universal` パッケージを選ぶか, 下記の `インストールパッケージの選び方` を参照してください.
2. AutoJs6 のプラグインセンターを開き, `OpenCV` プラグインが認識され有効になっていることを確認します.
3. 普段どおりにスクリプトを作成して実行します: スクリプトが画像 API を使用すると, AutoJs6 がプラグインの提供する OpenCV ネイティブライブラリを自動的にロードするため, スクリプトコードの変更は一切不要です.
4. プラグインの更新や再インストールの後は, まず AutoJs6 を完全に終了して再起動してから画像関連のスクリプトを実行し, 新しいバージョンのネイティブライブラリを確実に有効にしてください.

プラグインセンターにこのプラグインが表示されない場合は, まず AutoJs6 を新しいバージョン (内部バージョンコード 5237 以上) に更新してください. プラグイン自体は Android 7.0 (API 24) 以上の端末に対応しています.

### インストールパッケージの選び方

各リリースには 5 つの APK が含まれ, 違いは同梱されるネイティブライブラリのアーキテクチャだけです:

| パッケージ | 適用対象 |
|---|---|
| `arm64-v8a` | 現代の Android スマートフォンとタブレットの大多数 (64 ビット ARM). 第一の選択肢 |
| `armeabi-v7a` | 比較的古い 32 ビット ARM 端末 |
| `x86_64` | 64 ビット x86 エミュレーターと一部の x86 端末 |
| `x86` | 32 ビット x86 エミュレーターと一部の x86 端末 |
| `universal` | 4 種類すべてのアーキテクチャを同梱しサイズは最大. あらゆる端末で動作し, アーキテクチャが分からないときの無難な選択肢 |

AutoJs6 の APK Builder で複数アーキテクチャ向けのアプリをパッケージ化する場合は, `universal` プラグインパッケージのインストールが必須です: 単一アーキテクチャのプラグインは自身のアーキテクチャ用の OpenCV しか提供できません. 端末のアーキテクチャに合わない単一アーキテクチャパッケージを誤ってインストールした場合, プラグインは利用可能なネイティブライブラリを提供できませんが, `universal` パッケージに入れ替えれば解決します.

### クイックセルフチェック

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
