DUNSUM
# 트리구조
* backend (최상위 Directory)
  * common (운영에 필요한 모든 기능 - Config, Utils 등)
  * domains (domain 모음 - Controller, Service, Dao, Model 등)
  * batch (batch 관련 모음)

# 주요 기능
* Batch Scheduler - Batch.java
* MyBatis - MySQL 연결
* Custom RestTemplate → RestUtils (외부연동 Api)
* ControllerAuthAspect 구현 - 외부 Api 호출(구현) / 권한(미구현)
* 외부 API 호출 시 조회 이중화 작업 (외부 API 호출 실패 시 DB 조회)

# 주요 Utils Class
* DunsumStringUtils
* DunsumObjectUtils
* DunsumUrlUtils
* RandomUtils
* ModelUtils
* RestUtils
  * DnfApis

