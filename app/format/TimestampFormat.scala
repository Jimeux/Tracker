package format

import java.sql.Timestamp

import org.joda.time.DateTime

object TimestampFormat {
  import play.api.libs.json.Json._
  import play.api.libs.json._

  def timestampToDateTime(t: Timestamp): DateTime = new DateTime(t.getTime)

  def dateTimeToTimestamp(dt: DateTime): Timestamp = new Timestamp(dt.getMillis)

  implicit val implicitTimestampFormat = new Format[Timestamp] {
    def writes(t: Timestamp): JsValue = toJson(timestampToDateTime(t))

    def reads(json: JsValue): JsResult[Timestamp] = fromJson[DateTime](json).map(dateTimeToTimestamp)
  }
}