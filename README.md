# Angel-Kitchen
오픈SW 개발자 대회 팀 LC 천사식당 앱 프로젝트

## About Angel-Kitchen
Angel-Kitchen 프로젝트 app frontend에 관한 내용을 담당합니다.
서버의 API에서 받아온 무료급식소 정보와 main 화면에는 naver Map API를 렌더링합니다.

### 기능
#### 1. 무료급식소 마커
Retrofit으로 서버와 HTTP 통신하여 사용자 위치 반경 내에 존재하는 무료급식소의 위치를 마커로 렌더링합니다.

#### 2. 즐겨찾기
Room Local Database를 사용하여 사용자가 원하는 급식소만을 저장하고 언제든지 저장한 내용을 확인 가능

#### 3. 무료급식소 검색
Retrofit으로 서버와 HTTP 통신하여 검색어에 맞는 무료급식소 위치 제공

#### 4. 무료급식소 경로안내
서버의 API에서 무료급식소의 정보를 검색하여 찾아 선택한 급식소를 naver Direction5 API로 경로를 안내합니다.

## Prerequisites
```
kotlin v14.16.0
```

## API & Open-source
https://navermaps.github.io/android-map-sdk/guide-ko/
> naver Map API

https://api.ncloud-docs.com/docs/ai-naver-mapsdirections
> naver Direction 5

## License
This project is licensed under the MIT License - see the [Angel-Kitchen_Server/LICENSE](LICENSE)
