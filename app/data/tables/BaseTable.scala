package data.tables

import data.entities.tracker.BaseEntity
import slick.driver.PostgresDriver.api._
import slick.lifted.CanBeQueryCondition

import scala.language.higherKinds
import scala.reflect._

trait	HasId {
  _:Table[_] => def	id: Rep[Int]
}

abstract class BaseTable[E: ClassTag](tag: Tag, tableName: String)
  extends Table[E](tag, tableName) with HasId {

  def id = column[Int]("id", O.PrimaryKey)

}

object BaseTable {

  implicit class QueryExtensions[E <: HasId, U <: BaseEntity, C[_]](val q: Query[E, U, C]) {

    def first = q.result.headOption

    def byId(id: Int) = q.filter(_.id === id)

    def getFirst(id: Int) = q.byId(id).first

    def insert(row: U) = (q returning q) += row

    def update(row: U): Query[E, U, C] = q.byId(row.id).update(row)

    def where[K <: Rep[_]](expr: E => K)(implicit wt: CanBeQueryCondition[K]) =
      q.filter(expr)

  }

}