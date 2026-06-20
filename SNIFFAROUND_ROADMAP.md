# 🐾 SniffAround — Build Roadmap

Track progress by checking items off as you build. Organized by phase, then by layer (entities → repositories → services → controllers → security → tests → infra) so you can see exactly what's left in each slice.

---

## Legend

- [ ] Not started
- [x] Done
- 🔁 = revisit/refactor later
- 🧪 = has dedicated test coverage expected

---

## Phase 0 — Project Setup

- [X] Initialize Spring Boot project (Spring Initializr: Web, JPA, Security, Validation, PostgreSQL driver)
- [X] Set up project structure (`controller`, `service`, `repository`, `entity`, `dto`, `mapper`, `config`, `exception`, `security`)
- [X] Configure `application.yml` (dev / test / prod profiles)
- [ ] Set up Flyway (`src/main/resources/db/migration`)
- [ ] Add Docker Compose (PostgreSQL, Redis, RabbitMQ, MinIO)
- [ ] Set up Testcontainers base test config
- [X] Configure Lombok (or commit to records/plain getters — pick one and be consistent)
- [X] Add MapStruct dependency + annotation processor
- [ ] Set up SpringDoc OpenAPI (`/swagger-ui.html`)
- [X] Set up GitHub Actions CI (build + test on push)
- [ ] Add `.editorconfig` + Checkstyle/Spotless for formatting consistency

---

## Phase 1 — Core Entities & Database

### 1.1 Entities

- [X] `User` entity
- [X] `Pet` entity
- [X] `Community` entity
- [X] `CommunityMember` entity (join table w/ role)
- [X] `Post` entity
- [X] `Comment` entity (self-referential `parentId` for threads)
- [X] `Reaction` entity
- [X] `Event` entity
- [X] `EventRsvp` entity
- [X] `MarketplaceListing` entity
- [x] `MediaFile` entity (polymorphic: `entityType` + `entityId`)
- [x] `Notification` entity
- [x] `RefreshToken` entity (for JWT rotation)

### 1.2 Enums

- [x] `Role` (`USER`, `MODERATOR`, `ADMIN`)
- [ ] `CommunityRole` (`MEMBER`, `MODERATOR`, `ADMIN`)
- [ ] `PetSpecies` (`DOG`, `CAT`, `BIRD`, `RABBIT`, `OTHER`)
- [ ] `PostCategory` (`VETS`, `FOOD`, `STORES`, `GROOMING`, `GENERAL`)
- [ ] `ReactionType` (`LIKE`, `HELPFUL`, `LOVE`)
- [ ] `RsvpStatus` (`GOING`, `MAYBE`, `NOT_GOING`)
- [ ] `NotificationChannel` (`EMAIL`, `PUSH`, `IN_APP`)
- [ ] `NotificationType` (`NEW_POST`, `NEW_COMMENT`, `EVENT_REMINDER`, `RSVP_UPDATE`)

### 1.3 Database Migrations (Flyway)

- [ ] `V1__create_users_table.sql`
- [ ] `V2__create_pets_table.sql`
- [ ] `V3__create_communities_table.sql`
- [ ] `V4__create_community_members_table.sql`
- [ ] `V5__create_posts_table.sql`
- [ ] `V6__create_comments_table.sql`
- [ ] `V7__create_reactions_table.sql`
- [ ] `V8__create_events_table.sql`
- [ ] `V9__create_event_rsvps_table.sql`
- [ ] `V10__create_marketplace_listings_table.sql`
- [ ] `V11__create_media_files_table.sql`
- [ ] `V12__create_notifications_table.sql`
- [ ] `V13__add_indexes.sql` (feed query, FTS, notifications, events)
- [ ] `V14__add_refresh_tokens_table.sql`

### 1.4 🧪 Entity-layer tests

