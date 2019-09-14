package controllers

import javax.inject._
import java.util.Date
import java.util.Calendar
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.Logger

case class LocalizationEntry (
    var key: String,
    var english: String,
    var chinese: String,
    var hindi: String
) {
  
}

object LocalizationEntry {
  implicit val locEntryJsonFormat = Json.format[LocalizationEntry]
}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class LocalizationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * This need not be API endpoint, it can be a json file hosted in CDN instead
   * I just put it here for convenience
   */
  def getdatafile() = Action { implicit request: Request[AnyContent] =>
    val result: List[LocalizationEntry] = List(
        LocalizationEntry("helloworld", "Hello World", "你好，世界", "नमस्ते दुनिया"),
        LocalizationEntry("happy", "Happy", "快乐", "खुश"),
        LocalizationEntry("sad", "Sad", "伤心", "दुखी"),
        LocalizationEntry("angry", "Angry", "愤怒", "गगुस्साा"),
        LocalizationEntry("bored", "Bored", "无聊", "ऊब"),
    )
    val resJsons: JsValue = Json.toJson(result)
    Ok(resJsons.toString)
  }
  
}
