package data.tables

import slick.driver.PostgresDriver.api._

import scala.language.higherKinds
import scala.reflect._

abstract class BaseTable[E: ClassTag](tag: Tag, tableName: String)
  extends Table[E](tag, tableName) {

  def id = column[Int]("id", O.PrimaryKey)
}
