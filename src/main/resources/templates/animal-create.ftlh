<#import "./navbar.ftlh" as navbar>
<!DOCTYPE html>
<html>
<head>
    <@navbar.headContents/>
</head>
<body>

<@navbar.navbar/>
<h1>Add A New Llama</h1>
<form method="POST" action="/llamas" enctype="multipart/form-data">
    <label for="name">Name:</label><br><input name="name" id="name" type="text"><br>
    <label for="color">Color:</label><br><input type="text" id="color" name="color"><br>
    <label for="age">Age:</label><br><input id="age" name="age" type="number"><br>
    <label for="desc">Description:</label><br><textarea id="desc" name="description"></textarea><br><br>
    <label for="pic">Picture:</label><br><input type="file" id="pic" name="pic"><br><br>
    <button type="submit">Post!</button>
</form>
</body>
</html>
