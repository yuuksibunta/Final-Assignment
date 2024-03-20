# CRUD処理を備えたRestAPIの作成

### 概要

職員番号を主キーとし、氏名と年齢をカラムにもつSQLを作成しています。

### CRUD処理の実装  

#### Read処理
  - employeeNumber検索(パスパラメータ検索)の実装
  - 全件検索及び部分一致検索(クエリパラメータ)の実装
  - エラーハンドリングの実装  

社員情報全件取得のcURLコマンド
  ```
  curl http://localhost:8080/employees
  ```
指定した社員情報取得のcURLコマンド
  ```
  curl http://localhost:8080/employees/{社員番号}
  ```
---
#### Create処理
  - 新規登録機能の実装
  - レスポンスボディの設定
    - ステータスコード201でレスポンス
    - 新規登録時に「employee created」のメッセージを表示させる
  - エラーハンドリング
    - ageカラムが整数意外だった場合「整数を入力してください」というエラーメッセージを表示させる
    - Nameにバリデーションを追加する

社員情報作成のcURLコマンド
  ```
curl -X POST -H "Content-Type: application/json" -d '{"name": "{社員名}}", "age": {社員年齢}}' http://localhost:8080/employees
  ```
----
#### Update処理
  - nameを更新することができる
  - ageを更新することができる  

社員情報更新のcURLコマンド
  ```
curl -X PATCH -H "Content-Type: application/json" -d '{"name": "{更新する社員名前}", "age": {更新する社員年齢}}' http://localhost:8080/employees/{更新したい社員の番号}
  ```
----
#### Delete処理
  - 社員番号を指定し、存在する場合は削除することができる

社員情報削除のcURLコマンド
  ```
curl -X DELETE http://localhost:8080/employees/{削除したい社員の番号} 
  ```
----

### データベース作成時の内容

| **employeeNumber** | **name** | **age** |      
|:------------------:|:--------:|:-------:|  
|         1          |  スティーブ   |   21    |  
|         2          |   マーク    |   20    |  
|         3          |   ジェフ    |   30    |  

