# elastic search

엘라스틱서치 바이블을 읽고 정리, 실습하는 레포입니다.

## 1. elastic search 소개

엘라스틱서치는 오픈소스 기반의 분산 검색 및 분석 엔진입니다.   
분산 처리나 고가용성, 수평 확장성 등의 엔터프라이즈 영역에서 필요한 기능을 제공하며, 대용량의 데이터를 실시간으로 검색하고 분석할 수 있습니다.  
엘라스틱서치는 다양한 데이터 소스를 지원하며, JSON 형식의 문서를 인덱싱하고 검색할 수 있습니다.

### 검색엔진

엘라스틱 서치는 RDBMS나 다른 NoSQL 데이터베이스와는 다르게, 단순한 텍스트 매칭 검색이 아닌, 전문 검색(full-text search)을 지원해서 다양한 종류의 검색 쿼리를 작성할 수 있습니다.  
검색 엔진이기 때문에 역색인을 사용하여 빠른 검색 속도를 자랑하고, 다양한 애널라이저(tokenizer, filter 등)를 통해 여러 비즈니스 요구사항에 맞는 색인을 구성할 수 있고 형태소 분석을 통해 자연어
처리도 가능합니다.

### 분산 처리

엘라스틱서치는 분산 아키텍처를 기반으로 설계되어 있어, 데이터를 여러 노드에 분산 저장하고 처리할 수 있습니다.

### 고가용성

엘라스틱서치는 클러스터링 기능이 내장되어 있어서 노드 장애 시에도 데이터 손실 없이 서비스를 지속할 수 있습니다.  
만약 장애 발생 시 다시 복제본을 만들어 복제본의 개수를 유지하면서 노드 간 데이터의 균형(일관성, 무결성)을 보장합니다.

### 수평 확장

엘라스틱서치는 수평 확장이 용이하여, 데이터 양이 증가함에 따라 노드를 추가하여 클러스터의 용량과 성능을 향상시킬 수 있습니다.
더 많은 처리능력이 필요할 때 단순히 새로운 노드를 클러스터에 추가하는 것만으로 확장이 가능하고, 새 노드에 데이터를 복제하거나 재분배하는 작업도 엘라스틱서치가 자동으로 처리합니다.

### JSON 기반의 REST API 제공

엘라스틱서치는 JSON 기반의 RESTful API를 제공하여, HTTP 프로토콜을 통해 쉽게 데이터를 인덱싱하고 검색할 수 있습니다.  
이를 통해 다양한 프로그래밍 언어와 플랫폼에서 엘라스틱서치를 쉽게 사용할 수 있습니다.

### 데이터 안정성

데이터 색인 요청 후 200 OK를 받았다면 그 데이터는 확실하게 디스크에 저장된 것입니다.

### 다양한 플러그인을 통한 기능 확장 지원

엘라스틱서치는 핵심적인 기능들도 플러그인을 통해 제어할 수 있도록 설계되어 있습니다.  
그렇기 때문에 비즈니스 요구사항에 맞게 플러그인을 설치 혹은 커스터마이징하여 기능을 확장할 수 있습니다.

### 준실시간 검색

엘라스틱서치는 데이터를 색인한 후 거의 즉시 검색할 수 있는 준실시간 검색 기능을 제공합니다.  
엘라스틱서치는 데이터를 `색인하자마자` 조회하는 것은 가능하지만, 검색 요청은 성공하지 못할 가능성이 높습니다.  
이는 데이터를 색인한 후 역색인을 구성하고 이 역색인으로부터 검색이 가능한 상태가 되기까지 약간의 시간(기본값 1초)이 소요되기 때문입니다.

### 트랜잭션이 지원되지 않음

엘라스틱서치는 RDBMS와 달리 트랜잭션을 지원하지 않습니다.

### 조인을 지원하지 않음

엘라스틱서치는 조인을 지원하기는 하지만 성능이 매우 떨어지기 때문에 권장하지 않습니다.(사실상 아예 사용하지 않는 것이 좋습니다)  
이는 엘라스틱서치가 기본적으로 조인을 염두해두고 설계된 시스템이 아니기 때문입니다.

## 2. 엘라스틱서치 동작과 구조

### 문서 색인

엘라스틱서치는 데이터를 색인(index)이라는 단위로 저장합니다.
이 때 색인할 `인덱스 이름`과 `문서 ID(_id)`를 지정하고 문서의 내용을 JSON 형식으로 전달합니다.  
여기서 _id는 문서의 고유 식별자 역할을 하며, 지정하지 않으면 엘라스틱서치가 자동으로 생성합니다.

