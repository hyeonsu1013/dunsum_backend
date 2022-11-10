DUNSUM

# 버전 정보
```
  spring boot :: v2.7.4
```

***

# 트리구조
* backend (최상위 Directory)
  * common (운영에 필요한 모든 기능 - Config, Utils 등)
  * domains (domain 모음 - Controller, Service, Dao, Model 등)
  * batch (batch 관련 모음)

# 주요 기능
* Spring Security 및 jjwt Token 적용
* Batch Scheduler - Batch.java
* MyBatis - MySQL 연결
* Log4jdbc 사용 - logback(미구현)
* Custom RestTemplate → RestUtils (외부연동 Api)
* ControllerAuthAspect 구현 - 외부 Api 호출(구현) / 권한(미구현)
* 외부 API 호출 시 조회 이중화 작업 (외부 API 호출 실패 시 DB 조회)

# 공통 Service Class
* CommonService

# 공통 Utils Class
```
공통 Service와 공통 Utils의 차이
  Service : 운용에 필요한 공통 기능 (Method) Class
  Utils : 특정 Class 혹은 기능에 따른 모듈 Class
```
* DunsumStringUtils
* DunsumObjectUtils
* DunsumUrlUtils
* RandomUtils
* ModelUtils
* RestUtils
  * DnfApis


***

# TODO 개발사항
1. https 통신 구현 (현재 http 통신 중)

***

# 참고
1. secretApplication.yml 분리
```
  secretApplication.yml 파일 생성 및 gitIgnore 등록
  민감 정보 (DB 접속 정보, 개인 API-KEY 등) secretApplication.yml로 이관
  application.yml에서 secretApplication.yml import
  spriong boot version 확인 필수 (2.4.1 ↑)
```

