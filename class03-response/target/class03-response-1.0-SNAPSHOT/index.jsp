<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>响应不同类型的图片</title>
  <style>
    ul {
      list-style: none;
      padding: 0;
    }
    ul li {
      float: left;
      margin-top: 40px;
      padding: 20px 50px;
      height: 60px;
      background-color: aqua;
      color: black; /* 改为黑色以确保可读性 */
      box-sizing: border-box;
      margin-right: 10px;
      font-size: 16px;
      text-decoration: none; /* 修改为 none，去掉下划线 */
    }
  </style>
</head>
<body>
<h1><%= "设置 Content-Type 不同类型的资源" %></h1>
<h2><%= "根据不同参数类型返回资源"%></h2>
<br/>
<ul>
  <li><a href="/res?type=img">返回图片</a></li>
  <li><a href="/res?type=ppd">返回pdf</a></li>
  <li><a href="https://www.baidu.com/">前往百度</a></li>
  <li><a href="https://www.google.co.jp/">前往Google</a></li>
  <li><a href="download?filename=b.txt">文档下载</a></li>
  <li><a href="download?filename=image.jpg">壁纸下载</a></li>
</ul>
</body>
</html>
