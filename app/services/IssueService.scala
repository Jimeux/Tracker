package services

import com.google.inject.{ImplementedBy, Inject, Singleton}
import data.repositories.IssueRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

@ImplementedBy(classOf[IssueServiceImpl])
trait IssueService {
  def get(id: Int): Future[Option[JsValue]]
  def get(page: Int, perPage: Int): Future[JsValue]
}

@Singleton
class IssueServiceImpl @Inject()(issueRepo: IssueRepository) extends IssueService {

  def get(page: Int, perPage: Int) =
    issueRepo.getAllWithUser(offset(page, perPage), perPage)
      .map(Json.toJson(_))

  def get(id: Int) =
    issueRepo.getWithUser(id).map(_.map(Json.toJson(_)))

  def offset(page: Int, perPage: Int) = page * perPage - perPage

}
