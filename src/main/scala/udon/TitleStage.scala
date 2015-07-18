package net.akouryy.udon.view

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, Button, TextField, TextInputDialog}
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.{MouseButton, MouseEvent}
import scalafx.scene.layout.{BorderPane, FlowPane, GridPane, ColumnConstraints, HBox, Priority}
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.stage.Modality
import scalafx.util.Duration

import Function.const
import java.net.URL
import javax.sound.sampled._
import net.akouryy.tw4s

object TitleStage extends PrimaryStage { stage =>
  title = "udon"

  scene = new Scene {
    root = new BorderPane {
      vgrow = Priority.Always
      hgrow = Priority.Always

      style = """
                -fx-background-color: #F9D1D3;
                -fx-accent: #753329;
                -fx-focus-color: #FFFFFF;
              """

      center = new GridPane {
        alignment = Pos.Center
        margin = Insets(20)
        hgrow = Priority.Always

        columnConstraints = Array(30, 40, 30) map (x => new ColumnConstraints { percentWidth = x })

        val title = new HBox {
          margin = Insets(20)
          style = "-fx-font-size: 100pt;"
          hgrow = Priority.Always
          alignment = Pos.Center
          effect = new DropShadow {
            color = Color.web("#FFF9F9")
            radius = 25
            spread = 0.25
          }

          children = "udon".map(new TitleText(_))
        }

        val key = new TextField {
          promptText = "API Key (25 chars)"
          margin = Insets(5, 0, 5, 0)
          hgrow = Priority.Always
          style <== when(focused || length === 25) choose "-fx-text-box-border: transparent;" otherwise "-fx-border-color: red;"
        }
        val secret = new TextField {
          promptText = "API Secret (50 chars)"
          margin = Insets(5, 0, 5, 0)
          hgrow = Priority.Always
          style <== when(focused || length === 50) choose "" otherwise "-fx-border-color: red;"
        }
        val authorize = new Button {
          text = "authorize"
          maxWidth = Double.MaxValue
          margin = Insets(20, 0, 10, 0)
          hgrow = Priority.Always

          onAction = () => {
            var messages = List[String]()
            if(key.length.value != 25)
              messages +:= "API Key must have 25 characters."
            if(secret.length.value != 50)
              messages +:= "API Secret must have 50 characters."

            messages match {
              case Nil =>
                val a = new tw4s.Access(key.text.value, secret.text.value)
                a.showPin()
                new TextInputDialog {
                  title = "PIN authorization"
                  headerText = "Input 7-digit PIN code shown after authorizing."
                }.showAndWait.map(p => println(a fetchAccessToken p))
              case _ =>
                new Alert(Alert.AlertType.Error) {
                  initModality(Modality.APPLICATION_MODAL)
                  title = "Authorization keys format error"
                  headerText = "Error"
                  contentText = messages mkString "\n"
                }.showAndWait
            }
          }
        }

        add(title,      0, 0, 3, 1)
        add(key,        0, 1, 3, 1)
        add(secret,     0, 2, 3, 1) // 50
        add(authorize , 1, 3)
      }
    }
  }
}

class TitleText(c: Char) extends Text {
  text = c.toString.toUpperCase

  fill <== when(hover) choose Color.web("#FFFFFF") otherwise Color.web("#753329")
  stroke <== when(hover) choose Color.web("#753329") otherwise Color.web("#00000000")
  strokeWidth = 5

  onMouseClicked = (ev: MouseEvent) => {
    (ev.button match {
      case MouseButton.PRIMARY => Some(1)
      case MouseButton.SECONDARY => Some(2)
      case _ => None
    }) map { n =>
      val audioIn = AudioSystem.getAudioInputStream(new java.io.File(s"./resources/op/$c$n.wav"))
      val clip = AudioSystem.getClip
      clip.open(audioIn)
      clip.start
    }
  }
}
