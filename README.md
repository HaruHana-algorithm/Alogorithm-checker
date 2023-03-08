# 하루하나 알고리즘 풀이 체커

## 사용기술
- SpringBoot
- H2 Database

## 흐름도
- RestTemplate을 사용하여 특정 레포지토리의 유저 커밋정보를 가져온다.
- 커밋 정보가 오늘 날짜와 똑같다면 `:white_check_mark:` 를 표시


## 이슈 발생 및 해결-23.03.08
- 로직 및 DB 구조상 회원의 최신 커밋 하나만 Markdown 파일에 표시되는 이슈
- Redis를 이용하여 Cache에 회원 커밋정보를 임시저장하여 이를 나중에 Commit 테이블에 반영


## 알고리즘 월별 현황

- [23년 3월](https://github.com/HaruHana-algorithm/Alogorithm-checker/blob/main/2023_3.md)
