package controllers

import javax.inject._

import data.repositories.UserRepository
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._

@Singleton
class HomeController @Inject()(userRepo: UserRepository) extends Controller {

  def index = Action {
    userRepo.getById(4684105).map(println(_))
    Ok(views.html.index("Your new application is ready."))
  }

}
