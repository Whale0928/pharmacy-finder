### What is this?

> It is an application that identifies pharmacy information based on location information.



----

#### Requirements Setting

- springboot 3.1.9
- jdk 17
- mariadb
- redis
- gradle
- docker
- kakao map api

### Reference

- [docker](https://www.docker.com/products/docker-desktop)
- [국립중앙의료원_전국 약국 정보 조회 서비스](https://www.data.go.kr/data/15000576/openapi.do)
- [KAKAO MAP API](https://apis.map.kakao.com/)

----

### How to use?

1. required environment value

```yml
KAKAO_REST_API_KEY=user key 
```

2. start docker desktop

```bash
docker-compose up 
```
