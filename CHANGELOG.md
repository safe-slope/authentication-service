# Changelog

## [1.5.1](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.5.0...authentication-microservice-v1.5.1) (2026-01-29)


### Bug Fixes

* update workflows to improve release conditions and automate staging deployment ([8301b2f](https://github.com/safe-slope/authentication-service/commit/8301b2ffa7de123d47dcdd56f1e4fd06ecee7f4a))

## [1.5.0](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.4.2...authentication-microservice-v1.5.0) (2026-01-28)


### Features

* add DataInitializer for test data setup in development environment ([d31c7ea](https://github.com/safe-slope/authentication-service/commit/d31c7ea4d879af067bff72d250f07f55c0cf5442))

## [1.4.2](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.4.1...authentication-microservice-v1.4.2) (2026-01-27)


### Bug Fixes

* conditionally run Qodana job and remove unnecessary dependency from release-please job ([0e9e3aa](https://github.com/safe-slope/authentication-service/commit/0e9e3aa6ff92c3760e792dbd7e0f85a4209a18e2))

## [1.4.1](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.4.0...authentication-microservice-v1.4.1) (2026-01-27)


### Bug Fixes

* update .gitignore to re-include docker-compose.yml and remove DockerfileDev ([837e249](https://github.com/safe-slope/authentication-service/commit/837e249c500e1662d9802259049052f00a058ad0))

## [1.4.0](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.3.1...authentication-microservice-v1.4.0) (2026-01-27)


### Features

* add PageableDefault annotation for better API defaults ([73a758d](https://github.com/safe-slope/authentication-service/commit/73a758d2d54c0428f13e10f0d3af10d154554260))
* add pagination support to user and tenant list endpoints ([854af54](https://github.com/safe-slope/authentication-service/commit/854af54b00dc35414c371c1af3fec3856dd356ed))
* add Spring Boot Actuator healthcheck endpoint for Kubernetes ([2de26b2](https://github.com/safe-slope/authentication-service/commit/2de26b2d24cb958f4ce012d5ca4fc59a2fb229c2))


### Bug Fixes

* set health endpoint show-details to never in production ([a6f61cd](https://github.com/safe-slope/authentication-service/commit/a6f61cd31df78b407161a5292ec6f4aee5a10c78))


### Documentation

* add Kubernetes health probe configuration to CONFIG.md ([a68237d](https://github.com/safe-slope/authentication-service/commit/a68237d227c10da2ef241e04e8e4a48c16ff8d7b))

## [1.3.1](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.3.0...authentication-microservice-v1.3.1) (2026-01-21)


### Bug Fixes

* add public key property for JWT in application configuration ([7cca6c5](https://github.com/safe-slope/authentication-service/commit/7cca6c5138e2fc90723604613dcf05978ebd0f07))
* add public key support and validation in JwtKeyProvider ([d224046](https://github.com/safe-slope/authentication-service/commit/d22404610047faaa558382ec0200dd545e4086b4))
* deprecate token expiration validation in JwtService ([fae2b49](https://github.com/safe-slope/authentication-service/commit/fae2b49e5ee96b07ec828ec9a9b93a0fbda79413))
* enforce single-line Base64 private key format in JwtKeyProvider ([79ab4ab](https://github.com/safe-slope/authentication-service/commit/79ab4ab02624b53c8f3dd51c63a40148b0070567))
* enforce single-line Base64 private key format in JwtKeyProvider ([9f28d39](https://github.com/safe-slope/authentication-service/commit/9f28d39e8144217da3c0f3c06d7b0fc3431870d0))
* update JwtService to use public key verification for JWT parsing ([3fd20d5](https://github.com/safe-slope/authentication-service/commit/3fd20d51a5c7912373073917afd9f7fcfc3f38bd))

## [1.3.0](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.2.0...authentication-microservice-v1.3.0) (2026-01-17)


### Features

* enhance CI workflow to upload JAR artifact and update Docker build process ([010b405](https://github.com/safe-slope/authentication-service/commit/010b40571fdd5991e6033d7f57efc8f7e6cb07b1))


### Bug Fixes

* correct Dockerfile to use specific JAR filename for COPY command ([30f72ed](https://github.com/safe-slope/authentication-service/commit/30f72edd2244882c5c29182ee16ce56704205ad1))
* update artifact path in CI workflow to match new JAR file location ([b58eea7](https://github.com/safe-slope/authentication-service/commit/b58eea7da99f2326c9ef77eb78897aca7bd15ba6))
* update target branch in CI workflow trigger to `main` ([9ac80b8](https://github.com/safe-slope/authentication-service/commit/9ac80b808f3734ddecf3c518ae00ff49d7fc47d0))

## [1.2.0](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.1.0...authentication-microservice-v1.2.0) (2026-01-16)


### Features

* add Mockito dependency to `pom.xml` files in authentication microservice ([04ccc88](https://github.com/safe-slope/authentication-service/commit/04ccc88d52bfb7cf81e91a4fcd3110903141b31a))
* configure application properties with environment-specific profiles ([58e3ae3](https://github.com/safe-slope/authentication-service/commit/58e3ae3b67503c51f81d4717c09dcb0170b7cbbb))


### Bug Fixes

* remove redundant application configuration properties in authentication microservice ([7cbbdc0](https://github.com/safe-slope/authentication-service/commit/7cbbdc033ae5235ed313aff8e89d3c0a69e11f59))
* rename database table `user` to `app_user` in `User` entity ([e777c2c](https://github.com/safe-slope/authentication-service/commit/e777c2cc7213a0590ae420edcc727798c239f86f))
* replace UserRepository with UserService in CustomUserDetailsService for improved code reuse ([3a916b4](https://github.com/safe-slope/authentication-service/commit/3a916b470501c465fe951a3463cb29666df3181d))
* update CustomUserDetailsServiceTest to use UserService instead of UserRepository ([13c5131](https://github.com/safe-slope/authentication-service/commit/13c51316e79b233b082368014545e01a8d876761))


### Documentation

* add configuration documentation for environment profiles ([c3b1751](https://github.com/safe-slope/authentication-service/commit/c3b1751de92b70a4652d0e6a856ec03db3b32059))

## [1.1.0](https://github.com/safe-slope/authentication-service/compare/authentication-microservice-v1.0.0...authentication-microservice-v1.1.0) (2026-01-16)


### Features

* add PostgreSQL dependency in `pom.xml` of authentication microservice ([0c14874](https://github.com/safe-slope/authentication-service/commit/0c14874f390c42ec75381c8a42d9e480cc701de9))


### Bug Fixes

* add missing `<relativePath>` to child module `pom.xml` files in authentication microservice ([607fb14](https://github.com/safe-slope/authentication-service/commit/607fb14bb52d1ec4dfe2042abc3e67dfcf5525bc))
* cleanup redundant dependency version entries in `pom.xml` of authentication microservice ([fb16a67](https://github.com/safe-slope/authentication-service/commit/fb16a670d11f3cc4de19d9cbf1e08d722f8d05b1))
* correct formatting issues in `pom.xml` of authentication microservice ([d461a17](https://github.com/safe-slope/authentication-service/commit/d461a173829c7ae551ae5709f47688eb6cb2786c))
* remove redundant `properties` and dependencies from `pom.xml` in authentication microservice ([4c412fc](https://github.com/safe-slope/authentication-service/commit/4c412fc0748387049d91cfda25dcbefec53a7da9))
* remove redundant Spring Boot dependencies from parent POM dependencyManagement ([e57c7aa](https://github.com/safe-slope/authentication-service/commit/e57c7aaae31817f3647296cb49deddcaa9c1cd99))
* remove Spring Boot dependencies from parent POM dependencyManagement section ([c1c2c9d](https://github.com/safe-slope/authentication-service/commit/c1c2c9dead6b4a0d2bb59c15aad929db51897e2f))
* remove unused JUnit dependency from `pom.xml` in authentication microservice ([19f5999](https://github.com/safe-slope/authentication-service/commit/19f59990ebdca5b0797abeb4ecb930c3a6cb0263))
* restructure and standardize dependency blocks in `pom.xml` of authentication microservice ([b5c1d48](https://github.com/safe-slope/authentication-service/commit/b5c1d48275280921b2a6cbb70e8c411966023b61))
* update `.gitignore` to scope `.env` to authentication microservice ([a62de67](https://github.com/safe-slope/authentication-service/commit/a62de6706f835dee0693216a67cb886180eecd15))
* Update Dockerfile to use authentication microservice and Java 17 ([e665306](https://github.com/safe-slope/authentication-service/commit/e665306040a345dc4c6fc58873465e5fd58abc7c))

## 1.0.0 (2026-01-15)


### Features

* Add JUnit dependency for testing in authentication microservice ([e8a9205](https://github.com/safe-slope/authentication-service/commit/e8a9205e643d09ba03f72d1a15c30000bf1b1a18))
* Add release-please configuration files ([9526a45](https://github.com/safe-slope/authentication-service/commit/9526a459a54cbd78cb948c129430586605288354))
* Updated release-please action ([069aea9](https://github.com/safe-slope/authentication-service/commit/069aea9a43026f912f7ff66a6fcb9a4fbbf9dea3))


### Bug Fixes

* Updated permissions and token handling in CI workflow ([5876be8](https://github.com/safe-slope/authentication-service/commit/5876be890b225cd5cf136ae7fd46e2fa704c4951))
* Updated release-please action ([9193a4a](https://github.com/safe-slope/authentication-service/commit/9193a4a822bbcc718016e992006003ef7d864d09))
* Updated release-please action to use custom config and manifest files ([7c11eed](https://github.com/safe-slope/authentication-service/commit/7c11eede48d5c1b4a958090ecb5816d2960dea61))
* Updated release-please action to use custom config and manifest files ([f4fc279](https://github.com/safe-slope/authentication-service/commit/f4fc27972665b34470681b83618e01d5568ff85d))
* Use RELEASE_PLEASE_TOKEN in release-please action ([4d44ae4](https://github.com/safe-slope/authentication-service/commit/4d44ae4ed2252cc54b9c6787b26eb737fa3e7386))
