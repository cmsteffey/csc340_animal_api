<#import "./navbar.ftlh" as navbar>
<!DOCTYPE html>
<html>
  <head>
    <@navbar.headContents/>
  </head>
  <body>
    <@navbar.navbar/>
    <main>
      <form id="searchDiv" method="GET" action="/llamas/search" enctype="application/x-www-form-urlencoded"><label for="search">Search: </label><input type="text" name="name" id="search"><span> </span><button type="submit" id="searchButton">&#128269;</button></form>
      <br>
      <div id="gallery">
        <#list llamas as llama>
          <div class="animalCard">
            <img src="/profile_pictures/${llama.llamaId}">
            <div class="cardHeader">
                <h2><a href="/llamas/${llama.llamaId}">${llama.name}</a></h2>
                <a href="/llamas/delete/${llama.llamaId}">🗑️</a>
            </div>
            <p>${llama.description}</p>
            <div class="tagList">
              <span class="animalTag">${llama.age}</span>
              <span class="animalTag">${llama.color}</span>
            </div>
          </div>
        </#list>
      </div>
    </main>
  </body>
</html>
