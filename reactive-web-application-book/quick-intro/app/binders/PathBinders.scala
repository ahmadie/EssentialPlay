package binders
import play.api.i18n.Lang
import play.api.mvc.PathBindable
/**
 * Created by a.bukhari on 2017/06/30.
 */
object PathBinders {
  implicit object LangPathBindable extends PathBindable[Lang] {
    override def bind(key: String, value: String): Either[String, Lang] = {
      println(Lang.get(value))
      println(Lang.get(value).toRight(s"Language $value is not recognized"))
      // Lang.get(value).toRight(s"Language $value is not recognized")

      Lang.get(value) match {
        case Some(l) => Right(l)
        case None => Left(s"Language $value is not recognized")
      }
    }
    override def unbind(key: String, value: Lang): String = value.code
  }
}

