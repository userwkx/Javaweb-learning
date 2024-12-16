<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .register-container {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .register-container h2 {
            text-align: center;
        }
        .register-container label {
            display: block;
            margin-top: 10px;
        }
        .register-container input[type="text"],
        .register-container input[type="password"],
        .register-container input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .register-container button {
            width: 100%;
            padding: 10px;
            margin-top: 20px;
            background-color: #007BFF;
            border: none;
            color: #fff;
            border-radius: 4px;
            cursor: pointer;
        }
        .error-message {
            color: red;
            margin-top: 10px;
            text-align: center;
        }
        .captcha {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>用户注册</h2>
    <form action="/register" method="post">
        <label for="account">账号:</label>
        <input type="text" id="account" name="account" required>

        <label for="password">密码:</label>
        <input type="password" id="password" name="password" required>

        <label for="captcha">验证码:</label>
        <div class="captcha">
            <input type="text" id="captcha" name="captcha" required>
            <img src="register?action=captcha" alt="验证码" onclick="this.src='register?action=captcha&'+new Date().getTime();"title="点击刷新验证码">
        </div>

        <button type="submit">立即注册</button>

        <!-- 显示错误信息 -->
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error-message">
            <%= request.getAttribute("errorMessage") %>
        </div>
        <% } %>
    </form>
</div>
</body>
</html>