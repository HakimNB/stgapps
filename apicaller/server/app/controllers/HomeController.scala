package controllers

import javax.inject._
import java.util.Date
import java.util.Calendar
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.Logger

case class Echo(
    var data: String,
    var callcount: Int
) {
  
}

object Echo {
  implicit val echoJsonFormat = Json.format[Echo]
  
  // these should be in GlobalSettings, or memcache
  private var lc: java.util.Date = new Date()
  private var cc: Int = 0
  
  def called() = {
    if ( !issameday(lc) ) {
      cc = 0
    }
    cc += 1
  }
  
  def getlastcalled() = {
    lc
  }
  
  def getcallcount() = {
    cc
  }
  
  def issameday(date: Date) = {
    val today: Calendar = Calendar.getInstance()
    val thedate: Calendar = Calendar.getInstance()
    thedate.setTime(date)
    val sameday = today.get(Calendar.DAY_OF_MONTH) == thedate.get(Calendar.DAY_OF_MONTH)
    val samemonth = today.get(Calendar.MONTH) == thedate.get(Calendar.MONTH)
    val sameyear = today.get(Calendar.YEAR) == thedate.get(Calendar.YEAR)
    val res = sameday && samemonth && sameyear
    res
  }
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
  
  def appendcount() = Action { implicit request: Request[AnyContent] =>
    val oReq = request.body.asJson
    val jsReq = oReq.getOrElse(JsString("null"))
    val data = (jsReq \ "data").getOrElse(JsString("no data")).as[String]
    Logger.debug("data: " + data)
    Echo.called()
    val lastcalled = Echo.getlastcalled()
    val callcount = Echo.getcallcount()
    val echo = Echo(data + " " + callcount, callcount)
    Ok(Json.toJson(echo).toString())
  }
}
