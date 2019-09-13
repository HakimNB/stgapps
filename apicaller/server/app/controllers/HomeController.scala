package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.Logger

case class Echo(
    var data: String
) {
  
}

object Echo {
  implicit val echoJsonFormat = Json.format[Echo]
}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def appenddate() = Action { implicit request: Request[AnyContent] =>
    val oReq = request.body.asJson
    val jsReq = oReq.getOrElse(JsString("null"))
    val data = (jsReq \ "data").getOrElse(JsString("no data")).as[String]
    Logger.debug("data: " + data)
    val echo = Echo(data)
    Ok(Json.toJson(echo).toString())
  }
}
