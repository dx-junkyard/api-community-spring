# api-community-spring

## 概要
api-community-springは、スポコミ（Spocomi）プラットフォームのバックエンドAPIを提供するサービスです。コミュニティ管理、イベント管理、予約システムなどの機能をRESTful APIとして実装しています。

## 主な機能
- **コミュニティ管理API**  
  コミュニティの作成、編集、検索、メンバー管理などの機能を提供します。

- **イベント管理API**  
  コミュニティ内でのイベント作成、参加管理、検索機能を提供します。

- **予約システムAPI**  
  施設や備品の予約管理機能を提供します。

- **ユーザー認証API**  
  JWT（JSON Web Token）を使用したユーザー認証機能を提供します。

- **コミュニティネットワーキングAPI**  
  複数のコミュニティ間の連携を支援する機能を提供します。

## 技術スタック
- Spring Boot 2.6.0
- Java 11
- MyBatis
- MySQL
- JWT認証

## プロジェクト構成
本リポジトリは、スポコミのバックエンドAPI部分を管理しています。

### 主要コンポーネント
- **コントローラー**: RESTful APIエンドポイントを提供
- **サービス**: ビジネスロジックを実装
- **リポジトリ**: データアクセス層
- **ドメイン**: データモデル
- **設定**: アプリケーション設定

### APIエンドポイント
- `/v1/api/communities`: コミュニティ関連API
- `/v1/api/events`: イベント関連API
- `/v1/api/users`: ユーザー関連API

## セットアップ手順

### 前提条件
- Java 11
- MySQL 8.0
- Gradle

### データベース設定
1. MySQLにデータベースを作成します
```sql
CREATE DATABASE spocomidb;
CREATE USER 'spocomi'@'localhost' IDENTIFIED BY 'spocomi';
GRANT ALL PRIVILEGES ON spocomidb.* TO 'spocomi'@'localhost';
FLUSH PRIVILEGES;
```

2. アプリケーション設定を編集します
```
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/spocomidb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=spocomi
spring.datasource.password=spocomi
```

3. JWT認証の設定
```
# 環境変数または設定ファイルに追加
encrypt.jwt.secret=your_jwt_secret_key
encrypt.jwt.expire=3600000  # 1時間（ミリ秒）
```

4. ファイルアップロード設定
```
# 環境変数または設定ファイルに追加
file.upload-dir=/path/to/upload/directory/
file.image-dir=/images/
```

### ビルドと実行
```
./gradlew bootRun
```

## APIの使用例

### ユーザー登録
```
# 新規ユーザーを登録します(userA)
curl -XPOST -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/register' -d'{"name":"test userA", "email":"userA@spocomi.co.jp", "password":"pass"}'

# ユーザーをもう一人追加します(userB)
curl -XPOST -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/register' -d'{"name":"test userB", "email":"userB@spocomi.co.jp", "password":"pass"}'
```

### ログイン
```
# userAでloginしてtokenを取得します
tokenA=`curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/login' -d'{"email":"userA@spocomi.co.jp", "password":"pass"}'`

# userBでloginしてtokenを取得します
tokenB=`curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/login' -d'{"email":"userB@spocomi.co.jp", "password":"pass"}'`
```

### コミュニティ作成
```
# userAでコミュニティを作成します
curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/communities/community/new' -d'{"name":"西東京市サッカークラブ", "thumbnailMessage":"西東京市内のグランドで毎週土曜日9時からサッカーを楽しもう！週1回の活動で>、気軽に参加可能！","thumbnailPr":"週末に公園でサッカーを楽しみましょう！","description":"地域住民とのつながりを深めるために、週末にサッカーゲームを楽しんでいます。全年齢層が参加可能です。", "visibility":"1"}' | jq
```

### コミュニティリスト表示
```
# 全コミュニティのリストを表示します（認証不要）
curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/communities/communitylist' | jq

# userAが所属するコミュニティリストを表示します
curl -XGET -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/communities/mycommunitylist' | jq
```

### イベント登録
```
# ユーザーAでイベントを登録します
curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/events/event/new' -d'{"communityId":3, "title":"初心者向けヨガ体験イベント", "eventStart":"2024-10-22 10:00", "eventEnd":"2024-10-22 12:00", "applicationStart":"2024-08-22 00:00", "applicationEnd":"2024-09-28 00:01", "ownerId": "10838c04-1f68-4637-8c95-d7964aa4f0d9", "recruitmentMessage":"初めての方でも安心して参加できるよう準備しております！" , "description" : "（詳細）", "visibility":1, "status" : 0}' | jq
```

### イベント検索
```
# 「ヨガ」でイベントをキーワード検索
curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/events/keywordsearch/?keyword=%E3%83%A8%E3%82%AC' | jq
```

## フロントエンドとの連携
このバックエンドAPIは、[spocomi-frontend](https://github.com/dx-junkyard/spocomi-frontend)と連携して動作します。フロントエンドからのリクエストを受け取り、データベースとの通信を行い、結果をJSON形式で返します。

## 開発環境
- Java 11
- Spring Boot 2.6.0
- MySQL 8.0
- Gradle
