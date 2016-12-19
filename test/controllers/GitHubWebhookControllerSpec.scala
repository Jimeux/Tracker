package controllers

import constants.GitHubConstants.Webhooks
import org.scalatestplus.play._
import play.api.libs.json.JsString
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import services.github.WebhookServiceImpl

class GitHubWebhookControllerSpec extends PlaySpec with Results {

  /*"payload" should {
    val PayloadUrl = controllers.routes.GitHubWebhookController.payload().url
    val controller = new GitHubWebhookController(new WebhookServiceImpl)

    "return NOT_ACCEPTABLE without signature/event headers and a request body" in {
      val request = FakeRequest(POST, PayloadUrl)
        .withHeaders(gitHubHeaders(""): _*)

      val result = controller.payload().apply(request)
      status(result) mustBe NOT_ACCEPTABLE
    }

    "should be valid" in {
      val controller = new GitHubWebhookController(new WebhookServiceImpl)
      val request = FakeRequest(POST, PayloadUrl)
        .withHeaders(gitHubHeaders(""): _*)
        .withJsonBody(JsString("hello"))

      val result = controller.payload().apply(request)
      status(result) mustBe 200
    }

  }

  def gitHubHeaders(event: String, signature: String = "", delivery: String = "") = Seq(
    (Webhooks.Headers.Event, event),
    (Webhooks.Headers.Signature, signature),
    (Webhooks.Headers.Delivery, delivery)
  )
*/
}
