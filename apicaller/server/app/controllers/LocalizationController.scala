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
        LocalizationEntry("helloworld", "Hello World - R", "你好，世界 - R", "नमस्ते दुनिया - R"),
        LocalizationEntry("happy", "Happy - R", "快乐 - R", "खुश - R"),
        LocalizationEntry("sad", "Sad - R", "伤心 - R", "दुखी - R"),
        LocalizationEntry("angry", "Angry - R", "愤怒 - R", "गगुस्साा - R"),
        LocalizationEntry("bored", "Bored - R", "无聊 - R", "ऊब - R"),
    )
    Logger.debug("LocalizationController.getdatafile")
    val resJsons: JsValue = Json.toJson(result)
    Ok(resJsons.toString)
  }
  
}
