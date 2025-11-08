<#import "./navbar.ftlh" as navbar>
<!DOCTYPE html>
<html>
<head>
    <@navbar.headContents/>
</head>
<body>

<@navbar.navbar/>
<h1>Update ${llama.name}</h1>
<form method="POST" action="/llamas/update/${llama.llamaId}" enctype="multipart/form-data">
    <label for="name">Name:</label><br><input name="name" id="name" type="text" value="${llama.name}"><br>
    <label for="color">Color:</label><br><input type="text" id="color" name="color" value="${llama.color}"><br>
    <label for="age">Age:</label><br><input id="age" name="age" type="number" value="${llama.age}"><br>
    <label for="desc">Description:</label><br><textarea id="desc" name="description">${llama.description}</textarea><br><br>
    <label for="pic">Picture:</label><br><input type="file" id="pic" name="pic"><br><br>
    <span>^ Leave unselected to keep old picture</span><br>
    <button type="submit">Update!</button>
</form>
</body>
</html>
