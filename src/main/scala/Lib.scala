package net.akouryy

package object lib {
  def const = Function.const _

  def constL[A, B](a: =>A)(b: B) = a

  implicit class BooleanPlus(val b: Boolean) extends AnyVal {
    def |=>[A](a: =>A) = if(b) Some(a) else None
    def |!>[A](a: =>A) = !b |=> a
  }

  implicit class OptionPlus[A](val o: Option[A]) extends AnyVal {
    def ||>[B](b: =>B) = o map (Right(_)) getOrElse Left(b)
  }
}
