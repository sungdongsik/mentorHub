# mentorHub
멘토가 멘티를 찾는 커뮤니케이션!

## 프로젝트 실행 방법

### 1. Docker를 이용한 애플리케이션 및 MySQL 실행

프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 Spring Boot 애플리케이션과 MySQL 데이터베이스를 Docker 컨테이너로 실행합니다.

-   **기존 컨테이너 정리 (선택 사항):**
    ```bash
    docker-compose down -v
    ```
    (이전에 실행했던 컨테이너와 볼륨을 모두 삭제하여 깨끗한 상태로 시작합니다.)

-   **이미지 빌드 및 컨테이너 실행:**
    ```bash
    docker-compose up -d --build
    ```
    (백그라운드에서 이미지를 빌드하고 컨테이너를 실행합니다.)

-   **컨테이너 상태 확인:**
    ```bash
    docker-compose ps
    ```
    (실행 중인 컨테이너들의 상태를 확인합니다.)

-   **로그 확인:**
    ```bash
    docker-compose logs -f
    ```
    (애플리케이션 로그를 실시간으로 확인합니다. `Ctrl+C`로 종료할 수 있습니다.)

### 2. Qdrant 벡터 데이터베이스 실행

Qdrant는 `docker-compose.yml`에 포함되어 있지 않으므로 별도로 실행해야 합니다. 다음 Docker 명령어를 사용하여 Qdrant 컨테이너를 실행할 수 있습니다.

-   **Qdrant 컨테이너 실행:**
    ```bash
    docker run -d --name qdrant -p 6333:6333 -p 6334:6334 -v $(pwd)/qdrant_data:/qdrant/data qdrant/qdrant
    ```
    *   `-d`: 백그라운드에서 컨테이너를 실행합니다.
    *   `--name qdrant`: 컨테이너 이름을 `qdrant`로 지정합니다.
    *   `-p 6333:6333`: Qdrant 웹 UI 및 HTTP API 포트 (6333)를 호스트에 노출합니다.
    *   `-p 6334:6334`: Qdrant gRPC API 포트 (6334)를 호스트에 노출합니다. (애플리케이션에서 사용)
    *   `-v $(pwd)/qdrant_data:/qdrant/data`: 현재 디렉토리의 `qdrant_data` 폴더를 컨테이너 내부의 `/qdrant/data`에 마운트하여 Qdrant 데이터의 영속성을 보장합니다.

-   **Qdrant 상태 확인:**
    ```bash
    docker ps -f name=qdrant
    ```

-   **Qdrant 로그 확인:**
    ```bash
    docker logs -f qdrant
    ```

Qdrant가 실행되면 Spring Boot 애플리케이션은 `localhost:6334`를 통해 Qdrant에 연결하고, `mentee_writing_vectors` 컬렉션을 자동으로 생성할 것입니다.
