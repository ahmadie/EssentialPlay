package controllers

import play.api._
import play.api.data._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

object AuthController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  // TODO: Complete:
  //  - Create a form for a LoginRequest
  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginRequest.apply)(LoginRequest.unapply))

  // TODO: Complete:
  //  - Create a login page template:
  //     - Accepts a login form as a parameter
  //     - Displays the form and a submit button
  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  // TODO: Complete:
  //  - Process a submitted login form:
  //     - If it is valid, call AuthService.login:
  //        - If the login was successful, set a cookie and redirect to the chat page
  //        - If the user was not found, return a login form with an appropriate error message     [*]
  //        - If the password was incorrect, return a login form with an appropriate error message [*]
  //     - If it is invalid, a login form with an appropriate error message
  //
  // NOTE: You will have to specify the error messages marked with a [*] manually.
  // You can do this with the following code:
  //
  //     loginForm.withError("username", "User not found") // returns a new login form
  def submitLogin = Action { implicit request =>

    val form = loginForm.bindFromRequest()

    form.fold(
      hasErrors = { form: Form[LoginRequest] =>
        BadRequest(views.html.login(form))
      },
      success = { loginReq: LoginRequest =>
        AuthService.login(loginReq) match {
          case res: LoginSuccess =>
            Redirect(routes.ChatController.index)
              .withSessionCookie(res.sessionId)

          case res: UserNotFound =>
            BadRequest(views.html.login(addLoginError(form)))

          case res: PasswordIncorrect =>
            BadRequest(views.html.login(addLoginError(form)))
        }
      }
    )
  }

  def addLoginError(form: Form[LoginRequest]) =
    form.withError("username", "User not found or password incorrect")


  def loginRedirect(res: LoginSuccess): Result =
    Redirect(routes.ChatController.index).withSessionCookie(res.sessionId)
}
