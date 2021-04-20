# JAX-RS examples
=================

> ## Server side:
> Move to the `jakarta_book` folder and type:
> ```bash
> mvn clean package tomee: run
>```

> ## Client side:
> Move to the `book_client` folder and type:
> ````bash
> mvn clean package assembly:single
> 
> java -Dfile.encoding=UTF-8 -jar target/book_client-jar-with-dependencies.jar
>````
> 
><h4 align="center"> or </h4>
> 
> type in console:
> 
> ```bash
> curl -i http://localhost:8080/jakarta_book/root/books
> curl -i http://localhost:8080/jakarta_book/root/books/{id}
> curl -i http://localhost:8080/jakarta_book/root/books/stream
> curl -i http://localhost:8080/jakarta_book/root/books/async_stream
> curl -i http://localhost:8080/jakarta_book/root/books/callback
> curl -i http://localhost:8080/jakarta_book/root/books/xml
> curl -i http://localhost:8080/jakarta_book/root/tutorials{id}
> ```

> ### You can also enter this in the browser bar:
> ```curl
> http://localhost:8080/jakarta_book/root/books/stream
> ```
> #### Result:
> ```json
> [{"id":"0","title":"Book 0"},{"id":"1","title":"Book 1"},{"id":"2","title":"Book 2"},{"id":"3","title":"Book 3"},{"id":"4","title":"Book 4"},{"id":"5","title":"Book 5"},{"id":"6","title":"Book 6"},{"id":"7","title":"Book 7"},{"id":"8","title":"Book 8"},{"id":"9","title":"Book 9"},{"id":"10","title":"Book 10"},{"id":"11","title":"Book 11"},{"id":"12","title":"Book 12"},{"id":"13","title":"Book 13"},{"id":"14","title":"Book 14"},{"id":"15","title":"Book 15"},{"id":"16","title":"Book 16"},{"id":"17","title":"Book 17"},{"id":"18","title":"Book 18"},{"id":"19","title":"Book 19"},{"id":"20","title":"Book 20"},{"id":"21","title":"Book 21"},{"id":"22","title":"Book 22"},{"id":"23","title":"Book 23"},{"id":"24","title":"Book 24"},{"id":"25","title":"Book 25"},{"id":"26","title":"Book 26"},{"id":"27","title":"Book 27"},{"id":"28","title":"Book 28"},{"id":"29","title":"Book 29"},{"id":"30","title":"Book 30"},{"id":"31","title":"Book 31"},{"id":"32","title":"Book 32"},{"id":"33","title":"Book 33"},{"id":"34","title":"Book 34"},{"id":"35","title":"Book 35"},{"id":"36","title":"Book 36"},{"id":"37","title":"Book 37"},{"id":"38","title":"Book 38"},{"id":"39","title":"Book 39"},{"id":"40","title":"Book 40"},{"id":"41","title":"Book 41"},{"id":"42","title":"Book 42"},{"id":"43","title":"Book 43"},{"id":"44","title":"Book 44"},{"id":"45","title":"Book 45"},{"id":"46","title":"Book 46"},{"id":"47","title":"Book 47"},{"id":"48","title":"Book 48"},{"id":"49","title":"Book 49"}]
> ```