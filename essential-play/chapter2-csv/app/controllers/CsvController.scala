package controllers

import play.api._
import play.api.mvc._

object CsvController extends Controller with CsvHelpers {
  // TODO: Write a controller that:
  //
  //  - converts uploads of type `application/x-url-form-url-encoded`
  //    to CSV using the `formDataToCsv` helper;
  //
  //  - converts uploads of type `text/plain`
  //    to CSV using the `tsvToCsv` helper;
  //
  //  - converts uploads of type `text/tsv`
  //    to CSV using the `rawBufferToCsv` helper;
  //
  //  - otherwise responds with an HTTP 400 Bad Request response.
  //
  // Tips:
  //
  //  - Think about how you're going to determine the
  //    content type of each request. What will Play parse
  //    "text/tsv" as?
  //
  //  - Write separate handler functions for each content type.
  //    Get each function to return an `Option[Result]`.
  //
  //  - Chain the handlers together to create your action.
  //    Use the `map`, `flatMap`, `orElse`, and `getOrElse`
  //    methods of `Option`.
  //
  //  - Look at the helper functions in `CsvHelpers`.
  //    They will do a lot of the heavy lifting for you.
  def toCsv = Action { request =>
    formDataResult(request) orElse
    plainTextResult(request) orElse
    rawBufferResult(request) getOrElse
    failResult
  }

  def formDataResult(request: Request[AnyContent]): Option[Result] ={
    println("formDataResult " + request.body.asFormUrlEncoded)
    request.body.asFormUrlEncoded map formDataToCsv map csvResult
  }

  def plainTextResult(request: Request[AnyContent]): Option[Result] ={
    println("plainTextResult " + request.body.asText)
    request.body.asText map tsvToCsv map csvResult
  }

  def rawBufferResult(request: Request[AnyContent]): Option[Result] ={
    println("rawBufferResult " + request.body.asRaw)
    request.contentType flatMap {
      case "text/tsv" => request.body.asRaw map rawBufferToCsv map csvResult
      case _          => None
    }
  }

  def csvResult(csvData: String): Result = {
    println(csvData)
    Ok(csvData).withHeaders("Content-Type" -> "text/csv")
  }

  val failResult: Result =
    BadRequest("Expected application/x-www-form-url-encoded, " +
      "text/tsv, or text/plain")

}

trait CsvHelpers {
  def formDataToCsv(data: Map[String, Seq[String]]): String = {
    println("formDataToCsv ")
    println("data.keys " + data.keys)
    println("data.keys.toList " + data.keys.toList)
    val keys: Seq[String] = data.keys.toList.sorted
    val headLine: String = keys mkString ","
    val numValues: Int = data.map(_._2.length).max
    println("data.map(_._2.length) >> " + data.map(_._2.length))
    println("numValues " + numValues)

    val bodyLines: Seq[String] =
      (0 until numValues) map { i =>
        keys map { key =>
          val values = data.getOrElse(key, Nil)
          if(i < values.length) values(i) else ""
        } mkString ","
      }

    headLine +: bodyLines mkString "\n"
  }

  def tsvToCsv(str: String) = {
    println("tsvToCsv")
    str.replaceAll("\t", ",")
  }

  def rawBufferToCsv(buff: RawBuffer): String = {
    println("rawBufferToCsv")
    tsvToCsv(buff.asBytes() map (new String(_)) getOrElse "")
  }
}
