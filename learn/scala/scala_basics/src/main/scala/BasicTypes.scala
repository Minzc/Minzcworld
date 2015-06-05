/**
 * @author congzicun
 * @since 2015-06-04 1:36 PM
 *        To change this template use File | Settings | File Templates.
 */
object BasicTypes {
  def numbers() = {
    val x: Int = 1
    val decimal = 1234
    val decimalLong = 1234L
    val hexa = 0x23 // 16 base
    val i: Byte = 1
    val d = 0.0 // double
    val f = 0.0f // float
  }
  def charactors() = {
    val capB = '\102'
    val capB2 = 'B'
  }
  def strings() = {
    val multiLine =
      """ This is a multiple line
      """.stripMargin
  }

  def someTimeConsumingOperation() = {}

  def createVariable() = {
    val constant = 85
    var variable = 85

    var willKnowLater:String = _

    lazy val forLater = someTimeConsumingOperation()

    var a = 1
    lazy val b = a + 1
    a = 5 // now b is 6

    val first :: rest = List(1, 2, 3)
  }

  def defFunction() = {
    def max(a: Int, b: Int) = if(a > b) a else b

    def myFirstMethod = "exciting times ahead"

    def toList[A](value:A) = List(value) // type inference

    // function literals
    val evenNumbers = List(2, 4, 6, 8, 10)
    evenNumbers.foldLeft(0) { (a: Int, b:Int) => a + b } // add up everything

    def breakable(op: => Unit) = {}
    def foldLeft(initialValue: Int, operator: (Int, Int) => Int)= {}


  }


}
