# api-community-spring

### イベント追加リクエストの例（１つのスポーツ種目を含むイベント追加）
#### title  : イベント名
#### timeFrom :開始時間, timeTo : 終了時間
#### ownerId : イベント主催者のuser id
#### comment : コメント 
#### eventType : 1 公開イベント、2 クローズド開催（チーム内、団体内のみ）
#### sportEventIdList : このイベントに含まれるスポーツ種目

```
curl -XPOST -H "Content-Type: application/json"  http://localhost:8080/v1/api/event -d'{"title":"ボッチャ体験会","timeFrom":"2023-05-21 10:00","timeTo":"2023-05-21 14:00","ownerId":"xxxxx_owner_xxxx_id", "comment":"どなたでも参加いただけます","eventType":1, "sportEventIdList":[1]}'
```

### イベント追加リクエストの例（２つのスポーツ種目を含むイベント追加）
```
curl -XPOST -H "Content-Type: application/json"  http://localhost:8080/v1/api/event -d'{"title":"ボッチャ体験会Part2","timeFrom":"2023-05-21 12:00","timeTo":"2023-05-21 15:00","ownerId":"xxxxx_owner_xxxx_id", "comment":"どなたでも参加いただけます","eventType":1, "sportEventIdList":[1,2]}'
```

### イベント情報取得リクエストの例（eventId=4の場合）
```
curl -XGET http://localhost:8080/v1/api/event?eventId=4
```