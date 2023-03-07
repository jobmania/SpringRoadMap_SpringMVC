<%@ page import="hello.servlet.domain.memeber.MemberRepository" %>
<%@ page import="hello.servlet.domain.memeber.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    // request, response 그냥 사용 가능 = jsp는 자동적으로 servlet으로 변환되기 때문에 .!!
    MemberRepository memberRepository = MemberRepository.getInstance();
    String name = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(name, age);
    memberRepository.saved(member);

%>


<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age= <%=member.getAge()%></li>
</ul>

<a href="/index.html"> 메인으로 ~ </a>
</body>
</html>
