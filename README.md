# Community Service - 사용자들이 다양한 주제에 대해 소통할 수 있는 커뮤니티 서비스

1. [Tech Stack](#tech-stack)
2. [ERD](#erd)
3. [프로그램 기능 및 설계](#프로그램-기능-및-설계)
   - [회원관리](#회원관리)
   - [커뮤니티](#커뮤니티)

## 🛠️ Tech Stack

![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![spring-boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![mariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

![git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)
![amazons3](https://img.shields.io/badge/amazons3-569A31?&style=for-the-badge&logo=amazons3&logoColor=white)

## ERD

<img src="https://github.com/Namgyu11/ktb_cj_community_spring_BE/assets/103015031/e0b82b44-f284-4307-affc-dbbafb26848f" width="600" height="300"/>

## 프로그램 기능 및 설계

### 회원관리

- [ ] **회원가입 기능**
  - 사용자는 회원가입할 수 있습니다.
  - 이메일, 닉네임, 비밀번호를 입력 받으며, 이메일과 닉네임은 중복이 불가합니다.
  - 회원가입한 모든 사용자는 user 권한을 갖습니다.
  - 관리자 권한은 별도의 회원가입 절차를 거치지 않습니다.
  - 회원가입 시 비밀번호는 BCryptPasswordEncoder를 이용하여 암호화하여 저장합니다.
  - 회원가입시 이메일 인증을 진행합니다. 인증이 완료되면 'email_auth' 필드가 업데이트 됩니다.

- [ ] **로그인 기능**
  - 사용자는 로그인할 수 있습니다.
  - 로그인 시 회원가입 시 저장한 이메일과 비밀번호가 일치해야 합니다.
  - 로그인 성공 시 JWT 토큰을 발급합니다.
  - Redis에 토큰을 저장합니다.

- [ ] **로그아웃 기능**
  - Redis에서 토큰을 조회한 후 토큰이 존재하면 삭제합니다.

- [ ] **회원정보 조회**
  - 사용자는 자신의 회원 정보를 조회할 수 있습니다.
  - 이메일, 닉네임을 조회할 수 있습니다.
  - 회원 정보 조회 시 비밀번호는 조회되지 않습니다.

- [ ] **회원정보 수정**
  - 사용자는 자신의 회원 정보를 수정할 수 있습니다.
  - 비밀번호, 닉네임을 수정할 수 있습니다.

### 커뮤니티

- [ ] **게시물 작성 기능**
  - 로그인한 사용자는 게시물을 작성할 수 있습니다.
  - 제목, 내용(텍스트), 사진을 작성할 수 있습니다.
  - 사진 파일은 Amazon S3에 저장 및 관리합니다.

- [ ] **게시물 목록 조회 기능**
  - 로그인 여부와 상관없이 게시물 목록을 조회할 수 있습니다.
  - 게시물은 페이징 처리되어 표시됩니다.

- [ ] **특정 게시물 조회 기능(검색)**
  - 로그인 여부와 상관없이 게시물을 조회할 수 있습니다.
  - 제목 또는 내용을 검색하여 특정 게시물을 조회할 수 있습니다.
  - 제목, 내용, 작성자, 작성일, 좋아요 갯수가 조회됩니다.
  - 조회수는 Redis와 스케줄링을 활용하여 같은 게시물에 대한 중복 조회는 카운팅되지 않습니다.

- [ ] **게시물 좋아요 기능**
  - 로그인한 사용자는 게시물에 좋아요를 누를 수 있습니다.
  - 좋아요는 중복으로 누를 수 없습니다.

- [ ] **댓글 작성 기능**
  - 로그인한 사용자는 특정 게시물에 댓글을 작성할 수 있습니다.
  - 작성한 댓글은 다른 사용자들이 볼 수 있습니다.

- [ ] **댓글 수정 기능**
  - 로그인한 사용자는 자신이 작성한 댓글을 수정할 수 있습니다.
  - 수정된 댓글은 다른 사용자들이 볼 수 있습니다.

- [ ] **댓글 삭제 기능**
  - 로그인한 사용자는 자신이 작성한 댓글을 삭제할 수 있습니다.
  - 삭제된 댓글은 다른 사용자들이 볼 수 없습니다.

- [ ] **댓글 목록 조회 기능**
  - 로그인 여부와 상관없이 특정 게시물에 남겨진 모든 댓글을 조회할 수 있습니다.
  - 최신 댓글이 상단에 위치하도록 정렬됩니다.

- [ ] **댓글 좋아요 기능**
  - 로그인한 사용자는 특정 댓글에 좋아요를 남길 수 있습니다.
  - 좋아요는 중복으로 누를 수 없습니다.
