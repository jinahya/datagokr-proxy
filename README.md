# epost-openapi-proxy

## Abstract

An application for proxying [과학기술정보통신부 우정사업본부 APIs](https://www.data.go.kr/tcs/dss/selectDataSetList.do?dType=API&keyword=%EA%B3%BC%ED%95%99%EA%B8%B0%EC%88%A0%EC%A0%95%EB%B3%B4%ED%86%B5%EC%8B%A0%EB%B6%80+%EC%9A%B0%EC%A0%95%EC%82%AC%EC%97%85%EB%B3%B8%EB%B6%80&operator=AND&detailKeyword=&publicDataPk=&recmSe=N&detailText=&relatedKeyword=&commaNotInData=&commaAndData=&commaOrData=&must_not=&tabId=&dataSetCoreTf=&coreDataNm=&sort=&relRadio=&orgFullName=&orgFilter=&org=&orgSearch=&currentPage=1&perPage=10&brm=&instt=&svcType=&kwrdArray=&extsn=&coreDataNmArray=&pblonsipScopeCode=) using [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway).

## Routes

| service                                           | route.id                                         | notes |
|---------------------------------------------------|--------------------------------------------------|-------|
| [통합검색 5자리 우편번호 조회서비스][과학기술정보통신부 우정사업본부_우편번호 정보조회] | `retrieve_new_adress_area_cd_search_all_service` |       |      
| [새주소 5자리 우편번호 조회서비스][과학기술정보통신부 우정사업본부_도로명주소조회서비스] | `retrieve_new_adress_area_cd_service`            |       |     

## Application

### `@SpringBootApplication`

Add your own `@SprinbBootApplication` class.

e.g.

https://github.com/jinahya/epost-openapi-proxy/blob/75b114f36b20a12d1ba93ead76818959c11f5735/src/test/java/com/mycompany/Application.java#L1-L17

### `application.(properties|yaml)`

Put your own `application.(properties|yaml)` file.

See [application.yaml](src/test/resources/application.yaml), [application-development.yaml](src/test/resources/application-development.yaml), and [application-production.yaml](src/test/resources/application-production.yaml). 

#### development

Import `classpath:application-epost-openapi-proxy-development.yaml`.

#### production

Import `classpath:application-epost-openapi-proxy-development.yaml` and `classpath:application-epost-openapi-proxy-production.yaml`.

## Configuration

You can(or should) override various configuration properties through your own `application.(properties|yaml)` file.

### Timeouts

See [Http timeouts configuration](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/http-timeouts-configuration.html).


#### Global timeouts

> `connect-timeout` must be specified in milliseconds.
> 
> `response-timeout` must be specified as a java.time.Duration

e.g.

```yaml
spring:
  cloud:
    gateway:
      httpclient:
        connect-timeout: 1024  # connect-timeout must be specified in milliseconds.
        response-timeout: PT4S # response-timeout must be specified as a java.time.Duration
```

#### Per-route timeouts

> connect-timeout must be specified in milliseconds.
> 
> response-timeout must be specified in milliseconds.

Set with `<route-id>.connect-timeout` and `<route-id>.response-timeout`.

```yaml
retrieve_new_adress_area_cd_search_all_service:
  connect-timeout: 1024  # connect-timeout must be specified in milliseconds.
  response-timeout: 4096 # response-timeout must be specified in milliseconds.

```

### Caching

The [LocalResponseCache GatewayFilter Factory](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/gatewayfilter-factories/local-cache-response-filter.html) is already added.
> It accepts the first parameter to override the time to expire a cache entry (expressed in `s` for seconds, `m` for minutes, and `h` for hours) and a second parameter to set the maximum size of the cache to evict entries for this route (`KB`, `MB`, or `GB`).

Override preconfigured values with `<route-id>.cache.(time|size)`.

e.g.

```yaml
retrieve_new_adress_area_cd_search_all_service:
  cache:
    time: 10m
    size: 128MB
```

#### Disabling caching

Disable by overriding `spring.cloud.gateway.filter.local-response-cache.enabled` property.

```yaml
spring:
  cloud:
    gateway:
      filter:
        local-response-cache:
          enabled: false
```

## Links

### [github.com/spring-cloud/spring-cloud-gateway](https://github.com/spring-cloud/spring-cloud-gateway)

* [Double encoded URLs](https://github.com/spring-cloud/spring-cloud-gateway/issues/2065) (issues/2065)
* [Route Configuration Not Merging from Imported YAML Files](https://github.com/spring-cloud/spring-cloud-gateway/issues/3098)

[과학기술정보통신부 우정사업본부_우편번호 정보조회]: https://www.data.go.kr/data/15056971/openapi.do

[과학기술정보통신부 우정사업본부_도로명주소조회서비스]: https://www.data.go.kr/data/15000124/openapi.do
