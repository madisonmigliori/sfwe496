package com.sfwe496.user

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UserControllerSimulation extends Simulation {

  val baseUrl = "http://localhost:8081"

  val jwtToken = System.getenv("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuWmZjOFlDTWtMbnFWWkpISURmQjBaVjFNUldScTVnVXEyeFVLRVhKYy04In0.eyJleHAiOjE3NDUzMjYwMTYsImlhdCI6MTc0NTMyNTcxNiwianRpIjoiZDc3MjBjNzgtY2VkNy00NjkxLThlNjgtMGUxYjBmODVmMDIxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9zZndlNDk2IiwiYXVkIjpbInRyaXAtc2VydmljZSIsInNmd2U0OTYiXSwic3ViIjoiODJlYjk4ZDQtYjk1YS00ZWFiLWI3YzAtYzRjZDQwZTEyNTEzIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidXNlci1zZXJ2aWNlIiwic2lkIjoiYWNkYTc3OWItZGFiOS00M2RjLTliNjMtOWVhODMwYWZjYmVlIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0cmlwLXNlcnZpY2UiOnsicm9sZXMiOlsiVVNFUiJdfSwic2Z3ZTQ5NiI6eyJyb2xlcyI6WyJVU0VSIl19LCJ1c2VyLXNlcnZpY2UiOnsicm9sZXMiOlsiQURNSU4iLCJVU0VSIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJXaWxtYSBXaWxkY2F0IiwicHJlZmVycmVkX3VzZXJuYW1lIjoid2lsbWEud2lsZGNhdCIsImdpdmVuX25hbWUiOiJXaWxtYSIsImZhbWlseV9uYW1lIjoiV2lsZGNhdCIsImVtYWlsIjoid2lsbWEud2lsZGNhdEBleGFtcGxlLmNvbSJ9.MJgCN1ZAZJVGWciNaIiRKVGNni0g-xiOCS2Z4PgSKDRUcC9n87Z1Y9BdsJD1lZlQQAom-jdCrr3FufnhHJ6HohG8tvTE4s_TVasOtlHtCM_8iF26f89T7pm-mpqA2CwBaOrM_76ec46grjyU2uLxQsTYXSw2uLmLde1SeZI4PuJdTpLZnVD7-N7aDIZAjdYgjf6Tc1op_eIWkUQDgR2Wbt_WWEagNm_9v9zuNbtcC1LQNM2_SCjil1EJjN8woztJ800Pa9mjtTFmaYnwgi8yhYJSp3FPeFHvXy_QT8QoOPF7q2aQuZSxdLIPLdRAgYbUkoDk6uAGhxeVYJJyFSVXWQ") // set this before running
  val httpProtocol = http
    .baseUrl(baseUrl)
    .acceptHeader("application/json")
    .header("Authorization", s"Bearer $jwtToken")

  val getUser = scenario("Get Specific User with Resilience4j")
    .exec(
      http("GET /users/1")
        .get("/users/1")
        .check(status.in(200, 503)) 
    )
    .pause(1)

  val getAllUsers = scenario("Get All Users")
    .exec(
      http("GET /users")
        .get("/users")
        .check(status.is(200))
    )
    .pause(1)

  val registerUser = scenario("Register New User")
    .exec(
      http("POST /register")
        .post("/register")
        .body(StringBody("""{ "name": "Test User", "email": "test@example.com", "password": "123456" }""")).asJson
        .check(status.is(201))
    )
    .pause(1)

  val resetPassword = scenario("Reset Password")
    .exec(
      http("POST /reset-password")
        .post("/reset-password?email=test@example.com")
        .check(status.in(200, 404))
    )
    .pause(1)

  val updateUser = scenario("Update User")
    .exec(
      http("PUT /users/1")
        .put("/users/1")
        .body(StringBody("""{ "name": "Updated Name", "email": "updated@example.com" }""")).asJson
        .check(status.is(200))
    )
    .pause(1)

  val deleteUser = scenario("Delete User")
    .exec(
      http("DELETE /users/1")
        .delete("/users/1")
        .check(status.in(204, 403, 404)) // 403 if no ADMIN role
    )
    .pause(1)

  setUp(
    getUser.inject(constantUsersPerSec(10).during(30.seconds)),
    getAllUsers.inject(rampUsersPerSec(1).to(20).during(20.seconds)),
    registerUser.inject(atOnceUsers(5)),
    resetPassword.inject(rampUsers(5).during(10.seconds)),
    updateUser.inject(atOnceUsers(3)),
    deleteUser.inject(atOnceUsers(2))
  ).protocols(httpProtocol)
}
