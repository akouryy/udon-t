package net.akouryy.udon.view

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Alert
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.{MouseButton, MouseEvent}
import scalafx.scene.layout.HBox
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.util.Duration

import java.net.URL
import javax.sound.sampled._

object TitleStage extends PrimaryStage {
  title = "udon"

  scene = new Scene {
    fill = Color.web("#F9D1D3")

    content = new HBox {
      padding = Insets(20)
      style = "-fx-font-size: 100pt"

      effect = new DropShadow {
        color = Color.web("#fff9f9")
        radius = 25
        spread = 0.25
      }

      children = "udon".map(new TitleText(_))
    }
  }
}

class TitleText(c: Char) extends Text {
  text = c.toString.toUpperCase

  fill <== when(hover) choose Color.web("#FFFFFF") otherwise Color.web("#753329")
  stroke <== when(hover) choose Color.web("#753329") otherwise Color.web("#00000000")
  strokeWidth = 5

  onMouseClicked = (ev: MouseEvent) => {
    val num = ev.button match {
      case MouseButton.PRIMARY => Some(1)
      case MouseButton.SECONDARY => Some(2)
      case _ => None
    }
    num.map { n =>
      val audioIn = AudioSystem.getAudioInputStream(new java.io.File(s"./resources/op/$c$n.wav"))
      val clip = AudioSystem.getClip
      clip.open(audioIn)
      clip.start
    }
  }
}
