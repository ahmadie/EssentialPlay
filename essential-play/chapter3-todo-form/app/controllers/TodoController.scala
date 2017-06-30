package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.twirl.api.Html
import models._

object TodoController extends Controller with TodoDataHelpers {
  // TODO: Create a Form[Todo]:
  //  - build the basic form mapping;
  //  - create constraint to ensure the label is non-empty.
  val todoForm: Form[Todo] = Form(mapping(
    "id"        -> optional(text),
    "label"     -> nonEmptyText,
    "complete"  -> boolean
  )(Todo.apply)(Todo.unapply))

  def index = Action { request =>
    Ok(views.html.todoList(todoList, todoForm))
  }
  var todoList = TodoList(Seq(
    Todo("Dishes", true),
    Todo("Laundry"),
    Todo("World Domination")
  ))

  def submitTodoForm = Action { implicit request =>
    // TODO: Write code to handle the form submission:
    //  - validate the form;
    //  - if form is valid:
    //     - add todo to todoList;
    //     - redirect to index;
    //  - else:
    //     - display errors.
    todoForm.bindFromRequest().fold(
       { errorForm =>
        BadRequest(views.html.todoList(todoList, errorForm))
      },
       { todo =>
        todoList = todoList.addOrUpdate(todo)
        println(todoList)
        Redirect(routes.TodoController.index)
      }
    )
  }
}

trait TodoDataHelpers {

}
