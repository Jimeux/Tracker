package data.repositories

import data.entities.tracker.BaseEntity
import data.tables.BaseTable
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits._
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import slick.lifted.{CanBeQueryCondition, Rep, TableQuery}

import scala.concurrent.Future
import scala.language.higherKinds

trait BaseRepository[T <: BaseTable[E], E <: BaseEntity] {
  val table: TableQuery[T]
  def getById(id: Int): Future[Option[E]]
  def createIfNotExists(e: E): Future[E]
}

abstract class BaseRepositoryImpl[T <: BaseTable[E], E <: BaseEntity](t: TableQuery[T])
  extends HasDatabaseConfigProvider[JdbcProfile]
    with BaseRepository[T, E] {

  final val table: TableQuery[T] = t

  def getById(id: Int): Future[Option[E]] =
    db.run(table.byId(id).first)

  def createIfNotExists(e: E): Future[E] =
    db.run(table.getFirst(e.id)).flatMap {
      case Some(found) => Future.successful(found)
      case _ => db.run(table.insert(e))
    }

  implicit class QueryExtensions[C[_]](val q: Query[T, E, C]) {

    def first = q.result.headOption

    def byId(id: Int) = q.filter(_.id === id)

    def getFirst(id: Int) = q.byId(id).first

    def insert(row: E) = (q returning q) += row

    def update(row: E): Query[T, E, C] = q.byId(row.id).update(row)

    def where[K <: Rep[_]](expr: T => K)(implicit wt: CanBeQueryCondition[K]) =
      q.filter(expr)

  }

}
