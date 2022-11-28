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
* 로그인 및 회원가입 > Spring Security 및 jjwt Token 적용
* Batch Scheduler - Batch.java
* MyBatis - MySQL 연결
* Log4jdbc 사용 - logback(미구현)
* Custom RestTemplate → RestUtils (외부연동 Api)
* ControllerAuthAspect 구현 - 외부 Api 호출(구현) / 권한(미구현)
* 외부 API 호출 시 조회 이중화 작업 (외부 API 호출 실패 시 DB 조회)

# 로그인 및 회원가입
1. Guest 로그인
> 1. 접속 IP 단위로 생성
>   1. 접속 IP에 기등록된 Guest ID가 있다면 Guest 정보 호출
>   2. 최초 접근일 시 새로 생성
>   3. 연동된 회원가입 이력이 있다면 User 정보 반환 (미구현)
> 2. 최소한의 권한만 보유
> 3. 따라서 접속 장소가 변경되거나, 유동 IP를 사용할 경우 변동될 수 있음
> 4. 최종 접속 일자로부터 1주일동안 접속 없을 시 휘발 됨
> 5. Guest 상태의 정보를 토대로 회원가입 가능 (미구현)

2. User 회원가입 또는 로그인 (미구현 - 아래는 구현 예정 기능)
> 1. 가입 시 이메일 인증번호 체크
>    1. 비밀번호 찾기 기능 시 필요
>    2. 하나의 이메일로 최대 5개 가입 가능
> 2. 관리자 권한을 제외한 모든 권한 보유
> 3. 모든 입력 데이터는 User 단위로 저장

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
* MapperUtils
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