- [ ] Repository tests for `UserRepository` (Testcontainers + `@DataJpaTest`)
- [ ] Repository tests for `PetRepository`
- [ ] Repository tests for `CommunityRepository`
- [ ] Repository tests for `PostRepository` (custom `JOIN FETCH` queries)
- [ ] Repository tests for `EventRepository` (date-range queries)
- [ ] Repository tests for `NotificationRepository` (pagination + filtering)
- [ ] Test cascade behavior: deleting a `Post` removes its `Comment`s (`orphanRemoval`)
- [ ] Test unique constraint: one `Reaction` per `(user, post)`
- [ ] Test optimistic locking on `Event` (`@Version` conflict → `OptimisticLockException`)

---

## Phase 2 — Auth & Users Module

### 2.1 Core implementation

- [X] `AuthController` (`/register`, `/login`, `/refresh`, `/logout`)
- [X] `UserController` (`/users/me`, `/users/{id}`)
- [X] `AuthService` (registration, login, token refresh logic)
- [X] `UserService` (profile read/update)
- [X] Password hashing with `BCryptPasswordEncoder`
- [X] JWT utility class (`JwtTokenProvider`) — issue, parse, validate
- [X] `JwtAuthenticationFilter` (intercepts requests, sets `SecurityContext`)
- [X] `SecurityFilterChain` config (stateless, public/protected route rules)
- [X] Refresh token rotation + revocation on logout
- [X] DTOs: `RegisterRequest`, `LoginRequest`, `AuthResponse`, `UserProfileResponse`, `UpdateProfileRequest`
- [X] `UserMapper` (MapStruct)
- [X] Global exception handler (`@RestControllerAdvice`)
- [X] Custom exceptions: `ResourceNotFoundException`, `DuplicateEmailException`, `InvalidCredentialsException`, `TokenExpiredException`

### 2.2 🧪 Tests

- [ ] **Unit test** — `AuthService.register()` throws `DuplicateEmailException` on existing email
- [ ] **Unit test** — `AuthService.login()` returns valid JWT pair on correct credentials
- [ ] **Unit test** — `AuthService.login()` throws `InvalidCredentialsException` on wrong password
- [ ] **Unit test** — `JwtTokenProvider` generates token with correct claims (`sub`, `role`, `exp`)
- [ ] **Unit test** — `JwtTokenProvider.validateToken()` rejects expired token
- [ ] **Unit test** — `JwtTokenProvider.validateToken()` rejects tampered/invalid signature
- [ ] **Unit test** — `UserMapper` correctly maps `User` → `UserProfileResponse` (no password leak)
- [ ] **Slice test (`@WebMvcTest`)** — `AuthController.register()` returns 400 on invalid body (missing email)
- [ ] **Slice test (`@WebMvcTest`)** — `AuthController.login()` returns 401 on bad credentials
- [ ] **Integration test (`@SpringBootTest` + Testcontainers)** — full register → login → access protected endpoint flow
- [ ] **Integration test** — expired JWT on protected endpoint returns 401
- [ ] **Integration test** — refresh token flow issues new access token
- [ ] **Security test** — unauthenticated request to `/users/me` returns 401
- [ ] **Security test** — `/admin/**` endpoint blocked for `ROLE_USER`

---

## Phase 3 — Pets Module

### 3.1 Core implementation

- [ ] `PetController` (CRUD + photo upload)
- [ ] `PetService`
- [ ] `PetRepository`
- [ ] DTOs: `CreatePetRequest`, `UpdatePetRequest`, `PetResponse`
- [ ] `PetMapper`
- [ ] Ownership check: only the owner can update/delete their pet
- [ ] Pet photo upload wired to `FileStorageService` (Phase 7)

### 3.2 🧪 Tests

- [ ] **Unit test** — `PetService.create()` associates pet with authenticated user
- [ ] **Unit test** — `PetService.update()` throws `AccessDeniedException` if not owner
- [ ] **Unit test** — `PetService.delete()` removes pet and cascades media cleanup
- [ ] **Slice test** — `PetController` validation: missing `name` → 400
- [ ] **Slice test** — `PetController` validation: invalid `species` enum value → 400
- [ ] **Integration test** — create pet → fetch via `/users/me/pets` → appears in list
- [ ] **Integration test** — user A cannot delete user B's pet (403)

---

## Phase 4 — Communities Module

### 4.1 Core implementation

