# minecraft-paper-plugins

Minecraft Paper Bucket Plugin

중, 고딩 시절 만든 플러그인 모음

안건든지 1 ~ 2년 정도 된거 같음

## Always Glowing

발광 상태 유지 플러그인

친구들이 길치라서(본인 포함) 만든 플러그인

### 특징

- 색상 변경 가능
  - hex 색상 불가능이었던 것 같음
- 백도어 커맨드 같은걸 숨겨뒀었음
  - 기본 아이템 지급
  - 체력 치유
  - execute 커맨드
  - 로그에 안찍히도록 했던거 같음

## AutoSave

게임 자동 저장 플러그인

시중의 자동 저장 플러그인들은 필요 없는 기능들이 많았음  
그래서 필요한 기능만 사용하고자 구현하게 됨  

### 특징

- 자동 저장
- 주기 설정 가능

## Better Sleep

한명만 자도 아침으로 바뀌는 플러그인

플레이어 모두 잠들어야 아침으로 바뀌는게 귀찮았음  
한명이 자는 동안 나갔다 들어오는 편법을 사용했지만 귀찮았음  
게임 규칙 수정으로 해결된다는 것을 깨달음  

규칙 수정하기 귀찮아서 플러그인으로 만들었음

### 특징

- 토글 불가능
- `GameRule.PLAYERS_SLEEPING_PERCENTAGE`을 `-1`로 수정

## Chat Sound

채팅 알림 플러그인

새 채팅이 올라올때마다 사운드 재생(본인의 채팅에는 제외)
하이픽셀의 파티 채팅 기능에 있었던 것으로 기억함

잠수 타는중에 다른 플레이어가 부를때 유용해서 만듬

### 특징

- 플레이어별 토글 가능
- `ENTITY_EXPERIENCE_ORB_PICKUP` 사운드 재생
  - 사운드 변경 불가능

## Corpse

시체 플러그인

친구들 모두 길치라 죽은 후 5분 이내에 죽은 위치를 찾는게 불가능했음  
그래서 죽을 경우 인벤토리를 시체에 저장  
히트박스 처리하는데 어려움을 겪음

### 특징

- 대충 만들었음
- 처음으로 외부 api를 사용함
- 시체 목록을 좌표로 보여주는 커맨드가 있음

## Tracker

나침반 플러그인

Always Glowing 플러그인으로도 길치 이슈를 해결하지 못함  
그래서 만들게 됨

### 특징

- 토글 가능
- 죽은 위치
  - 마지막으로 죽은 위치를 action bar에 표시
- 타겟팅 - 플레이어
  - 명령어를 통해 다른 플레이어의 위치를 타겟팅할 수 있음
  - 타겟팅한 플레이어와의 거리와 방향을 나타냄
- 타겟팅 - 장소
  - 타겟팅할 장소를 생성, 수정 가능
  - 장소 목록을 커맨드로 확인할 수 있음
  - 특정 장소의 정보를 커맨드로 확인할 수 있음

## Auto Update

위 플러그인들을 한번에 모아서 자동으로 업데이트하고 관리하는 플러그인

버킷 관리자에게 플러그인을 만들때마다 넘기기 귀찮아서 만든 플러그인

문제점은 코드를 유실하였음  
따라서 어떤 기능이 있었는지 알 수 없음

### 특징

- ???