예시: 인덱스 이름이 `my_index`이고, 문서 ID가 `1`인 문서를 색인하는 예시입니다.

```bash
PUT /my_index/_doc/1
{
  "title": "Elasticsearch Basics",
  "content": "Elasticsearch is a distributed, RESTful search and analytics engine."
}
```

색인에 성공했다면 다음과 같은 응답을 받게 됩니다.

```json
{
  "_index": "my_index",
  "_type": "_doc",
  "_id": "1",
  "_version": 1,
  "result": "created",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 0,
  "_primary_term": 1
}
```

예시: id를 지정하지 않고 색인하는 예시입니다.

```bash
PUT /my_index/_doc
{
    "title": "Elasticsearch Basics",
    "content": "Elasticsearch is a distributed, RESTful search and analytics engine."
}

응답 예시:
```json
{
  "_index": "my_index",
  "_type": "_doc",
  "_id": "7YcHW4EBB3n5mX2xYz6G", // 엘라스틱서치가 자동으로 생성된 ID
  "_version": 1,
  "result": "created",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 0,
  "_primary_term": 1
}
```

#### 문서 조회

문서를 조회할 때는 인덱스 이름과 문서 ID를 지정하여 GET 요청을 보냅니다.

```bash
GET /my_index/_doc/1
```

응답 예시:

```json
{
  "_index": "my_index",
  "_type": "_doc",
  "_id": "1",
  "_version": 1,
  "found": true,
  "_source": {
    "title": "Elasticsearch Basics",
    "content": "Elasticsearch is a distributed, RESTful search and analytics engine."
  }
}
```

결과의 _source 필드에 문서의 실제 내용이 포함되어 있습니다.

#### 문서 수정

문서를 수정할 때는 _doc 대신 update를 사용해서 인덱스 이름과 문서 ID를 지정 후 POST 요청을 보냅니다.

```bash
POST /my_index/_update/1

{
  "doc": {
    "content": "Elasticsearch is a powerful distributed search and analytics engine."
  }
}

응답 예시:
```json
{
  "_index": "my_index",
  "_type": "_doc",
  "_id": "1",
  "_version": 2,
  "result": "updated",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 1,
  "_primary_term": 1
}
```

#### 문서 검색

엘라스틱서치는 다양한 검색 쿼리를 가지고 있고, 이를 위한 전용 Query DSL을 제공합니다.  
검색을 위해서는 _search 엔드포인트에 GET 혹은 POST 요청을 보냅니다.

```bash
GET /my_index/_search
{
  "query": {
    "match": {
      "content": "distributed"
    }
  }
}
```

혹은

```bash
POST /my_index/_search
{
    "query": {
        "match": {
            "title": "Elasticsearch Basics"
        }
    }
}
```

응답 예시:

```json
{
  "took": 10,
  "timed_out": false,
  "_shards": {
    "total": 2,
    "successful": 2,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 1,
      "relation": "eq"
    },
    "max_score": 0.2876821,
    "hits": [
      {
        "_index": "my_index",
        "_type": "_doc",
        "_id": "1",
        "_score": 0.5876821,
        "_source": {
          "title": "Elasticsearch Basics",
          "content": "Elasticsearch is a powerful distributed search and analytics engine."
        }
      },
      {
        "_index": "my_index",
        "_type": "_doc",
        "_id": "7YcHW4EBB3n5mX2xYz6G",
        "_score": 0.20536052,
        "_source": {
          "title": "Hello Elasticsearch",
          "content": "Elasticsearch is a distributed, RESTful search and analytics engine."
        }
      }
    ]
  }
}
```

title 필드에 지정한 검색어 Elasticsearch Basics로 문서 2건이 검색되었습니다.  
검색 결과를 보면 Hello Elasticsearch도 함께 검색되고, score 값이 다르게 나오는 것을 볼 수 있습니다.  
이는 엘라스틱서치가 RDBMS처럼 단순히 텍스트 매칭을 하는 것이 아니라, 문서를 분석해서 역색인을 만들고 검색어와 둘 사이의 유사도가 높은 문서를 찾기 때문입니다.  


#### 문서 삭제

문서를 삭제할 때는 인덱스 이름과 문서 ID를 지정하여 DELETE 요청을 보냅니다.

```bash
DELETE /my_index/_doc/1
```

응답 예시:

```json
{
  "_index": "my_index",
  "_type": "_doc",
  "_id": "1",
  "_version": 3,
  "result": "deleted",
  "_shards": {
    "total": 2,
    "successful": 1,
    "failed": 0
  },
  "_seq_no": 2,
  "_primary_term": 1
}
```

### 엘라스틱서치 구조 개괄