- [ ] `CommunityController` (create, browse/filter, join, leave, members list)
- [ ] `CommunityService`
- [ ] `CommunityMemberRepository`
- [ ] `CommunityPermissionEvaluator` (custom `@PreAuthorize` bean)
- [ ] DTOs: `CreateCommunityRequest`, `CommunityResponse`, `CommunityMemberResponse`
- [ ] `CommunityMapper`
- [ ] Filtering by `city` + `area` with `Specification` or custom JPQL
- [ ] Auto-assign creator as `ADMIN` role in `CommunityMember`

### 4.2 🧪 Tests

- [ ] **Unit test** — `CommunityService.join()` is idempotent (joining twice doesn't duplicate row)
- [ ] **Unit test** — `CommunityService.leave()` removes membership but not the community itself
- [ ] **Unit test** — creator is auto-assigned `ADMIN` role on creation
- [ ] **Unit test** — `CommunityPermissionEvaluator.isMember()` returns false for non-members
- [ ] **Unit test** — `CommunityPermissionEvaluator.isModerator()` returns true for `MODERATOR`/`ADMIN` roles only
- [ ] **Slice test** — filter query `?city=Nashik&area=West` returns scoped results
- [ ] **Integration test** — non-member cannot post in a community (403)
- [ ] **Integration test** — pagination on `/communities/{id}/members` returns correct page metadata

---

## Phase 5 — Posts, Comments & Reactions Module

### 5.1 Core implementation

- [ ] `PostController` (CRUD, feed with filter/sort)
- [ ] `CommentController` (create, threaded replies)
- [ ] `ReactionController` (toggle reaction)
- [ ] `PostService`, `CommentService`, `ReactionService`
- [ ] `PostRepository` with `JOIN FETCH` query for feed (avoid N+1)
- [ ] DTOs: `CreatePostRequest`, `PostResponse`, `CreateCommentRequest`, `CommentResponse`
- [ ] `PostMapper`, `CommentMapper`
- [ ] Domain event: `PostCreatedEvent` (published via `ApplicationEventPublisher`)
- [ ] Edit/delete authorization (owner or community moderator)
- [ ] View count increment logic (consider race-condition handling)

### 5.2 🧪 Tests

- [ ] **Unit test** — `PostService.create()` publishes `PostCreatedEvent`
- [ ] **Unit test** — `PostService.delete()` allowed for owner
- [ ] **Unit test** — `PostService.delete()` allowed for community moderator (non-owner)
- [ ] **Unit test** — `PostService.delete()` denied for regular member (non-owner, non-mod)
- [ ] **Unit test** — `ReactionService.toggle()` adds reaction if none exists
- [ ] **Unit test** — `ReactionService.toggle()` removes reaction if same type exists (toggle off)
- [ ] **Unit test** — `CommentService.create()` supports nested `parentId` replies
- [ ] **Unit test** — `CommentService.create()` throws if `parentId` belongs to a different post
- [ ] **Repository test** — `findByCommunityAndCategory()` returns posts sorted by `createdAt DESC` with author pre-fetched (assert no lazy-load exception outside transaction)
- [ ] **Slice test** — feed endpoint pagination defaults (`page=0&size=20`) applied when params omitted
- [ ] **Integration test** — full flow: create post → add comment → react → fetch post detail shows all three
- [ ] **Concurrency test** — two simultaneous reactions from same user on same post → only one persisted (unique constraint enforced)
- [ ] **Event listener test (`@RecordApplicationEvents`)** — `PostCreatedEvent` triggers notification fan-out

---

## Phase 6 — Events Module

### 6.1 Core implementation

- [ ] `EventController` (create, list upcoming, RSVP, attendees)
- [ ] `EventService`, `EventRsvpService`
- [ ] `EventRepository` (date-range queries)
- [ ] DTOs: `CreateEventRequest`, `EventResponse`, `RsvpRequest`, `AttendeeResponse`
- [ ] `EventMapper`
- [ ] Capacity enforcement with `@Version` optimistic locking
- [ ] Weather API integration on event creation (attach forecast snapshot)

### 6.2 🧪 Tests

- [ ] **Unit test** — `EventRsvpService.rsvp()` throws `CapacityExceededException` when full
- [ ] **Unit test** — `EventRsvpService.rsvp()` allows status change (`GOING` → `MAYBE`) without double-counting capacity
- [ ] **Unit test** — `WeatherClient` handles 4xx response by throwing `ExternalApiException`
- [ ] **Unit test** — `WeatherClient` call mocked via `MockRestServiceServer` or WireMock
- [ ] **Concurrency test** — simulate two threads RSVPing to the last spot; assert one succeeds, one gets `OptimisticLockException` → mapped to 409 Conflict
- [ ] **Repository test** — `findByEventTimeBetween()` returns only events in the 24–25hr reminder window
- [ ] **Integration test** — create event → RSVP → attendee appears in `/events/{id}/attendees`
- [ ] **Integration test** — event creation gracefully degrades if weather API times out (event still created, forecast null)

---

## Phase 7 — File Handling & Storage

### 7.1 Core implementation

- [ ] `FileStorageService` (upload, generate URL, delete)
- [ ] `MediaFileRepository`
- [ ] S3/MinIO client `@Configuration` bean
- [ ] File type + size validation (reject non-image, >5MB, etc.)
- [ ] Polymorphic linking: `entityType` + `entityId` on upload
- [ ] (Optional, advanced) Pre-signed URL upload flow for large files

### 7.2 🧪 Tests

- [ ] **Unit test** — `FileStorageService.upload()` rejects file >5MB with `FileTooLargeException`
- [ ] **Unit test** — `FileStorageService.upload()` rejects disallowed MIME type
- [ ] **Unit test** — `FileStorageService.upload()` generates correctly-namespaced S3 key
- [ ] **Integration test (Testcontainers MinIO module)** — upload real file → retrieve via generated URL
- [ ] **Integration test** — uploading photo for a pet creates corresponding `MediaFile` row with correct `entityType`/`entityId`

---

## Phase 8 — Scheduling & Background Jobs

### 8.1 Core implementation

- [ ] `@EnableScheduling` config
- [ ] `EventReminderJob` (hourly cron — 24hr-ahead reminders)
- [ ] `ExpiredListingCleanupJob` (daily cron)
- [ ] `DigestEmailJob` (optional — weekly community digest)
- [ ] Externalize cron expressions to `application.yml`

### 8.2 🧪 Tests

- [ ] **Unit test** — `EventReminderJob.sendUpcomingEventReminders()` queries correct time window
- [ ] **Unit test** — `EventReminderJob` only notifies users with `GOING` status (not `MAYBE`/`NOT_GOING`)
- [ ] **Unit test** — `ExpiredListingCleanupJob` deactivates listings older than threshold, leaves recent ones active
- [ ] **Integration test** — manually invoke job bean (don't wait for real cron) and assert side effects (notification rows created)

---

## Phase 9 — Async Processing & Messaging

### 9.1 Core implementation

- [ ] `@EnableAsync` config + custom `ThreadPoolTaskExecutor` bean
- [ ] RabbitMQ config: exchange, queue, binding (`notifications.exchange` → `notification.queue`)
- [ ] `NotificationPublisher` (produces to RabbitMQ)
- [ ] `NotificationConsumer` (`@RabbitListener`)
- [ ] `EmailService` (SendGrid/SES client wrapper)
- [ ] `PushService` (FCM client wrapper)
- [ ] Dead-letter queue config for failed notification delivery
- [ ] `NotificationController` (`GET /notifications`, `PATCH /notifications/{id}/read`)

### 9.2 🧪 Tests

- [ ] **Unit test** — `NotificationPublisher.publish()` sends correctly-shaped message to exchange (mock `RabbitTemplate`)
- [ ] **Unit test** — `NotificationConsumer` routes `EMAIL` channel to `EmailService`, `PUSH` to `PushService`
- [ ] **Unit test** — `NotificationConsumer` persists `Notification` row regardless of channel
- [ ] **Integration test (Testcontainers RabbitMQ module)** — publish message → consumer picks it up → `Notification` row exists in DB
- [ ] **Integration test** — simulate consumer failure → message lands in dead-letter queue
- [ ] **Async test** — verify `@Async` method actually runs off the calling thread (`Thread.currentThread().getName()` assertion)

---

## Phase 10 — External API Integration

- [ ] `WeatherClient` (`RestClient`-based, timeout + retry config)
- [ ] `MapsClient` (geocoding for community/event location → lat/lng)
- [ ] Centralized `RestClient` bean config (connection pool, timeouts, error handling interceptor)
- [ ] Circuit breaker / retry policy (Resilience4j — optional but recommended)
- [ ] 🧪 **Unit test** — `MapsClient.geocode()` parses lat/lng from mocked response
- [ ] 🧪 **Unit test** — `RestClient` timeout triggers `ExternalApiException`, doesn't propagate raw `IOException`
- [ ] 🧪 **Contract test** — WireMock stub matches real API response shape (catch breaking external API changes early)

---

## Phase 11 — Marketplace Module

- [ ] `MarketplaceController` (create, search/filter, deactivate)
- [ ] `MarketplaceService`
- [ ] `MarketplaceListingRepository` (search by category + keyword)
- [ ] DTOs: `CreateListingRequest`, `ListingResponse`
- [ ] Soft-delete (`active` flag) instead of hard delete
- [ ] 🧪 **Unit test** — `MarketplaceService.deactivate()` sets `active=false`, doesn't hard-delete
- [ ] 🧪 **Unit test** — search filters correctly combine `category` + free-text `q` param
- [ ] 🧪 **Integration test** — deactivated listings excluded from default search results

---

## Phase 12 — Caching & Performance

- [ ] Redis connection config (`spring-boot-starter-data-redis`)
- [ ] `@EnableCaching` + `RedisCacheManager` bean
- [ ] `@Cacheable` on `CommunityService.getCommunity()`
- [ ] `@CacheEvict` on community update/delete
- [ ] Cache TTL tuning per cache region (community: long, feed: short)
- [ ] Full-text search index (PostgreSQL `tsvector`) on posts + listings
- [ ] `EXPLAIN ANALYZE` review on top 5 slowest queries
- [ ] 🧪 **Unit test** — cache hit on second call (mock `CacheManager`, verify repository called only once)
- [ ] 🧪 **Integration test (Testcontainers Redis module)** — `@CacheEvict` actually clears stale data after update

---

## Phase 13 — Cross-Cutting: Validation, Errors, Observability

- [ ] Bean Validation on every request DTO (`@NotBlank`, `@Size`, `@Email`, `@Future`, etc.)
- [ ] `GlobalExceptionHandler` covers all custom exceptions with consistent `ErrorResponse` shape
- [ ] Structured JSON logging (Logback + `logstash-logback-encoder`)
- [ ] Correlation/request ID filter (MDC) for tracing a request across logs
- [ ] Spring Actuator endpoints (`/health`, `/metrics`, `/info`) — secured, not public
- [ ] Micrometer + Prometheus metrics export
- [ ] Rate limiting (Bucket4j) on auth endpoints (`/login`, `/register`)
- [ ] 🧪 **Unit test** — `GlobalExceptionHandler` maps each custom exception to correct HTTP status
- [ ] 🧪 **Unit test** — validation error response includes field-level messages
- [ ] 🧪 **Integration test** — rate limiter blocks 6th login attempt within window (429)

---

## Phase 14 — Security Hardening

- [ ] CORS config scoped to actual frontend origin(s) (not `*`)
- [ ] CSRF disabled only because stateless JWT (document why, don't cargo-cult)
- [ ] Password policy enforcement (min length, complexity) on registration DTO
- [ ] Account lockout / throttling after repeated failed logins
- [ ] Refresh token revocation list (logout invalidates token server-side)
- [ ] Role-based method security audit (`@PreAuthorize` on every mutating endpoint)
- [ ] Dependency vulnerability scan (OWASP Dependency-Check or Snyk in CI)
- [ ] 🧪 **Security test** — moderator of Community A cannot moderate posts in Community B
- [ ] 🧪 **Security test** — JWT signed with wrong secret is rejected
- [ ] 🧪 **Security test** — SQL injection attempt in search query param is neutralized (parameterized queries confirmed)

---

## Phase 15 — Test Suite Completion Checklist

Use this as a final coverage sweep — not phase-ordered, just "did I cover this kind of test anywhere."

- [ ] **Unit tests** — all `*Service` classes, mocking repositories/collaborators (JUnit 5 + Mockito)
- [ ] **Repository tests** — all custom `@Query` methods (`@DataJpaTest` + Testcontainers PostgreSQL)
- [ ] **Slice tests** — all controllers (`@WebMvcTest` + `MockMvc`, security context mocked via `@WithMockUser`)
- [ ] **Integration tests** — full request→DB→response flows (`@SpringBootTest(webEnvironment = RANDOM_PORT)` + Testcontainers, real HTTP via `TestRestTemplate` or `WebTestClient`)
- [ ] **Contract tests** — external API clients (WireMock stubs for Weather/Maps APIs)
- [ ] **Concurrency tests** — optimistic locking on `Event` RSVP, reaction uniqueness
- [ ] **Security tests** — auth boundaries, role checks, community-scoped permissions
- [ ] **Async/event tests** — `@Async` methods, `ApplicationEventPublisher` listeners, RabbitMQ pub/sub
- [ ] **Scheduled job tests** — manually-triggered job bean assertions
- [ ] **Cache tests** — hit/miss/eviction behavior
- [ ] **Mapper tests** — MapStruct entity↔DTO mapping correctness (especially nested objects, null-safety)
- [ ] **Validation tests** — every DTO constraint has at least one negative test case
- [ ] **End-to-end smoke test** — one full user journey (register → create pet → join community → post → comment → RSVP to event) run as a single integration test
- [ ] Code coverage report wired into CI (JaCoCo), target ≥ 80% on service layer
- [ ] Mutation testing pass (PIT) on critical modules (auth, RSVP capacity logic) — optional but valuable 🔁

---

## Phase 16 — DevOps & Deployment

- [ ] Multi-stage `Dockerfile` (build JAR → slim runtime image)
- [ ] `docker-compose.yml` for full local stack (app + Postgres + Redis + RabbitMQ + MinIO)
- [ ] Environment-specific config via env vars (no secrets in `application.yml`)
- [ ] CI pipeline: build → test → coverage report → Docker image push
- [ ] CD pipeline (manual trigger or auto-deploy to staging)
- [ ] Database migration runs automatically on deploy (Flyway baseline)
- [ ] Health check endpoint wired to deployment platform's readiness probe
- [ ] Basic load test (k6 or Gatling) on feed and RSVP endpoints before launch
- [ ] Backup strategy documented for PostgreSQL

---

## Phase 17 — Nice-to-Haves / Future

- [ ] Full-text search upgrade (Elasticsearch/OpenSearch) if Postgres FTS becomes a bottleneck
- [ ] Recommendation engine (suggest communities/events based on pet species + activity)
- [ ] Mobile push notification deep-linking
- [ ] Admin dashboard (separate Spring Boot module or just protected endpoints + simple UI)
- [ ] Multi-language support (i18n on validation messages, notification templates)
- [ ] GraphQL gateway as alternative to REST
- [ ] WebSocket support for live comment updates on popular posts

---

## Progress Summary

| Phase | Status |
|---|---|
| 0 — Setup | ⬜ |
| 1 — Entities & DB | ⬜ |
| 2 — Auth & Users | ⬜ |
| 3 — Pets | ⬜ |
| 4 — Communities | ⬜ |
| 5 — Posts/Comments/Reactions | ⬜ |
| 6 — Events | ⬜ |
| 7 — File Storage | ⬜ |
| 8 — Scheduling | ⬜ |
| 9 — Async/Messaging | ⬜ |
| 10 — External APIs | ⬜ |
| 11 — Marketplace | ⬜ |
| 12 — Caching | ⬜ |
| 13 — Cross-cutting | ⬜ |
| 14 — Security Hardening | ⬜ |
| 15 — Test Coverage Sweep | ⬜ |
| 16 — DevOps | ⬜ |
| 17 — Future | ⬜ |

*(Update manually: ⬜ Not started · 🟨 In progress · ✅ Done)*
