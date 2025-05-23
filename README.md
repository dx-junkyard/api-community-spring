# api-community-spring


### ユーザー登録
```
# 新規ユーザーを登録します(userA)
curl -XPOST -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/register' -d'{"name":"test userA", "email":"example-userA@example.com", "password":"pass"}'

# ユーザーをもう一人追加します(userB)
curl -XPOST -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/register' -d'{"name":"test userB", "email":"example-userB@example.com", "password":"pass"}'
```

### login
```
# userAでloginしてtokenを取得します
tokenA=`curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/login' -d'{"email":"example-userA@example.com", "password":"pass"}'`

# userBでloginしてtokenを取得します
tokenB=`curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/users/login' -d'{"email":"example-userB@example.com", "password":"pass"}'`
```


### userA, Bでコミュニティ作成
```
# userAでコミュニティを３つ作成します
curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/communities/community/new' -d'{"name":"西東京市サッカークラブ", "thumbnailMessage":"西東京市内のグランドで毎週土曜日9時からサッカーを楽しもう！週1回の活動で>、気軽に参加可能！","thumbnailPr":"週末に公園でサッカーを楽しみましょう！","description":"地域住民とのつながりを深めるために、週末にサッカーゲームを楽しんでいます。全年齢層が参加可能です。", "visibility":"1"}' | jq


curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/communities/community/new' -d'{ "name":"ランニングクラブA", "thumbnailMessage":"西東京市内の公園外周を毎週日曜日の朝7時からランニング。健康維持のため、週1で>気軽に参加!", "thumbnailPr":"健康維持のためにランニングしませんか！？", "description":"西東京市内の公園外周を走るランニングクラブです。週末の朝早くに集まり、気持ちの良いスタートを切ります。", "visibility":"1"}' | jq


curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/communities/community/new' -d'{"name":"ヨガ教室","thumbnailMessage":"西東京市内の区民館等で毎週木曜日18時からヨガ。週1のリラックスタイムを一緒に！","thumbnailPr":"毎週木曜、一緒にヨガしませんか？","description":"都心の喧騒を離れて、ヨガで心身ともに健康を目指します。初心者から上級者まで、どなたでも参加可能です。","visibility":"1"}' | jq


# userBでコミュニティを2つ作成します
curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenB}" 'http://localhost:8080/v1/api/communities/community/new' -d'{"name":"西東京バドミントンクラブ","thumbnailMessage":"西東京体育館で毎週水曜日20時からバドミントン！週1で気軽に楽しもう！","thumbnailPr":"毎週水曜日の夜、体育館で待っています！","description":"友達を作りながら、健康も手に入れることができます。初心者から経験者まで、皆さん大歓迎です！","visibility":"1"}' | jq

curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenB}" 'http://localhost:8080/v1/api/communities/community/new' -d'{"name":"緑区テニスサークル","thumbnailMessage":"西東京市の緑区テニスコートで毎週日曜日10時からテニス。週1のスポーツでリフレッ>シュ！","thumbnailPr":"楽しく運動できる環境です！","description":"初心者から経験者まで、テニスを通じて交流を深めるサークルです。","visibility":"1"}' | jq
```

### コミュニティリスト表示
```
# 全コミュニティのリスト（５つ）を表示します（認証不要）
curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/communities/communitylist' | jq

# userAが所属するコミュニティリス（３つ）を表示します
curl -XGET -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/communities/mycommunitylist' | jq
```

### userAでイベントを登録
```
# ユーザーAでイベントを登録します。
curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/events/event/new' -d'{"communityId":3, "title":"初心者向けヨガ体験イベント", "eventStart":"2024-10-22 10:00", "eventEnd":"2024-10-22 12:00", "applicationStart":"2024-08-22 00:00", "applicationEnd":"2024-09-28 00:01", "ownerId": "10838c04-1f68-4637-8c95-d7964aa4f0d9", "recruitmentMessage":"初めての方でも安心して参加できるよう準備しております！" , "description" : "（詳細）", "visibility":1, "status" : 0}' | jq


curl -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer ${tokenA}" 'http://localhost:8080/v1/api/events/event/new' -d'{"communityId":3, "title":"ボッチャ体験会", "eventStart":"2024-10-22 15:00", "eventEnd":"2024-10-22 17:00", "applicationStart":"2024-08-25 00:00", "applicationEnd":"2024-09-20 00:01", "ownerId": "10838c04-1f68-4637-8c95-d7964aa4f0d9", "recruitmentMessage":"初めての方でも安心して参加できるよう準備しております！" , "description" : "（詳細）", "visibility":1, "status" : 0}' | jq
```

### 「ヨガ」でイベントをキーワード検索
```
curl -XGET -H "Content-Type: application/json" 'http://localhost:8080/v1/api/events/keywordsearch/?keyword=%E3%83%A8%E3%82%AC' | jq
```

