package data.entities.github.webhook

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json.Json

case class GitHubUser(
  id: Int,
  login: String,
  avatarUrl: String,
  url: String,
  siteAdmin: Boolean
)

object GitHubUser {
  implicit val gitHubUserParser = JsonNaming.snakecase(Json.format[GitHubUser])
}