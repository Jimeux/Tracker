package controllers

import javax.inject._

import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import services.IssueService

@Singleton
class IssueController @Inject()(issueService: IssueService) extends Controller {

  def get(id: Int) = Action.async {
    issueService.get(id) map {
      case Some(json) => Ok(json)
      case None => NotFound
    }
  }

  def getAll(page: Int, perPage: Int) = Action.async {
    issueService.get(page, perPage).map(Ok(_))
  }

}
