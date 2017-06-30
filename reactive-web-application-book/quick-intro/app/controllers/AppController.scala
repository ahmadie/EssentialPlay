package controllers

import play.api.Logger
import play.api.Play.current
import play.api.mvc._
import play.api.i18n.Lang
import models._
import javax.inject.Inject
import services.VocabularyService

class AppController @Inject() (vocabulary: VocabularyService) extends Controller {
  def index = Action { request =>
    Ok("Hello world!")
  }

  def importWord(
    sourceLanguage: Lang,
    targetLanguage: Lang,
    word: String,
    translation: String) = Action { request =>
    Ok("importWord")
  }
}
