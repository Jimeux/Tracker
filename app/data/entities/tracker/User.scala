package data.entities.tracker

import data.entities.github.webhook.GitHubUser
import play.api.libs.json.Json

case class User(
  id: Int,
  login: String,
  avatarUrl: String,
  url: String,
  siteAdmin: Boolean
) extends BaseEntity

object User {

  implicit val userReads = Json.reads[User]
  implicit val userWrites = Json.writes[User]

  def of(u: GitHubUser): User =
    User(u.id, u.login, u.avatarUrl, u.url, u.siteAdmin)
}