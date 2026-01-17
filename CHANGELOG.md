# Changelog

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
