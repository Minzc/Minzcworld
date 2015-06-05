/**
 * @author congzicun
 * @since 2015-06-04 2:38 PM
 *        To change this template use File | Settings | File Templates.
 */
object ArrayList {
  def createArray() = {
    val array = new Array[String](3)
    array(0) = "This"
    array(1) = "is"
    array(2) = "mutable"
  }

  def interateArray() = {
    val array = new Array[String](3)
    array.foreach(println)
  }

  def createList() = {
    val myList = List("This", "is", "immutable")
  }


}
