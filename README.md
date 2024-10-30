# ParkingPlace
## 프로젝트 안내

<table style="width:100%; border:1px solid gray;">
  <tr>
    <td align="left" style="width:85%; padding:10px;">
      <strong>프로젝트 이름</strong>: 미리보는 주차장 파킹플레이스<br>
      <strong>교육 기관</strong>: 한국소프트웨어산업협회 최종 프로젝트<br>
      <strong>팀명</strong>: 곰곰히 (팀원 이니셜: G, O, M)
    </td>
    <td align="right" style="width:15%; padding:10px;">
      <img width="100" alt="팀 로고" src="https://github.com/user-attachments/assets/52fdbfdc-b7a8-4122-84c3-2b7337c0354f">
    </td>
  </tr>
</table>


## 목차
1. [ParkingPlace  안내](#parkingplace)
2. [배포 사이트](#배포-사이트)
3. [개발 인원](#개발-인원--총-3명fullstack)
4. [개발 일정](#개발-일정--2024년-08월-26일---2024년-10월-07일)
5. [프로젝트 개요](#프로젝트-개요)
6. [시스템 아키텍처](#시스템-아키텍처)
7. [기술 스택](#기술-스택)
8. [데이터베이스 구조 (ERD)](#데이터베이스-구조-erd)
9. [Project환경](#project-환경)
10. [프로젝트 각종 자료](#프로젝트-각종-자료)

---

<a href="https://www.parkingplace.store">
    <img src="https://github.com/user-attachments/assets/51528759-ad11-4c4d-a592-02ade9057956" alt="ParkingPlace - FrontEnd 안내" />
</a>

## 배포 사이트 : https://www.parkingplace.store

---

### 개발 인원 : 총 3명(FullStack)
| **팀원**      | **역할**                                     |
|---------------|----------------------------------------------|
| **⭐️양건모** <br> [![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/rjsah09)   | 팀장, 주차창 마커 생성 및 위치 표시, 주차장 추천 및 알림, 즐겨찾기, 배포 |
| **🙋‍♂️김경민** <br> [![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/gaengmin)   | 주차장 예약, 결제시스템 예약 및 취소, 시스템 관리자 페이지, 주차장 관리자 예약 관리|
| **🙋‍♀️오지수** <br> [![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/Wildturkeyy)   | Spring Security, JWT, 회원 관리 시스템, 주차장 상세 페이지, 리뷰 및 문의 |

---

### 개발 일정 : 2024년 08월 26일 - 2024년 10월 07일

| **단계**                | **내용**                                      | **기간**                |
|-------------------------|-----------------------------------------------|-------------------------|
| **1. 주제 선정**        | 주제 아이디어 구상 및 확정                    | 24.08.26 - 24.08.27     |
| **2. 웹개발 명세서 작성** | 웹 개발을 위한 상세 명세서 작성                 | 24.08.27 - 24.08.30     |
|                         | - ERD 설계                                    |                         |
|                         | - 피그마 설계                                  |                         |
|                         | - API 명세서 설계                              |                         |
| **3. 개발 환경 설정**    | 프로젝트 개발 환경 구축                       | 24.08.30                |
| **4. 프로젝트 개발**     | 기능 개발 및 구현                             | 24.09.02 - 24.09.24     |
| **5. 버그 수정**         | 발견된 버그 수정 및 안정화 작업               | 24.09.24 - 24.10.04     |
| **6. 발표자료 작성 및 제출** | 발표 자료 준비 및 제출                        | 24.09.24 - 24.09.27     |
| **7. AWS 서버 및 GITHUB CI/CD 설정** | 서버 배포 및 지속적 통합/배포 환경 구축 | 24.09.26 - 24.10.01     |
| **8. 프로젝트 정리 및 발표 준비** | 프로젝트 점검 및 발표 준비                  | 24.10.01 - 24.10.04     |
|                         | - 데이터 체크                                 |                         |
|                         | - 프로젝트 테스트                              |                         |
|                         | - 발표 리허설                                  |                         |
| **9. 최종 발표**         | 최종 프로젝트 발표                           | 24.10.07                |

---


## 프로젝트 개요
**ParkingPlace**는 사용자가 다양한 주차 공간을 비교하고 빈 주차장을 쉽게 검색하여 예약할 수 있는 주차 예약 플랫폼입니다.

## 개발 배경
- 도심 지역과 관광 지역에서 **주차 공간 부족**으로 인해 주차 공간을 찾는 데 많은 시간과 에너지가 소요되는 문제가 빈번하게 발생합니다.

## 주요 기능
- **위치 기반 주차장 검색**: 현재 위치 또는 목적지를 기반으로 주차장 정보를 검색하고 확인할 수 있는 기능
- **가격 비교 및 추천 시스템**: 주차장 요금과 거리 정보를 비교하여 사용자의 조건에 맞는 주차장을 추천하는 기능
- **예약 및 결제 서비스**: 주차 공간을 사전에 예약하고, 차량에 맞는 공간을 선택하여 결제할 수 있는 기능
- **주차장 관리 플랫폼**: 주차장 사업자가 예약 상태를 확인하고 차량을 효율적으로 관리할 수 있는 기능
  
## 기대 효과
- **사용자 편의성 증대**
  - 신속한 주차 예약을 통해 주차 경험 개선
  - 시간 절약으로 인한 비용 절감 효과
- **사업자 수입 증대**
  - 예약 시스템 도입으로 운영 효율성 증대
  - 플랫폼을 통한 주차 공간 노출 효과로 수익 증대
- **스마트 도시 인프라 기여**
  - 도시 교통 혼잡 문제 해결에 기여
  - 공공 주차 정책과 연계 가능성 제공


---

### 시스템 아키텍처
<img src="https://github.com/user-attachments/assets/a1eb4fcc-9d4c-4ed7-940a-4987b221b8e0" alt="ParkingPlace 시스템 아키텍처" width="700" />

---

### 기술 스택
<img src="https://github.com/user-attachments/assets/7d755b1d-8ce6-4043-b7c8-0d5eb01b553a" alt="ParkingPlace 기술 스택" width="800" />

---

### 데이터베이스 구조 (ERD)
<img src="https://github.com/user-attachments/assets/12adba31-7f9a-4961-981d-336cd7494b4e" alt="ParkingPlace ERD" width="700" />

---

### 컴포넌트 구조도
<img src="https://github.com/user-attachments/assets/a96967ce-8d80-4045-9434-f6d04d766f30" alt="ParkingPlace Component Tree" width="700" />

---

## 프로젝트 환경

이 프로젝트는 Java와 Spring Boot 기반으로 개발된 백엔드 시스템으로, AWS와 GitHub Actions를 활용한 배포 및 관리 환경을 구성하였습니다.

| 구성 요소                | 버전             |
|-------------------------|-------------------------|
| **Language**            | Java 21                 |
| **Framework**           | Spring Boot 3.2.9       |
| **Authentication**      | JWT                     |
| **Payment API**         | PortOne (아임포트)      |
| **ORM**                 | JPA                     |
| **Database**            | MySQL 8             |
| **CI/CD**               | GitHub Actions          |
| **Build Tool**          | Gradle 8.x             |
| **Security**            | Spring Security         |
| **Web Server**          | Nginx                   |
| **AWS EC2**             | -                       |
| **AWS RDS**             | MySQL                   |
| **AWS S3**              | -                       |

---

## 프로젝트  자료

### 1차 화면 설계도
- **링크**: [1차 화면 설계도 보기](https://docs.google.com/presentation/d/1VwnvDgRGF6RHtMWVWyzPoEOlVtjZa1gBNOjbrLNAgp0/edit?usp=sharing)
- **설명**: 프로젝트의 화면 구성을 설계한 자료입니다. 페이지 흐름과 주요 기능 배치를 시각적으로 표현하여 개발 방향을 잡는 데 참고할 수 있습니다.

### FIGMA 디자인 시안
- **링크**: [FIGMA 디자인 시안 보기](https://www.figma.com/design/AlSC3JXQhidFO55lNNvMxQ/KOSA-Final-Project)
- **설명**: 프로젝트의 초기 화면 설계 및 UI/UX 시안을 확인할 수 있는 FIGMA 링크입니다. 전체적인 페이지 디자인과 사용자 인터페이스 요소를 포함하고 있습니다.

### 프로젝트 시연 자료
- **링크**: [프로젝트 시연 자료 보기](https://parkingplace1.my.canva.site/)
- **설명**: 최종 프로젝트의 기능 시연을 위한 자료입니다. 프로젝트의 주요 기능과 사용자 시나리오를 포함하고 있어 시연 및 발표 시 참고할 수 있습니다.

### API 명세서
- **링크**: [API 명세서 보기](https://docs.google.com/spreadsheets/d/e/2PACX-1vRgCzUPZIB3-DR5TeOHZFf-WmVxsDKfr21lbfdjM4qq1GKIxUhvJbOgkjD2q0887w/pubhtml)
- **설명**: 서버와 클라이언트 간의 데이터 통신을 위한 API 명세서입니다. 각 API의 엔드포인트, 요청 및 응답 형식, 상태 코드 등이 포함되어 있어 백엔드 및 프론트엔드 개발 시 참고 자료로 활용됩니다.
