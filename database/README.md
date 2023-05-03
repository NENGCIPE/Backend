# 냉시피 Back-End Database

### [data table schema 작성] 23년 4월 15일(작성자: 추세빈)
<img width="500" alt="image" src="https://user-images.githubusercontent.com/102461290/233582765-b1c24259-94f4-4794-ad57-1a376ff054d4.png">


### [data conceptual modeling] 23년 4월 16일 (작성자: 추세빈)
<img width="500" alt="image" src="https://user-images.githubusercontent.com/102461290/233585521-6a66977f-e495-4c68-8fae-86661f922042.png">

### [data logical modeling] 23년 4월 21일 (작성자: 추세빈)
<img width="500" alt="냉_논리적 모델링" src="https://user-images.githubusercontent.com/102461290/233584597-46bdf057-c8b2-49f7-815b-4a6dae50d3af.png">

#### 팀원들과 논의하여 결정할 것.
1. FK가 너무 많아서 융통성있게 줄여야 할 듯하다.
2. recipies_name / ingredient_name 넣?말? id만으로 구분하면 안넣어도 된다다.


### [data table schema 수정] 23년 5월 3일
   name 추가   
   user->member 변경   
   API적인 고민 & 레시피, 유저 레시피, 재료 간의 관계에 대한 고민을 하다 보니까 추가된 점들이 있습니다.
   <img width="500" alt="냉db 스키마" src="https://user-images.githubusercontent.com/102461290/235827140-c6b269cb-163b-436f-91fc-0885f2e7f80f.png">
