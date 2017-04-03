# the Client API

this is the client API consuming the Todo Api.

using `FeignClient`

`Ribbon` is disabled

to try out, start the `Hystrix Dashboard` and the `Todo App` then the `Todo Client Api`

by invoking [GET /client/todos](http://localhost:8081/client/todos) you'll make call to [GET /api/todos](http://localhost:8080/api/todos)
