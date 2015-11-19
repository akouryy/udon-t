package net.akouryy.udon

import scalafx.Includes._
import net.akouryy.sacalaba.Imports._

object Udon extends JFXApp {
  stage = new PrimaryStage {
    title = "udon"
    scene = view.TitleScene
  }
}
