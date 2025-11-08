<#import "./navbar.ftlh" as navbar>
<!DOCTYPE html>
<html>
<head>
    <@navbar.headContents/>
</head>
<body>

<@navbar.navbar/>
<h1>${llama.name}</h1>
<h3>${llama.age}, ${llama.color}</h3>
<img style="height: 400px; margin: 0 auto;display:block;" src="/
profile_pictures/${llama.llamaId}">

<p style="width: 500px;margin: 0 auto;font-size: 20px;">
    ${llama.description}
</p><br>
<a style="margin: 0 auto; display: block; width: max-content; font-size: 110%;" href="/update-animal/${llama.llamaId}">Update ${llama.name}</a>
<br>
</body>
</html>
