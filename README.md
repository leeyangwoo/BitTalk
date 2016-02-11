# BitTalk

<h5>Chat with BitTalk</h5>

<br><br>
<h3>GITHUB 규칙</h3>

-- Remote(Origin)에서 최신 코드 받아오기
<br>git pull origin master<br>
소스트리에서 pull하고 master 선택<br><br>


-- 로컬에서 작업시 브랜치 따기<br>
git branch (브랜치이름)<br>
git checkout (브랜치이름) -->브랜치이동/ git shell에서 브랜치 이름 바뀌었는지 확인<br><br><br>


-- 로컬에서 작업하면서 자기 브랜치에서 commit하기<br>
1.git add . / 소스트리에서 스테이지에 올리기<br>
2.git commit -m "작업 내용" / 소스트리에서 커밋버튼 누르고 작업메세지 작성<br>
3.git push origin (브랜치이름) / 소스트리에서 푸시버튼 누르고 브랜치 선택<br><br><br>


-- 로컬에서 작업 후 merge하기 전에 팀원들에게 알리고 검토받기<br>

<hr>

<h3>DB정보</h3>
IP: 192.168.1.35:3306<br>
DB명: bittalk<br>
username: bit<br>
password: qlxm<br>
<br>

<h4>테이블 구조</h4>
<br>
<h5>member(회원테이블)</h5><br>

mno int(11) Auto increment PK(시퀀스값 고유번호)<br>
mid varchar(15)(로그인할 때 필요한 id)<br>
mpasswd varchar(20)(로그인할 때 필요한 비밀번호)<br>
mname varchar(20)(닉네임, 한글이름)<br>
<br>
<h5>chatroom(채팅방테이블)</h5>
crno int(11) Auto increment PK(고유 채팅방번호)<br>
numparticipant int(11) 채팅방 참가자 수<br>
<br>
<h5>participate(대화방 참가 테이블)</h5>
mno int(11) 회원을 가리키는 외래키  //  <i>mno와 crno가 함께 primary key</i><br>
crno int(11) 채팅방을 가리키는 외래키<br>
<br>
<h5>chatmsg(채팅내용테이블)</h5>
cmno int(11) Auto increment PK(고유 채팅내용번호)<br>
crno int(11) 채팅방번호를 가리키는 외래키<br>
senderno int(11) 회원을 가리키는 외래키(메세지 보낸사람)<br>
msg varchar2(200) 메세지 내용<br>
sendtime datetime 메세지 전송시간<br>
<br>

<h3>BitTalk-Server</h3>
<br>
<h4>URL</h4>
회원 예제 보기 URL: <b>192.168.1.35/BitTalkServer/index.jsp</b><br> 
로그인할 URL: <b>192.168.1.35/BitTalkServer/login.jsp</b><br>
Parameter : <b>mid / mpasswd</b><br>
회원가입 URL: <b>192.168.1.35/BitTalkServer/join.jsp</b><br>
Parameter : <b>mid / mpasswd / mname</b>
<hr>
<h5>회원 예제 데이터</h5>
<ol>
  <ul>
    <li>mno: 1</li>
    <li>mid: leeyang</li>
    <li>mpasswd: 1111</li>
    <li>mname: 이양우</li>
  </ul>
  <br>
  <ul>
    <li>mno: 2</li>
    <li>mid: yangwoo</li>
    <li>mpasswd: 1112</li>
    <li>mname: 양우리</li>
  </ul>
</ol>
